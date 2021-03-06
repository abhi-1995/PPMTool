package com.fullstackbackend.ppmtool.web;

import com.fullstackbackend.ppmtool.domain.Project;
import com.fullstackbackend.ppmtool.services.MapValidationErrorService;
import com.fullstackbackend.ppmtool.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController // = @Controller + @ResponseBody
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Operation(summary = "CREATE a new Project")
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @Operation(summary = "GET a Project BY ID")
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {

        Project project = projectService.findProjectByIdentifier(projectId.toUpperCase(),principal.getName());
        return new ResponseEntity<>(project,HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Projects")
    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @Operation(summary = "DELETE a project by ID")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId.toUpperCase(),principal.getName());
        return new ResponseEntity<String>("Project with ID: '"+projectId.toUpperCase()+"' was deleted successfully",HttpStatus.OK);
    }
}
