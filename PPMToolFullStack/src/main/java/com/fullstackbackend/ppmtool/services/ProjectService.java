package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.Backlog;
import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.domain.User;
import com.fullstackbackend.ppmtool.exceptions.ProjectIdException;
import com.fullstackbackend.ppmtool.exceptions.ProjectNotFoundException;
import com.fullstackbackend.ppmtool.repositories.BacklogRepository;
import com.fullstackbackend.ppmtool.repositories.ProjectRepository;
import com.fullstackbackend.ppmtool.repositories.UserReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserReposiroty userReposiroty;

    public Project saveOrUpdateProject(Project project,String username){
        try {

            User user = userReposiroty.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID: '"+project.getProjectIdentifier().toUpperCase()+"' already exist");
        }

    }

    public Project findProjectByIdentifier(String projectId, String username){
        //Only want to return the project if the user looking for it is the owner
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if(project == null){
            throw new ProjectIdException("Project ID: '"+projectId+"' does not exist");
        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){
        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }
}
