package com.agribay.agribayapp.model;

public enum Unit {
	kg("kg"), dozen("dozen");

	private String unit;

	Unit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}
}
