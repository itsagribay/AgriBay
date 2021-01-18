package com.agribay.agribayapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
