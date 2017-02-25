package com.toptal.demo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.toptal.demo.domain.SecureUser;

@RepositoryRestResource(exported = true)
public interface SecureUserRepository extends PagingAndSortingRepository<SecureUser, Long> {
	
	@RestResource(path = "name")
	SecureUser findByName(@Param("name") String name);

}
