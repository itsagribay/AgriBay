package com.agribay.agribayapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration // so that Spring instantiates this and anything inside here
public class AwsConfig {
	@Value("${AWS_ACCESS_KEY_ID:fallBackValue}")
	private String accessKey;

	@Value("${AWS_SECRET_ACCESS_KEY:fallBackValue}")
	private String secretKey;

	@Value("${AWS_REGION:fallBackValue}")
	private String region;

	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region).build();
	}

}
