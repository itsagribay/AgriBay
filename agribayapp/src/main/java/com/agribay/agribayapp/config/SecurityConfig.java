package com.agribay.agribayapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.agribay.agribayapp.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity         // this will enables the web security module in our project
@AllArgsConstructor        
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {    // this class holds the complete security configuration of our application

	 private final UserDetailsService userDetailsService;
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 

	    @Bean(BeanIds.AUTHENTICATION_MANAGER)               // Bean created to provide implementation to AuthenticationManager
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	 
	 @Override
	    public void configure(HttpSecurity httpSecurity) throws Exception {    //we are configuring Spring to allow all the requests which match the endpoint “/api/auth/**” , as these endpoints are used for authentication and registration we don’t expect the user to be authenticated at that point of time.
	        httpSecurity.cors().and()
	                .csrf().disable()        
	                .authorizeRequests()
	                .antMatchers("/api/auth/**")       // authencticate all the request which doen't match this pattern
	                .permitAll().antMatchers(HttpMethod.GET, "/api/auth") .permitAll()
					.antMatchers(HttpMethod.GET, "/api/products/") .permitAll()     // these should be GET call so that spring will not authorize these everytime and guest can see these pages without login
 					.antMatchers(HttpMethod.GET, "/api/cart/**") .permitAll()
					.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",     
					 "/configuration/security", "/swagger-ui.html", "/webjars/**")                
					.permitAll()
	                .anyRequest()
	                .authenticated();
	                httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	    }
	 
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	    	log.info("Password encrypting started ");
	        authenticationManagerBuilder.userDetailsService(userDetailsService)
	                .passwordEncoder(passwordEncoder());          // here we are using passwordEncoder of spring, we can also use LDAP, memory based, Database based authentication
	    }

		
		 @Bean        // we used bean annotation because PasswordEncoder is a interface and whenver we are Autowiring this bean , we will get PasswordEncoder() object
		 PasswordEncoder passwordEncoder() { 
			 log.info("Password is encrypted using BCrypt Hashing algorithm ");
			 return new BCryptPasswordEncoder();
		 }
		

}
