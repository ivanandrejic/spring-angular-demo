package com.toptal.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.toptal.demo.domain.SecureUser;
import com.toptal.demo.repo.SecureUserRepository;

@Component
public class LocalUserDetailsService implements UserDetailsService {

	@Autowired
	private SecureUserRepository userRepo;


	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		SecureUser user = userRepo.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException("User " + name + "does not exist.");
		}
		return new User(user.getName(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRole()));
	}

}
