package com.agribay.agribayapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribay.agribayapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
