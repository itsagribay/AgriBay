package com.agribay.agribayapp.model;

public enum BucketName {
	PRODUCT_IMAGE("agribay");

	private final String bucketName;

	BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}
}
