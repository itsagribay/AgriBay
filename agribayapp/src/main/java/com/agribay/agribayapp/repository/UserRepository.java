package com.agribay.agribayapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agribay.agribayapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {  // first argument: its our entity for which   and Long argument
    Optional<User> findByUsername(String username);
}
