package com.agribay.agribayapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE p.item.name LIKE %?1%")
	Page<Product> findByItemNameContaining(String name, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.item.category = ?1")
	Page<Product> findByItemCategory(ItemCategory itemCategory, Pageable pageable);
}
