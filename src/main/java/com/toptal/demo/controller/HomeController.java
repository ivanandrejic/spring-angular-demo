package com.toptal.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toptal.demo.domain.SecureUser;
import com.toptal.demo.repo.SecureUserRepository;

@RestController
public class HomeController {
	
	@Autowired
	private SecureUserRepository userRepo;

	@RequestMapping("/user")
	public ResponseEntity<?> user(Principal principal) {
		SecureUser user = userRepo.findByName(principal.getName());
		return ResponseEntity.ok(user != null ? user : principal);
	}


}
