package com.agribay.agribayapp.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ProductUpdateRequest {
	@NotNull
	private BigDecimal unitPrice;

	@NotNull
	private BigDecimal totalQuantity;

	private String description;

	public ProductUpdateRequest() {
		super();
	}

	public ProductUpdateRequest(@NotNull BigDecimal unitPrice, @NotNull BigDecimal totalQuantity, String description) {
		super();
		this.unitPrice = unitPrice;
		this.totalQuantity = totalQuantity;
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public String getDescription() {
		return description;
	}
}
