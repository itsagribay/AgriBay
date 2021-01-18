package com.agribay.agribayapp.dto;

import java.math.BigDecimal;

public class ProductCreateRequest {
	// id - not required as auto-generated
	// seller - take it from user context
	private String sellerAddress;
	private BigDecimal unitPrice;
	private BigDecimal totalQuantity;
	private String imageUrl1;
	private String imageUrl2;
	private String description;
	private Long itemId;

	public ProductCreateRequest(String sellerAddress, BigDecimal unitPrice, BigDecimal totalQuantity, String imageUrl1,
			String imageUrl2, String description, Long itemId) {
		super();
		this.sellerAddress = sellerAddress;
		this.unitPrice = unitPrice;
		this.totalQuantity = totalQuantity;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.description = description;
		this.itemId = itemId;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
}
