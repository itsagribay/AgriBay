package com.agribay.agribayapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribay.agribayapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
