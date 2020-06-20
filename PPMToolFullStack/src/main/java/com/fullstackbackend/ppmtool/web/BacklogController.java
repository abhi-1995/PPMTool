package com.fullstackbackend.ppmtool.web;

import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.domain.ProjectTask;
import com.fullstackbackend.ppmtool.services.MapValidationErrorService;
import com.fullstackbackend.ppmtool.services.ProjectTaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Operation(summary = "Add Project Task to BackLog")
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id,
                                                     Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null){
            return errorMap;
        }
        ProjectTask pTask = projectTaskService.addProjectTask(backlog_id,projectTask,principal.getName());
        return new ResponseEntity<>(pTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a Project Task by Backlog ID")
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id,Principal principal){
        return projectTaskService.findBacklogById(backlog_id,principal.getName());
    }

    @Operation(summary = "Get Project Task by Backlog ID and PT Sequence")
    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
        return new ResponseEntity<>(projectTask,HttpStatus.OK);
    }

    @Operation(summary = "Update the existing project task")
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                               BindingResult result, @PathVariable String backlog_id,
                                               @PathVariable String pt_id,Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null){
            return errorMap;
        }
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask,backlog_id,pt_id,principal.getName());

        return new ResponseEntity<>(updatedTask,HttpStatus.OK);
    }

    @Operation(summary = "Delete a project task by Backlog ID and Project task sequence ID")
    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        projectTaskService.deletePTByProjectSequence(backlog_id,pt_id,principal.getName());
        return new ResponseEntity<String>("Project Task '"+pt_id+"' was deleted successfully",HttpStatus.OK);
    }
}
