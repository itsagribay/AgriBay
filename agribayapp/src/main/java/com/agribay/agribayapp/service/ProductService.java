package com.agribay.agribayapp.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.dto.ProductCreateRequest;
import com.agribay.agribayapp.dto.ProductUpdateRequest;
import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;
import com.agribay.agribayapp.model.User;
import com.agribay.agribayapp.repository.ItemRepository;
import com.agribay.agribayapp.repository.ProductRepository;
import com.agribay.agribayapp.repository.UserRepository;
import com.agribay.agribayapp.utils.JsonNullableUtils;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	@Autowired
	public ProductService(ProductRepository productRepository, UserRepository userRepository,
			ItemRepository itemRepository) {
		super();
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	public ResponseEntity<Product> getProductById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public Page<Product> getProducts(int pageNo, int pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
		return productRepository.findAll(page);
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

	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Map<String, List<ItemCategory>> getAllProductCategories() {
		List<ItemCategory> itemCategoryList = Arrays.asList(ItemCategory.values());
		Map<String, List<ItemCategory>> categories = new HashMap<>();
		categories.put("categories", itemCategoryList);
		return categories;
	}

	public ResponseEntity<Product> createNewProduct(ProductCreateRequest productCreateRequest) {
		Product product = new Product();
		Optional<User> optionalUser = userRepository.findById((long) 2);
		if (optionalUser.isPresent()) {
			product.setSeller(optionalUser.get());
		}
		product.setSellerAddress(productCreateRequest.getSellerAddress());
		product.setUnitPrice(productCreateRequest.getUnitPrice());
		product.setTotalQuantity(productCreateRequest.getTotalQuantity());
		product.setImageUrl1(productCreateRequest.getImageUrl1());
		product.setImageUrl2(productCreateRequest.getImageUrl2());
		product.setDescription(productCreateRequest.getDescription());
		Optional<Item> optionalItem = itemRepository.findById(productCreateRequest.getItemId());
		if (optionalItem.isPresent()) {
			product.setItem(optionalItem.get());
		}
		Product savedProduct = productRepository.save(product);
		return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public ResponseEntity<Product> updateProduct(long id, ProductUpdateRequest productUpdateRequest) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (!productOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Product product = productOptional.get();
		JsonNullableUtils.changeIfPresent(productUpdateRequest.getUnitPrice(), product::setUnitPrice);
		JsonNullableUtils.changeIfPresent(productUpdateRequest.getTotalQuantity(), product::setTotalQuantity);

		Product updatedProduct = productRepository.save(product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
}
