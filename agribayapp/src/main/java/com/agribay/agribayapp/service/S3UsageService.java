package com.agribay.agribayapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agribay.agribayapp.model.S3Usage;
import com.agribay.agribayapp.repository.S3UsageRepository;

@Service
public class S3UsageService {

	private final S3UsageRepository s3UsageRepository;

	public S3UsageService(S3UsageRepository s3UsageRepository) {
		super();
		this.s3UsageRepository = s3UsageRepository;
	}

	public void incrementPutRequestsCount() {
		Optional<S3Usage> optionalS3Usage = s3UsageRepository.findById(1);
		
		if (optionalS3Usage.isPresent()) {
			S3Usage s3Usage = optionalS3Usage.get();
			s3Usage.setPutRequestsCount(s3Usage.getPutRequestsCount() + 1);
			s3UsageRepository.save(s3Usage);
		}
	}

	public void incrementGetRequestsCount() {
		Optional<S3Usage> optionalS3Usage = s3UsageRepository.findById(1);
		
		if (optionalS3Usage.isPresent()) {
			S3Usage s3Usage = optionalS3Usage.get();
			s3Usage.setGetRequestsCount(s3Usage.getGetRequestsCount() + 1);
			s3UsageRepository.save(s3Usage);
		}
	}

	public boolean putRequestsIsWithinLimit() {
		Optional<S3Usage> optionalS3Usage = s3UsageRepository.findById(1);
		S3Usage s3Usage = null;
		if (optionalS3Usage.isPresent()) {
			s3Usage = optionalS3Usage.get();
		}
		return s3Usage.getPutRequestsCount() < 1800;
	}

	public boolean getRequestsIsWithinLimit() {
		Optional<S3Usage> optionalS3Usage = s3UsageRepository.findById(1);
		S3Usage s3Usage = null;
		if (optionalS3Usage.isPresent()) {
			s3Usage = optionalS3Usage.get();
		}
		return s3Usage.getGetRequestsCount() < 16000;
	}
}
