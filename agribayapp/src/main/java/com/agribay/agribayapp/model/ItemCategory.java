package com.agribay.agribayapp.model;

public enum ItemCategory {
	vegetables("vegetables"), fruits("fruits");

	private String category;

	ItemCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
