package com.toptal.demo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.toptal.demo.domain.TimeZone;

@RepositoryRestResource(exported = true)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface TimeZoneRepository extends PagingAndSortingRepository<TimeZone, Long> {

	@Override
	@PostAuthorize("hasRole('ROLE_ADMIN') || (returnObject != null && returnObject.user.name == authentication?.name)")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	TimeZone findOne(@Param("id")Long id);
	
	@Override
	@PreAuthorize("#entity?.user != null && (hasRole('ROLE_ADMIN') || #entity?.user.name == authentication?.name)")
	<S extends TimeZone> S save(@Param("entity")S entity);
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') || (#entity?.user != null && #entity?.user.name == authentication?.name)")
	void delete(@Param("entity")TimeZone entity);
	
}
