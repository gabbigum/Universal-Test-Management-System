package com.utms.utmswebapi.controllers;

import com.utms.utmswebapi.entities.TestRun;
import com.utms.utmswebapi.models.ProjectDTO;
import com.utms.utmswebapi.services.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class TestRunController {

    private ProjectService projectService;

    public TestRunController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{project_id}/runs")
    public List<TestRun> getTestRunsByProjectId(@PathVariable(name = "project_id") String id) {
        return this.projectService.getTestRunsByProjectId(id);
    }

    @PostMapping("/{project_id}/runs")
    public void createNewTestRun(@PathVariable(name = "project_id") String id, @RequestBody ProjectDTO project) {
        this.projectService.createNewTestRun(project);
    }

    // TODO test this fully after the integration
    @GetMapping("/{project_id}/runs/{run_id}")
    public TestRun getTestRunDetails(@PathVariable(name = "project_id") String projectId,
                                     @PathVariable(name = "run_id") Long runId) {
        return this.projectService.getTestRunDetails(projectId, runId);
    }

}
