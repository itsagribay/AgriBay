package com.agribay.agribayapp.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.openapitools.jackson.nullable.JsonNullable;

public class ProductUpdateRequest {
	@NotNull
	private JsonNullable<BigDecimal> unitPrice = JsonNullable.undefined();

	@NotNull
	private JsonNullable<BigDecimal> totalQuantity = JsonNullable.undefined();

	public JsonNullable<BigDecimal> getTotalQuantity() {
		return totalQuantity;
	}

	public JsonNullable<BigDecimal> getUnitPrice() {
		return unitPrice;
	}
}
