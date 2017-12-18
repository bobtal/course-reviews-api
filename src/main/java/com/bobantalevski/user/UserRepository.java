package com.bobantalevski.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Don't expose users to the API
@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}
