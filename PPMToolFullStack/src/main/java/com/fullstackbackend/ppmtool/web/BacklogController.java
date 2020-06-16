package com.fullstackbackend.ppmtool.web;

import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.domain.ProjectTask;
import com.fullstackbackend.ppmtool.services.MapValidationErrorService;
import com.fullstackbackend.ppmtool.services.ProjectTaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null){
            return errorMap;
        }
        ProjectTask pTask = projectTaskService.addProjectTask(backlog_id,projectTask);
        return new ResponseEntity<>(pTask, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findBacklogById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id);
        return new ResponseEntity<>(projectTask,HttpStatus.OK);
    }
}
