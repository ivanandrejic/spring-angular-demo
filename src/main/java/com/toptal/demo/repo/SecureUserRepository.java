package com.toptal.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.toptal.demo.domain.SecureUser;

@RepositoryRestResource(exported = false)
public interface SecureUserRepository extends PagingAndSortingRepository<SecureUser, Long> {
	
	@Override
	@PostFilter("(filterObject.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
	Iterable<SecureUser> findAll();
	
	@Override
	@PostFilter("(filterObject.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
	Iterable<SecureUser> findAll(Iterable<Long> ids);
	
	@Override
	@PostFilter("(filterObject.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
	Page<SecureUser> findAll(Pageable pageable);
	
	@Override
	@PostFilter("(filterObject.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
	Iterable<SecureUser> findAll(Sort sort);
	
	@Override
	@PostAuthorize("returnObject != null "
			+ "&& (hasRole('ROLE_ADMIN') "
			+ "|| (hasRole('ROLE_USER') && returnObject.name == authentication?.name)"
			+ "|| (hasRole('ROLE_USER_MANAGER') && returnObject.role != 'ROLE_ADMIN'))")
	SecureUser findOne(@Param("id")Long id);
	
	@RestResource(path = "name")
	SecureUser findByName(@Param("name") String name);
	
	@Override
	@PreAuthorize("(#user?.role != 'ROLE_ADMIN' && hasRole('ROLE_USER_MANAGER')) || hasRole('ROLE_ADMIN')")
	void delete(@Param("user") SecureUser user);
	
	@Override
	@RestResource(exported = false)
	void deleteAll();
	
	@Override
	@RestResource(exported = false)
	void delete(Iterable<? extends SecureUser> entities);
	
	@Override
	@RestResource(exported = false)
	void delete(Long id);
	
	

}
