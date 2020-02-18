package br.com.evaluation.car.rent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String awsAccessKey;
	@Value("${cloud.aws.credentials.secretKey}")
	private String awsSecretKey;
	@Value("${email.origin}")
	private String emailOrigin;
	@Value("${cloud.aws.bucket.name}")
	private String bucketName;
	@Value("${cloud.aws.bucket.url}")
	private String bucketBaseUrl;
	
	
	public String getBucketBaseUrl() {
		return bucketBaseUrl;
	}
	public void setBucketBaseUrl(String bucketBaseUrl) {
		this.bucketBaseUrl = bucketBaseUrl;
	}
	public String getEmailOrigin() {
		return emailOrigin;
	}
	public void setEmailOrigin(String emailOrigin) {
		this.emailOrigin = emailOrigin;
	}
	public String getAwsAccessKey() {
		return awsAccessKey;
	}
	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getAwsSecretKey() {
		return awsSecretKey;
	}
	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
}
