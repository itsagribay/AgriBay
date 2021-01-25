package com.agribay.agribayapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

	@Query("SELECT i FROM Item i WHERE i.id NOT IN (SELECT p.item.id FROM Product p WHERE p.seller.id = :sellerId)")
	Iterable<Item> findItemNotInSellersProductListing(@Param("sellerId") Long sellerId);

}
