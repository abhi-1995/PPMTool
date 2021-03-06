package com.fullstackbackend.ppmtool.services;

import com.fullstackbackend.ppmtool.domain.Backlog;
import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.domain.ProjectTask;
import com.fullstackbackend.ppmtool.exceptions.ProjectNotFoundException;
import com.fullstackbackend.ppmtool.repositories.BacklogRepository;
import com.fullstackbackend.ppmtool.repositories.ProjectRepository;
import com.fullstackbackend.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

            //PT's to be added to a specific project, project != null, BL exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);

            //set bl to pt
            projectTask.setBacklog(backlog);

            //Project SEQ
            Integer BacklogSequence = backlog.getPTSequence();
            //UPDATE BL SEQ
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null
            if(projectTask.getPriority()==null || projectTask.getPriority()==0){
                projectTask.setPriority(3);
            }

            //INITIAL Status when Status is null
            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);



    }

    public Iterable<ProjectTask> findBacklogById(String id, String username){
//        Project project = projectRepository.findByProjectIdentifier(id);
//        if(project == null){
//            throw new ProjectNotFoundException("Project with id: '"+id+"' does not exist");
//        }
        projectService.findProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id, String username){
        //make sure we are searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id,username);
        //make sure our task exists
        ProjectTask projectTask= projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }
        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: "+backlog_id);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask,String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        projectTaskRepository.delete(projectTask);
    }
}
