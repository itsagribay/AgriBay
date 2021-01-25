package com.agribay.agribayapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

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
    
	
    // ---------------------------- JWT signing code ----------------------------------- 

    @PostConstruct
    public void init() {          // We are using AsymmetricEncryption to sign our JWT’s using Java Keystore (using Public-Private Key)
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/agribay.jks");
            keyStore.load(resourceAsStream, "changeit".toCharArray());
                log.info("signing our JWT’s using agribay.jks Keystore");
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringAgribayException("Exception occurred while loading keystore",e);
        }

    }
    
	// ---------------------------- Generating token code ----------------------------------- 

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
              log.info("4. token generated and send to login method ");
        
        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(from(Instant.now())).signWith(getPrivateKey()).setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).compact();
             
    }

    public String generateTokenWithUserName(String username) {
    	      log.info("Generating token with username which will expire after 90 sec");
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

    
	// ---------------------------- Extracting username from jwt code ----------------------------------- 
    
    public String getUsernameFromJwt(String token) {        // we have to extract username from JWt because we use it as a subject while creating the JWT token
    	
    	     log.info("extracting username from jwt ");
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
