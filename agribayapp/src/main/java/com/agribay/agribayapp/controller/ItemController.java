package com.agribay.agribayapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.service.ItemService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("items")
public class ItemController {
	private final ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		super();
		this.itemService = itemService;
	}

	@GetMapping
	public Iterable<Item> getAllProducts() {
		return itemService.getAllItems();
	}

}
