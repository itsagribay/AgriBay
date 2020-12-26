package com.agribay.signup_Signin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	    public void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity.cors().and()
	                .csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/api/auth/**")
	                .permitAll()
	                .antMatchers(HttpMethod.GET, "/api/subreddit")
	                .permitAll()
	                .antMatchers(HttpMethod.GET, "/api/posts/")
	                .permitAll()
	                .antMatchers(HttpMethod.GET, "/api/posts/**")
	                .permitAll()
	                .antMatchers("/v2/api-docs",
	                        "/configuration/ui",
	                        "/swagger-resources/**",
	                        "/configuration/security",
	                        "/swagger-ui.html",
	                        "/webjars/**")
	                .permitAll()
	                .anyRequest()
	                .authenticated();
	       /* httpSecurity.addFilterBefore(jwtAuthenticationFilter,
	                UsernamePasswordAuthenticationFilter.class);
	   */ }

	  @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
