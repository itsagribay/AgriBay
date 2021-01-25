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
import org.springframework.web.multipart.MultipartFile;

import com.agribay.agribayapp.dto.ProductCreateRequest;
import com.agribay.agribayapp.dto.ProductUpdateRequest;
import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;
import com.agribay.agribayapp.model.User;
import com.agribay.agribayapp.repository.ItemRepository;
import com.agribay.agribayapp.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ItemRepository itemRepository;
	private final AuthService authService;
	private final ProductImageFileService productImageFileService;

	@Autowired
	public ProductService(ProductRepository productRepository, ItemRepository itemRepository, 
			              AuthService authService, ProductImageFileService productImageFileService) {
		super();
		this.productRepository = productRepository;
		this.itemRepository = itemRepository;
		this.authService = authService;
		this.productImageFileService = productImageFileService;
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

	public Iterable<Product> getAllProductsBySeller() {
		User seller = authService.getAuthenticatedUser();
		return productRepository.findBySeller(seller.getId());
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public ResponseEntity<?> updateProduct(Long id, ProductUpdateRequest updates) {
		productRepository.update(id, updates.getUnitPrice(), updates.getTotalQuantity(), updates.getDescription());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> createNewProduct(String productCreateRequestString, List<MultipartFile> imageFiles) {
		User seller = authService.getAuthenticatedUser();
		String imageUrls[] = new String[] { "", "" };
		// TODO: Change to s3 storage implementation in production (2 lines commented below)
		// String imageUrls[] = new String[2];
		// imageUrls = productImageFileService.getImageUrls(imageFiles, seller);
		
		Product product = new Product();
		Product savedProduct = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ProductCreateRequest productCreateRequest = objectMapper.readValue(productCreateRequestString,
					ProductCreateRequest.class);
			product.setSeller(seller);
			product.setUnitPrice(productCreateRequest.getUnitPrice());
			product.setTotalQuantity(productCreateRequest.getTotalQuantity());
			product.setDescription(productCreateRequest.getDescription());
			product.setImageUrl1(imageUrls[0]);
			product.setImageUrl2(imageUrls[1]);
			Optional<Item> optionalItem = itemRepository.findById(productCreateRequest.getItemId());
			if (optionalItem.isPresent()) {
				product.setItem(optionalItem.get());
			}
			savedProduct = productRepository.save(product);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	}

	public byte[] downloadProductImage(String sellerNameAndId, String filename) {
		return new byte[0];
		// TODO: Change to s3 storage implementation in production (1 lines commented below)
		// return productImageFileService.downloadUserProfileImage(sellerNameAndId, filename);
	}
}
