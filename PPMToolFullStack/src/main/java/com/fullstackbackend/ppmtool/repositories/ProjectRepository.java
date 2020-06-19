package com.fullstackbackend.ppmtool.repositories;

import com.fullstackbackend.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);
}
