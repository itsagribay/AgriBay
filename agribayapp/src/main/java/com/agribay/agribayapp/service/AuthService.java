package com.agribay.agribayapp.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agribay.agribayapp.dto.AuthenticationResponse;
import com.agribay.agribayapp.dto.LoginRequest;
import com.agribay.agribayapp.dto.RefreshTokenRequest;
import com.agribay.agribayapp.dto.RegisterRequest;
import com.agribay.agribayapp.exception.SpringAgribayException;
import com.agribay.agribayapp.model.NotificationEmail;
import com.agribay.agribayapp.model.User;
import com.agribay.agribayapp.model.VerificationToken;
import com.agribay.agribayapp.repository.UserRepository;
import com.agribay.agribayapp.repository.VerificationTokenRepository;
import com.agribay.agribayapp.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
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
        log.info("2. Registration request got");
        user.setUsername(registerRequest.getUsername());
        log.info("3. Username {} ",registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        log.info("4. Email {} ",registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        log.info("5. Password {} ",registerRequest.getPassword());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        
		
		 userRepository.save(user);
		 
		 String token = generateVerificationToken(user); mailService.sendMail(new
		 NotificationEmail("Please Activate your Account", user.getEmail(),
		 "Thank you for signing up to Agribay, " +
		 "please click on the below url to activate your account : " +
		 "http://localhost:8080/api/auth/accountVerification/" + token));
		 log.info("6. mail sent to email  {} ",registerRequest.getEmail());

}


  
  private String generateVerificationToken(User user) {  
	  String token = UUID.randomUUID().toString(); 
	  VerificationToken verificationToken = new VerificationToken();
	  verificationToken.setToken(token);
      verificationToken.setUser(user);
      log.info("7. verification token generated , token {} ",token);
       verificationTokenRepository.save(verificationToken);
         return token;
      }
 
   public void verifyAccount(String token) {     // here we query the verificationTokenRepository by the given token   
	      Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);   // this will check the token present in DB(token) or not
          fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringAgribayException("Invalid Token"))); // if token not found, it will throw the custom exception "invalid token" or if found , it will enable the user
                
   }
   
   @Transactional  
   private void fetchUserAndEnable(VerificationToken verificationToken) { // it will takethe verificationToken and find by username, if found it will enable the user else throw exception... 
  	 String username = verificationToken.getUser().getUsername();
       User user = userRepository.findByUsername(username).orElseThrow(() -> new  SpringAgribayException("User not found with name - " + username));
           user.setEnabled(true); 
           userRepository.save(user);
           }
  
  public AuthenticationResponse login(LoginRequest loginRequest) {
           Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
          log.info("2. we get username : {} and password : {} ",loginRequest.getUsername(),loginRequest.getPassword() );
           
           SecurityContextHolder.getContext().setAuthentication(authenticate); 
           log.info("3. Authenticated object {} :", authenticate);
            
           String token = jwtProvider.generateToken(authenticate);
          
           log.info("5. token generated {} and return to AuthResponse", token);
           log.info("6. refresh token passed as empty string so if we performed login , we get one Json web token , one refresh token and expiry date" );
           //return new AuthenticationResponse(token, loginRequest.getUsername());
           return AuthenticationResponse.builder().authenticationToken(token).refreshToken(refreshTokenService.generateRefreshToken().getToken()).expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())).username(loginRequest.getUsername()).build(); 
           }
  
  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
       refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
       String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        log.info("here we validate refreshToken, if success , this will generate new refreshToken with username{}: ",token);
       return AuthenticationResponse.builder().authenticationToken(token).refreshToken(refreshTokenRequest.getRefreshToken()).expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())).username(refreshTokenRequest.getUsername()).build(); 
       }
 
/* public boolean isLoggedIn() { Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication(); return
 * !(authentication instanceof AnonymousAuthenticationToken) &&
 * authentication.isAuthenticated(); }
 */
	  
}
