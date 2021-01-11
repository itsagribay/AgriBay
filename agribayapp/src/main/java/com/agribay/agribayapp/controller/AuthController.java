package com.agribay.agribayapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;

import com.agribay.agribayapp.dto.RegisterRequest;
import com.agribay.agribayapp.service.AuthService;

/*import com.agribay.signup_Signin.dto.AuthenticationResponse;
import com.agribay.signup_Signin.dto.LoginRequest;
import com.agribay.signup_Signin.dto.RefreshTokenRequest;
import com.agribay.signup_Signin.dto.RegisterRequest;
import com.agribay.signup_Signin.service.AuthService;
import com.agribay.signup_Signin.service.RefreshTokenService;*/


//import javax.validation.Valid;




//import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	
	 private final AuthService authService;
	 //private final RefreshTokenService refreshTokenService;
	
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		
		 authService.signup(registerRequest);
		 BodyBuilder builder = (BodyBuilder) ResponseEntity.ok();
		 return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
		
    }

	/*
	 * @GetMapping("accountVerification/{token}") public ResponseEntity<String>
	 * verifyAccount(@PathVariable String token) { authService.verifyAccount(token);
	 * return new ResponseEntity<>("Account Activated Successfully", OK); }
	 * 
	 * @PostMapping("/login") public AuthenticationResponse login(@RequestBody
	 * LoginRequest loginRequest) { return authService.login(loginRequest); }
	 * 
	 * @PostMapping("/refresh/token") public AuthenticationResponse
	 * refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	 * return authService.refreshToken(refreshTokenRequest); }
	 * 
	 * @PostMapping("/logout") public ResponseEntity<String>
	 * logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	 * refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken())
	 * ; return
	 * ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!"); }
	 */}
