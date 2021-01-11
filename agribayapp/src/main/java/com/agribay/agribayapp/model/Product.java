package com.agribay.agribayapp.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = { "seller_id", "item_id" }) })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.DETACH)
	@JoinColumn(name = "seller_id", foreignKey = @ForeignKey(name = "seller_id"))
	@JsonIdentityReference(alwaysAsId = true)
	private User seller;

	@Column(length = 512, nullable = false)
	private String sellerAddress;

	@Column(length = 7, scale = 2, nullable = false, columnDefinition = "Decimal(7,2) default '0.00'")
	// CONSTRAINT price_gte_0 CHECK (base_price >= 0)
	private BigDecimal unitPrice;

	@Column(length = 6, scale = 3, nullable = false, columnDefinition = "Decimal(6,3) default '0.000'")
	// CONSTRAINT quantity_gte_0 CHECK (total_quantity >= 0)
	private BigDecimal totalQuantity;

	@Column(length = 100)
	private String imageUrl1;

	@Column(length = 100)
	private String imageUrl2;

	@Column(length = 512)
	private String description;

	@ManyToOne(optional = false, cascade = CascadeType.DETACH)
	@JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "item_id"))
	private Item item;

	@Transient
	private BigDecimal totalPrice;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, User seller, String sellerAddress, BigDecimal unitPrice, BigDecimal totalQuantity,
			String imageUrl1, String imageUrl2, String description, Item item) {
		super();
		this.id = id;
		this.seller = seller;
		this.sellerAddress = sellerAddress;
		this.unitPrice = unitPrice;
		this.totalQuantity = totalQuantity;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.description = description;
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void updateTotalPrice() {
		this.totalPrice = this.getUnitPrice().multiply(this.getTotalQuantity());
	}
}