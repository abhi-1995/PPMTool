package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.Backlog;
import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.domain.ProjectTask;
import com.fullstackbackend.ppmtool.repositories.BacklogRepository;
import com.fullstackbackend.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //PT's to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //set bl to pt
        projectTask.setBacklog(backlog);

        //Project SEQ
        Integer BacklogSequence = backlog.getPTSequence();
        //UPDATE BL SEQ
        BacklogSequence++;

        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority when priority null
//        if(projectTask.getPriority()==0 || projectTask.getPriority()==null){
//            projectTask.setPriority(3);
//        }

        //INITIAL Status when Status is null
        if(projectTask.getStatus()==""||projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);

    }
}
