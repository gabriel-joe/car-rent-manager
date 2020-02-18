package br.com.evaluation.car.rent.domain;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class AmazonCredentials implements AWSCredentialsProvider {
	
	private String accessKey;
	private String secretKey;

	public AWSCredentials getCredentials() {
		return new BasicAWSCredentials(this.accessKey,
				this.secretKey);
	}
	
	public AmazonCredentials(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
	}
}
