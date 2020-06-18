package com.fullstackbackend.ppmtool.repositories;

import com.fullstackbackend.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposiroty extends CrudRepository<User, Long> {

}
