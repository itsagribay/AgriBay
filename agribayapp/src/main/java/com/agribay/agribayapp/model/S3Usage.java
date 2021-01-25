package com.agribay.agribayapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class S3Usage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@Column(nullable = false)
	private int getRequestsCount;
	
	@NotNull
	@Column(nullable = false)
	private int putRequestsCount;

	public S3Usage() {
		super();
	}

	public S3Usage(Integer id, @NotNull int getRequestsCount, @NotNull int putRequestsCount) {
		super();
		this.id = id;
		this.getRequestsCount = getRequestsCount;
		this.putRequestsCount = putRequestsCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getGetRequestsCount() {
		return getRequestsCount;
	}

	public void setGetRequestsCount(int getRequestsCount) {
		this.getRequestsCount = getRequestsCount;
	}

	public int getPutRequestsCount() {
		return putRequestsCount;
	}

	public void setPutRequestsCount(int putRequestsCount) {
		this.putRequestsCount = putRequestsCount;
	}
}
