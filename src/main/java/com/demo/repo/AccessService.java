package com.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.domain.SecureUser;
import com.demo.domain.TimeZone;

@Component
public class AccessService {
	
	@Autowired
	private SecureUserRepository userRepo;
	
	public String getUserName(TimeZone zone) {
		SecureUser findOne = null;
		try {
			findOne = userRepo.findOne(zone.getUserId());
		} catch(Exception e) {}
		return findOne != null ? findOne.getName() : "";
	}
	

}
