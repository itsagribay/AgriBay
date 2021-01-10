package com.agribay.agribayapp.repository.cartRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.entity.cartItems.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	
	public List<CartItem> findByCustomer(Customer customer);
	
}
