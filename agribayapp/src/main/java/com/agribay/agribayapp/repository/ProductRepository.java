package com.agribay.agribayapp.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE p.item.name LIKE %?1%")
	Page<Product> findByItemNameContaining(String name, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.item.category = ?1")
	Page<Product> findByItemCategory(ItemCategory itemCategory, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.seller.id = ?1")
	Iterable<Product> findBySeller(Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p SET p.unitPrice = :unitPrice, p.totalQuantity = :totalQuantity, p.description = :description WHERE p.id = :id")
	void update(@Param("id") Long id, @Param("unitPrice") BigDecimal unitPrice,
			@Param("totalQuantity") BigDecimal totalQuantity, @Param("description") String description);
}
