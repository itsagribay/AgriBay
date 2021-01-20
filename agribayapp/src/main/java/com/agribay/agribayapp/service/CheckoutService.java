package com.agribay.agribayapp.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.agribay.agribayapp.dto.Purchase;
import com.agribay.agribayapp.dto.PurchaseResponse;
import com.agribay.agribayapp.model.Customer;
import com.agribay.agribayapp.model.Order;
import com.agribay.agribayapp.model.OrderItem;
import com.agribay.agribayapp.repository.CustomerRepository;

@Service
public class CheckoutService {

	private CustomerRepository customerRespository;
	
	public CheckoutService(CustomerRepository customerRespository)
	{
		this.customerRespository = customerRespository;
	}
	
	public PurchaseResponse placeOrder(Purchase purchase)
	{
		//retrieve the order info from the dto
		
		Order order = purchase.getOrder();
		
		//generate tracking number
		
		String orderTrackingNumer = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumer);
		
		//populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item->order.add(item));
		
		//populate order with billingAddress and shipping address
		
		order.setBillingAddress(purchase.getBillingAddess());
		order.setShippingAddress(purchase.getShippingAddress());
		
		//populate customer with order
		
		Customer customer = purchase.getCustomer();
		customer.add(order);
		
		//save to the database
		customerRespository.save(customer);
		
		//return the response
		return new PurchaseResponse(orderTrackingNumer);
	}

	private String generateOrderTrackingNumber() //Universally Unique Identifier
	{
		//generate a random UUID number (UUID version-4)
		return UUID.randomUUID().toString();   //built in java class
		
	}
}
