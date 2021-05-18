package com.utms.utmswebapi.controllers;

import com.utms.utmswebapi.entities.Project;
import com.utms.utmswebapi.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public List<Project> getProjects() {
        return this.projectService.getProjects();
    }

    // this will be used by the cli
    @GetMapping("/{project_name}")
    public Project getProjectByName(@PathVariable("project_name") String name) {
        return this.projectService.getProjectByName(name);
    }

    // this will be used by the cli
    @PostMapping("/{project_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerProjectByNameAndDescription(
            @PathVariable("project_id") String id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "desc") String description) {
        this.projectService.registerProjectByNameAndDescription(name, description);
    }
}
