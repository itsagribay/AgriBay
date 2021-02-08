package com.agribay.agribayapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribay.agribayapp.model.User;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/userprofile")
public class UserProfileController {
	
	@PostMapping()
	public void addUserDetails(@RequestBody User user) {
		log.info("user data from frontend: " + user.toString());
		
	}

}
