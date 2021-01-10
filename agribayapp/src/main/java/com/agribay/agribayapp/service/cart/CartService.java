package com.agribay.agribayapp.service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.entity.cartItems.CartItem;
import com.agribay.agribayapp.repository.cartRepository.CartItemRepository;

@Service
public class CartService {

	@Autowired
	private CartItemRepository cartRepo;
	
	public List<CartItem> listCartItems(Customer customer)
	{
		return cartRepo.findByCustomer(customer);
	}
	
	
	
	
}
