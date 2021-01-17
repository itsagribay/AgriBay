package com.agribay.agribayapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agribay.agribayapp.exception.SpringAgribayException;
import com.agribay.agribayapp.model.RefreshToken;
import com.agribay.agribayapp.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenService {         //this class is responsible to create, delete and validate refresh token

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

           log.info("refresh token stored in database {}:", refreshToken);
        return refreshTokenRepository.save(refreshToken);             // refresh token stored in database
    }

    void validateRefreshToken(String token) {
    	   log.info("validating refresh token {} :");
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringAgribayException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
    	   log.info("Deleting Refresh token {}: ",token);
        refreshTokenRepository.deleteByToken(token);
    }
}
