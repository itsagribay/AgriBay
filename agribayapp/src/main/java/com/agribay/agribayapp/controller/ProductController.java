package com.agribay.agribayapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agribay.agribayapp.dto.ProductUpdateRequest;
import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;
import com.agribay.agribayapp.service.ProductService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	// product by id
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		return productService.getProductById(id);
	}

	// get products paginated based on pageNo and pageSize
	@GetMapping
	public Page<Product> getProducts(@RequestParam("page") int pageNo, @RequestParam("size") int pageSize) {
		return productService.getProducts(pageNo, pageSize);
	}

	// get products paginated based on item name substring match
	@GetMapping("/search/findByItemNameContaining")
	public Page<Product> getProductsByItemName(@RequestParam("name") String itemName, @RequestParam("page") int pageNo,
			@RequestParam("size") int pageSize) {
		return productService.getProductsByItemName(itemName, pageNo, pageSize);
	}

	// get products by ItemCategory
	@GetMapping("/search/findByCategory")
	public ResponseEntity<Page<Product>> getProductsByCategory(@RequestParam("category") String category,
			@RequestParam("page") int pageNo, @RequestParam("size") int pageSize) {
		return productService.getProductsByCategory(category, pageNo, pageSize);
	}

	// get all products
	@GetMapping("/all")
	public Iterable<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/categories")
	public Map<String, List<ItemCategory>> getAllProductCategories() {
		return productService.getAllProductCategories();
	}

	// find all products by seller
	@GetMapping("/findBySeller")
	public Iterable<Product> getAllProductsBySeller() {
		return productService.getAllProductsBySeller();
	}

	// edit product listing - yeah here patch requests
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
			@RequestBody ProductUpdateRequest partialUpdates) {
		return productService.updateProduct(id, partialUpdates);
	}

	// delete product listing - delete simply
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

	// create new product listing
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewProduct(@RequestPart("productCreateRequest") String productCreateRequest,
			@RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) {
		return productService.createNewProduct(productCreateRequest, imageFiles);
	}
	
	// download the file (spring server acts as a proxy to aws)
    @GetMapping("image/download/{sellerNameAndId}/{filename}")
    public byte[] downloadUserProfileImage(@PathVariable("sellerNameAndId") String sellerNameAndId, 
    									   @PathVariable("filename") String filename) {
        return productService.downloadProductImage(sellerNameAndId, filename);
    }
}
