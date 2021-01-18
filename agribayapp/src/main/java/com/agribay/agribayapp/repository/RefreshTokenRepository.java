package com.agribay.agribayapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agribay.agribayapp.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
