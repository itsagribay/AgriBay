package com.agribay.agribayapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;
import com.agribay.agribayapp.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	public Page<Product> getProducts(int pageNo, int pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
		return productRepository.findAll(page);
	}

	public ResponseEntity<Product> getProductById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public Page<Product> getProductsByItemName(String itemName, int pageNo, int pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
		return productRepository.findByItemNameContaining(itemName, page);
	}

	public ResponseEntity<Page<Product>> getProductsByCategory(String category, int pageNo, int pageSize) {
		try {
			ItemCategory itemCategory = ItemCategory.valueOf(category);
			PageRequest page = PageRequest.of(pageNo, pageSize);
			Page<Product> productPage = productRepository.findByItemCategory(itemCategory, page);
			return new ResponseEntity<Page<Product>>(productPage, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Page<Product>>(HttpStatus.NOT_FOUND);
		}
	}
}
