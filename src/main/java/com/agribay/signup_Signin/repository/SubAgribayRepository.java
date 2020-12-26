package com.agribay.signup_Signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agribay.signup_Signin.model.SubAgribay;

import java.util.Optional;

@Repository
public interface SubAgribayRepository extends JpaRepository<SubAgribay, Long> {

    Optional<SubAgribay> findByName(String subredditName);
}
