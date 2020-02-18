package br.com.evaluation.car.rent.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressResult;

import br.com.evaluation.car.rent.config.PropertiesConfig;
import br.com.evaluation.car.rent.domain.AmazonCredentials;
import br.com.evaluation.car.rent.domain.S3File;
import br.com.evaluation.car.rent.domain.SESEmail;
import br.com.evaluation.car.rent.service.AwsFeaturesService;
import br.com.evaluation.car.rent.util.OperationUtil;

@Service
public class AwsFeaturesServiceImpl implements AwsFeaturesService {
	
	
	private AmazonSimpleEmailService amazonSimpleEmailServiceClient;
	
	private static final Regions regionDefault = Regions.US_WEST_2;
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Override
	public void sendSESEmail(SESEmail sesEmail) {
		SendEmailRequest request = new SendEmailRequest()
				.withDestination(
						new Destination().withToAddresses(sesEmail.getEmail()))
				.withMessage(new Message()
				.withBody(new Body()
				.withHtml(new Content()
				.withCharset("UTF-8").withData(sesEmail.getContent()))
				.withText(new Content()
				.withCharset("UTF-8").withData(sesEmail.getContent())))
				.withSubject(new Content()
				.withCharset("UTF-8").withData(sesEmail.getSubject())))
				.withSource(propertiesConfig.getEmailOrigin());
		amazonSimpleEmailServiceClient = this.buildAmazonSimpleEmailService(Regions.US_WEST_2);
		if(!this.checkEmailVerified(sesEmail.getEmail())) {
			this.sesVerifyEmail(sesEmail.getEmail());
		}
		amazonSimpleEmailServiceClient.sendEmail(request);
		
	}

	
	@Override
	public AmazonSimpleEmailService buildAmazonSimpleEmailService(Regions region) {
		
		if(amazonSimpleEmailServiceClient == null) {
			AmazonCredentials credentials = new AmazonCredentials(propertiesConfig.getAwsAccessKey(), propertiesConfig.getAwsSecretKey());
			amazonSimpleEmailServiceClient = 
			          AmazonSimpleEmailServiceClientBuilder.standard()
			          	.withCredentials(credentials)
			            .withRegion(region).build();
		}
		
		return amazonSimpleEmailServiceClient;
	}

	@Override
	public boolean checkEmailVerified(String emailTo) {
		amazonSimpleEmailServiceClient = this.buildAmazonSimpleEmailService(Regions.US_WEST_2);
		return amazonSimpleEmailServiceClient.listVerifiedEmailAddresses().getVerifiedEmailAddresses().contains(emailTo);
	}

	@Override
	public void sesVerifyEmail(String email) throws AmazonClientException {
		amazonSimpleEmailServiceClient = this.buildAmazonSimpleEmailService(Regions.US_WEST_2);
		VerifyEmailAddressRequest vf = new VerifyEmailAddressRequest().withEmailAddress(email);
		VerifyEmailAddressResult result = amazonSimpleEmailServiceClient.verifyEmailAddress(vf);
		if(result.getSdkHttpMetadata().getHttpStatusCode() != 200) {
			throw new AmazonClientException(String.format("Email % not verified!", email));
		}
	}

	@Override
	public AmazonS3 buildAmazonS3Client() {
		AmazonCredentials credentials = new AmazonCredentials(propertiesConfig.getAwsAccessKey(), propertiesConfig.getAwsSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials.getCredentials()))
				.withRegion(regionDefault)
				.build();
		return s3Client;
	}

	@Override
	public S3File storageImageS3(MultipartFile file)  {
		S3File s3File = new S3File();
        File fileMemory;
		try {
			AmazonS3 s3client = buildAmazonS3Client();
			fileMemory = OperationUtil.convertMultiPartToFile(file);
			s3client.putObject(new PutObjectRequest(propertiesConfig.getBucketName(), file.getOriginalFilename(), fileMemory)
		            .withCannedAcl(CannedAccessControlList.PublicRead));
			fileMemory.delete();
			s3File.setMessageRequest("Image uploaded!");
			s3File.setMessageStatus(true);
			s3File.setUrlS3(propertiesConfig.getBucketBaseUrl() + "/" + file.getOriginalFilename());
		} catch (Exception e) {
			s3File.setMessageRequest(e.getMessage());
			s3File.setMessageStatus(false);
		}
		return s3File;
	}
	
	

}
