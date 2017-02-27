package com.toptal.demo.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.toptal.demo.domain.TimeZone;

@RepositoryRestResource(exported = true)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface TimeZoneRepository extends PagingAndSortingRepository<TimeZone, Long> {

	@Override
	@PostAuthorize("hasRole('ROLE_ADMIN') || (returnObject != null && @secureUserRepository.findOne(returnObject.userId).name == authentication?.name)")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	TimeZone findOne(@Param("id")Long id);
	
	@RestResource(path = "userId")
	@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @secureUserRepository.findOne(#userId).name == authentication?.name)")
	List<TimeZone> findByUserId(@Param("userId") Long userId);
	
	@Override
	@PreAuthorize("#entity?.userId != null && (hasRole('ROLE_ADMIN') || @secureUserRepository.findOne(#entity.userId).name == authentication?.name)")
	<S extends TimeZone> S save(@Param("entity")S entity);
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') || (#entity?.userId != null && @secureUserRepository.findOne(#entity.userId).name == authentication?.name)")
	void delete(@Param("entity")TimeZone entity);
	
}
