package br.com.evaluation.car.rent.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import br.com.evaluation.car.rent.domain.S3File;
import br.com.evaluation.car.rent.domain.SESEmail;

public interface AwsFeaturesService {
	
	public AmazonSimpleEmailService buildAmazonSimpleEmailService(Regions region);
	
	public AmazonS3 buildAmazonS3Client();
	
	public void sendSESEmail(SESEmail sesEmail);
	
	public S3File storageImageS3(MultipartFile s3File);
	
	public boolean checkEmailVerified(String emailTo);
	
	public void sesVerifyEmail(String email) throws AmazonClientException;
}
