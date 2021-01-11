package com.agribay.agribayapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 255, nullable = false, unique = true)
	@NaturalId
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 255, nullable = false)
	private ItemCategory category;

	@Enumerated(EnumType.STRING)
	@Column(length = 255, nullable = false, columnDefinition = "varchar(255) default 'kg'")
	private Unit unit;

	@Column(length = 100)
	private String defaultImage;

	public Item() {
		super();
	}

	public Item(Long id, String name, ItemCategory category, Unit unit, String defaultImage) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.unit = unit;
		this.defaultImage = defaultImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
}
