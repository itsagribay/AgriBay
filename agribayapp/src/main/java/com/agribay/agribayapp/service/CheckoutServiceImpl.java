package com.agribay.agribayapp.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agribay.agribayapp.dto.Purchase;
import com.agribay.agribayapp.dto.PurchaseResponse;
import com.agribay.agribayapp.model.Customer;
import com.agribay.agribayapp.model.Order;
import com.agribay.agribayapp.model.OrderItem;
import com.agribay.agribayapp.repository.CustomerRepository;

@Service
public class CheckoutServiceImpl implements CheckoutService{

	private CustomerRepository customerRepository;
	
	
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		// retrieve the order info from dto
		Order order = purchase.getOrder();
		
		// generate order number
		String orderNumber = generateOrderNumber();
		order.setOrderNumber(orderNumber);
		
		// populate order with orderItmes
		Set<OrderItem> orderItems = purchase.getOrderItems();
		System.out.println( "show orderItem here -->   " + orderItems);
		orderItems.forEach(item -> order.add(item));
		
		// populate order with address
		order.setAddress(purchase.getDeliveryAddress());
		
		// populate customer with order
		Customer customer = purchase.getCustomer();
		customer.add(order);
		// save to the database
		customerRepository.save(customer);
		
		// return response
		return new PurchaseResponse(orderNumber);
	}

	private String generateOrderNumber() {
		// generate a random UUID (version4)
		return UUID.randomUUID().toString();
	}
	

}
