package com.agribay.agribayapp.dto;

import java.util.Set;

import com.agribay.agribayapp.model.Address;
import com.agribay.agribayapp.model.Customer;
import com.agribay.agribayapp.model.Order;
import com.agribay.agribayapp.model.OrderItem;

import lombok.Data;

@Data
public class Purchase {

	private Customer customer;
	//private Address shippingAddress;
	private Address deliveryAddress;
	private Order order;
	private Set<OrderItem> orderItems;
}
