package com.agribay.agribayapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.model.User;
import com.agribay.agribayapp.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final AuthService authService;

	@Autowired
	ItemService(ItemRepository itemRepository, AuthService authService) {
		super();
		this.itemRepository = itemRepository;
		this.authService = authService;
	}

	public Iterable<Item> getAllItems() {
		// TODO: change the normal findAll
		// to be filtered based on products listing already
		// done by the Security context user (current user)
		User seller = authService.getAuthenticatedUser();
		// return itemRepository.findAll();
		return itemRepository.findItemNotInSellersProductListing(seller.getId());
	}

}
