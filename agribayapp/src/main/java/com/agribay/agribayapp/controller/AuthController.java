package com.agribay.agribayapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.agribay.agribayapp.dto.AuthenticationResponse;
import com.agribay.agribayapp.dto.LoginRequest;
import com.agribay.agribayapp.dto.RefreshTokenRequest;
import com.agribay.agribayapp.dto.RegisterRequest;
import com.agribay.agribayapp.service.AuthService;
import com.agribay.agribayapp.service.RefreshTokenService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

//import jdk.internal.org.jline.utils.Log;

//import javax.validation.Valid;




//import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")            // authentication request should come in this format
@AllArgsConstructor
@Slf4j
public class AuthController {

	
	 private final AuthService authService;
	 private final RefreshTokenService refreshTokenService;
	
	@CrossOrigin( origins = "http://localhost:4200") 
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		    log.info("1. signup process started");
		  
		  authService.signup(registerRequest);
		     log.info("signup process completed");
		  return  new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
			    
    }

	
	 @GetMapping("accountVerification/{token}")     
	 public ResponseEntity<String> verifyAccount(@PathVariable String token) 
	 {
		    log.info("Account verification started with token {}: ",token);
		 authService.verifyAccount(token);
	     return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
	 }
	 
	@PostMapping("/login") 
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		    log.info("Login request started for user {}: ",loginRequest.getUsername());
		return authService.login(loginRequest);
		}
	 
	 @PostMapping("/refresh/token")
	 public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	        log.info("generate refersh token process started");     
		  return authService.refreshToken(refreshTokenRequest);
	           }
	 
	  @PostMapping("/logout")
	  public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		  refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		     log.info("logout happens and refresh token deleted");
		  return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!"); }
	 }
