package com.agribay.agribayapp.service;

import com.agribay.agribayapp.dto.Purchase;
import com.agribay.agribayapp.dto.PurchaseResponse;

public interface CheckoutService {
	
	PurchaseResponse placeOrder(Purchase purchase);

}
