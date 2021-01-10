package com.agribay.agribayapp.entity.cartItems;

import javax.persistence.Entity;

@Entity
public class CartItems {

	private int cartId;
	
	private Product product;
	
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private Customer customer;
	
	
	
}
