package com.agribay.agribayapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.agribay.agribayapp.model.S3Usage;

public interface S3UsageRepository extends CrudRepository<S3Usage, Integer> {
}
