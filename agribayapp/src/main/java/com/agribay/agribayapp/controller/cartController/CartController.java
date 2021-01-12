package com.agribay.agribayapp.controller.cartController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribay.agribayapp.entity.cartItems.CartItem;
import com.agribay.agribayapp.service.cart.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	private CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	
	@GetMapping("/cartitems{customerid}")
	public List<CartItem> findall(@PathVariable int customer) {
		return cartService.listCartItems(customer);
	}

	@PostMapping("/cartitems") /// Adding to the cart and updating the cart
	public Customer update(@RequestBody Customer customer) {
		return cartService.save(customer);
	}

	@DeleteMapping("/cartitems/{customerid}")
	public String deleteEmployee(@PathVariable int customerid) {
	Customer tempCustomer = cartService.deleteById(customerid);

	cartService.deleteById(customerid);

	return "Deleted item id=" + customerid;

	}

}
