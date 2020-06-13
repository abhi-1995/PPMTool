package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        return projectRepository.save(project);
    }
}
