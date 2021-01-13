package com.agribay.agribayapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.agribay.agribayapp.exception.SpringAgribayException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
@Slf4j
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/agribay.jks");
            keyStore.load(resourceAsStream, "changeit".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringAgribayException("Exception occurred while loading keystore",e);
        }

    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        log.info("4. token generated and send to login method ");
        
        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(from(Instant.now())).signWith(getPrivateKey()).setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).compact();
             
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
        	    
            return (PrivateKey) keyStore.getKey("agribay","changeit".toCharArray());  // here "agribay" is the alias, and "changeit" is the password for this alias.
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringAgribayException("Exception occured while retrieving public key from keystore",e);
        }
    }

    public boolean validateToken(String jwt) {
    	log.info("token validation happens in jwtProvider class");
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickey() {
        try {
        	log.info("getPublicKey() called and getting certificate from keystore agribay ");
            return keyStore.getCertificate("agribay").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringAgribayException("Exception occured while " +
                    "retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {
    	
    	log.info("extracting username from jwt");
        Claims claims = parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
