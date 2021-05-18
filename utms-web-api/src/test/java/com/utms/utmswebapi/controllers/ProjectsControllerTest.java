package com.utms.utmswebapi.controllers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utms.utmswebapi.entities.Project;
import com.utms.utmswebapi.entities.TestRun;
import com.utms.utmswebapi.repositories.ProjectRepository;
import com.utms.utmswebapi.services.ProjectService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProjectsControllerTest {
    @Test
    public void testGetProjects() {
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        ArrayList<Project> projectList = new ArrayList<Project>();
        when(projectRepository.findAll()).thenReturn(projectList);
        List<Project> actualProjects = (new ProjectsController(new ProjectService(projectRepository))).getProjects();
        assertSame(projectList, actualProjects);
        assertTrue(actualProjects.isEmpty());
        verify(projectRepository).findAll();
    }

    @Test
    public void testGetProjectByName() {
        Project project = new Project();
        project.setId("42");
        project.setTestRuns(new ArrayList<TestRun>());
        project.setName("Name");
        project.setDescription("The characteristics of someone or something");
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findProjectByName(anyString())).thenReturn(project);
        assertSame(project, (new ProjectsController(new ProjectService(projectRepository))).getProjectByName("Name"));
        verify(projectRepository).findProjectByName(anyString());
    }

    @Test
    public void testRegisterProjectByNameAndDescription() {
        Project project = new Project();
        project.setId("42");
        project.setTestRuns(new ArrayList<TestRun>());
        project.setName("Name");
        project.setDescription("The characteristics of someone or something");

        Project project1 = new Project();
        project1.setId("42");
        project1.setTestRuns(new ArrayList<TestRun>());
        project1.setName("Name");
        project1.setDescription("The characteristics of someone or something");
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.save((Project) any())).thenReturn(project1);
        when(projectRepository.findProjectByName(anyString())).thenReturn(project);
        (new ProjectsController(new ProjectService(projectRepository))).registerProjectByNameAndDescription("42", "Name",
                "The characteristics of someone or something");
        verify(projectRepository).save((Project) any());
        verify(projectRepository).findProjectByName(anyString());
    }
}

