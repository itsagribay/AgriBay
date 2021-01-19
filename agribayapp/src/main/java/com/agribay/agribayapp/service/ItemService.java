package com.agribay.agribayapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	@Autowired
	ItemService(ItemRepository itemRepository) {
		super();
		this.itemRepository = itemRepository;
	}

	public Iterable<Item> getAllItems() {
		return itemRepository.findAll();
	}

}
