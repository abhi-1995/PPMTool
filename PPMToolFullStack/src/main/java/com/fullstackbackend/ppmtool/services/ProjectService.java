package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.exceptions.ProjectIdException;
import com.fullstackbackend.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;



    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID: '"+project.getProjectIdentifier().toUpperCase()+"' already exist");
        }

    }
}
