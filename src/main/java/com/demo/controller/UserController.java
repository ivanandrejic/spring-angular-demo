package com.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.SecureUser;
import com.demo.repo.SecureUserRepository;

@RepositoryRestController
@RequestMapping("/rest/users")
public class UserController {
	
//	TODO check ADMIN access
	
	@Autowired
	private SecureUserRepository secureUserRepo;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER_MANAGER')")
    public ResponseEntity<?> getAll(Principal principal) {
		Iterable<SecureUser> all = secureUserRepo.findAll();
		
		List<Resource<SecureUser>> usersResources = new ArrayList<Resource<SecureUser>>();
		StreamSupport.stream(all.spliterator(), false).forEach((user) -> {
			Resource<SecureUser> resource = new Resource<SecureUser>(user);
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).get(user.getId())).withSelfRel());
			usersResources.add(resource);
		});
		
		Resources<Resource<SecureUser>> resources = new Resources<Resource<SecureUser>>(usersResources); 
		resources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).getAll(principal)).withSelfRel());
		
		return ResponseEntity.ok(resources);
    }
	
	@RequestMapping(value = "/search/name/{name}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER_MANAGER')")
	@PostAuthorize("returnObject.getBody() == null || (returnObject.getBody().getContent().role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findByName(@PathVariable String name) {
		SecureUser user = secureUserRepo.findByName(name);
		
		ResponseEntity<?> result; 
		if (user == null) {
			result = ResponseEntity.ok(null);
		} else {			
			Resource<SecureUser> resource = new Resource<SecureUser>(user);
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).findByName(name)).withSelfRel());
			result = ResponseEntity.ok(resource);
		}
		
		return result;
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
		
		SecureUser user = secureUserRepo.findOne(id);
		Resource<SecureUser> resource = new Resource<SecureUser>(user);
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).get(id)).withSelfRel());
		
		return ResponseEntity.ok(resource);
    }
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "|| (hasRole('ROLE_CREATE_USER') && #user?.role == 'ROLE_USER') "
			+ "|| (#user?.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER'))")
    public @ResponseBody ResponseEntity<?> post(@RequestBody SecureUser user) {
		
		if (user == null || StringUtils.isEmpty(user.getName())) {
			throw new IllegalArgumentException("User object not valid.");
		}
		
		SecureUser searchUser = secureUserRepo.findByName(user.getName());
		
		if (searchUser != null) {
			throw new IllegalArgumentException("User name already defined.");
		}
		
		if (StringUtils.isEmpty(user.getPassword())){			
			user.setPassword(SecureUser.PASSWORD_ENCODER.encode(SecureUser.DEFAULT_PASSWORD));
		} else {
			user.setPassword(SecureUser.PASSWORD_ENCODER.encode(user.getPassword()));
		}
		
		SecureUser newUser = secureUserRepo.save(user);
		
		Resource<SecureUser> resource = new Resource<SecureUser>(newUser);
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).get(user.getId())).withSelfRel());
		
		return ResponseEntity.ok(resource);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "|| (hasRole('ROLE_USER') && #user?.name == authentication?.name && #user?.role == 'ROLE_USER') "
			+ "|| (hasRole('ROLE_USER_MANAGER') && #user?.role != 'ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<?> put(@PathVariable String id, @RequestBody SecureUser user) {
		
		if (user == null || StringUtils.isEmpty(user.getName())) {
			throw new IllegalArgumentException("User object not valid.");
		}
		
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("ID not valid.");
		}
		
		if (StringUtils.isEmpty(user.getPassword())){
			SecureUser oldUser = secureUserRepo.findOne(user.getId());
			user.setPassword(oldUser.getPassword());
		} else if (!isEncoded(user.getPassword())) {		
			user.setPassword(SecureUser.PASSWORD_ENCODER.encode(user.getPassword()));
		}
		
		SecureUser newUser = secureUserRepo.save(user);
		Resource<SecureUser> resource = new Resource<SecureUser>(newUser);
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).get(user.getId())).withSelfRel());
		
		return ResponseEntity.ok(resource);
    }
	
	private boolean isEncoded(String password) {
		return password.length() > 20 && '$' == password.charAt(0);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void delete(@PathVariable Long id) {
		SecureUser oldUser = secureUserRepo.findOne(id);
		secureUserRepo.delete(oldUser);
    }

}
