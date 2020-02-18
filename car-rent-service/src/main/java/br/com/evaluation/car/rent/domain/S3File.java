package br.com.evaluation.car.rent.domain;

public class S3File {
	
	private String urlS3;
	private String messageRequest;
	private boolean messageStatus;
	
	public String getMessageRequest() {
		return messageRequest;
	}
	public void setMessageRequest(String messageRequest) {
		this.messageRequest = messageRequest;
	}
	public String getUrlS3() {
		return urlS3;
	}
	public void setUrlS3(String urlS3) {
		this.urlS3 = urlS3;
	}
	public boolean isMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(boolean messageStatus) {
		this.messageStatus = messageStatus;
	}
}
