package com.agribay.signup_Signin.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agribay.signup_Signin.dto.AuthenticationResponse;
import com.agribay.signup_Signin.dto.LoginRequest;
import com.agribay.signup_Signin.dto.RefreshTokenRequest;
import com.agribay.signup_Signin.dto.RegisterRequest;
import com.agribay.signup_Signin.exception.SpringAgribayException;
import com.agribay.signup_Signin.model.NotificationEmail;
import com.agribay.signup_Signin.model.User;
import com.agribay.signup_Signin.model.VerificationToken;
import com.agribay.signup_Signin.repository.UserRepository;
import com.agribay.signup_Signin.repository.VerificationTokenRepository;
import com.agribay.signup_Signin.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	 private final MailService mailService;
	 private final AuthenticationManager authenticationManager;
	    private final JwtProvider jwtProvider;
	    private final RefreshTokenService refreshTokenService;

	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        
        userRepository.save(user);
       
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Agribay, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    

}
	  private void fetchUserAndEnable(VerificationToken verificationToken) {
	        String username = verificationToken.getUser().getUsername();
	        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringAgribayException("User not found with name - " + username));
	        user.setEnabled(true);
	        userRepository.save(user);
	    }
	
	  private String generateVerificationToken(User user) {
	        String token = UUID.randomUUID().toString();
	        VerificationToken verificationToken = new VerificationToken();
	        verificationToken.setToken(token);
	        verificationToken.setUser(user);

	        verificationTokenRepository.save(verificationToken);
	        return token;
	    }
	  
	  public void verifyAccount(String token) {
	        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
	        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringAgribayException("Invalid Token")));
	    }

	    public AuthenticationResponse login(LoginRequest loginRequest) {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
	                loginRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        String token = jwtProvider.generateToken(authenticate);
	        return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(loginRequest.getUsername())
	                .build();
	    }

	    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
	        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
	        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
	        return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenRequest.getRefreshToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(refreshTokenRequest.getUsername())
	                .build();
	    }

	    public boolean isLoggedIn() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	    }
	  
}
