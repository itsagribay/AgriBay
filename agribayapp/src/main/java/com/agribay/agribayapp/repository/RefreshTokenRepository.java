package com.agribay.signup_Signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribay.signup_Signin.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
