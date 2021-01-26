package com.agribay.agribayapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.exception.SpringAgribayException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class S3FileStorageService {

	private final AmazonS3 s3;
	private final S3UsageService s3UsageService;

	@Autowired
	public S3FileStorageService(AmazonS3 s3, S3UsageService s3UsageService) {
		this.s3 = s3;
		this.s3UsageService = s3UsageService;
	}

	public void save(String path, String fileName, Optional<Map<String, String>> optionalMetaData,
			InputStream inputStream) {
		ObjectMetadata metaData = new ObjectMetadata();
		optionalMetaData.ifPresent(map -> {
			if (!map.isEmpty()) {
				map.forEach(
					// alternatively can use method ref here: metaData::addUserMetadata
					(key, value) -> metaData.addUserMetadata(key, value)
				);
			}
		});
		try {
			s3.putObject(path, fileName, inputStream, metaData);
		} catch (AmazonServiceException e) {
			throw new SpringAgribayException("Failed to store file to s3", e);
		} finally {
			s3UsageService.incrementPutRequestsCount();			
		}
	}

	public byte[] download(String path, String key) {
		try {
			S3Object s3Object = s3.getObject(path, key);
			S3ObjectInputStream inputStream = s3Object.getObjectContent();
			return IOUtils.toByteArray(inputStream);
		} catch (AmazonServiceException | IOException e) {
			throw new SpringAgribayException("Failed to download", e);
		} finally {
			s3UsageService.incrementGetRequestsCount();
		}
	}
}