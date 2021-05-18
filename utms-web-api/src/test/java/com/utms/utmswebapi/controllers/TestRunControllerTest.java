package com.utms.utmswebapi.controllers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.utms.utmswebapi.entities.Project;
import com.utms.utmswebapi.entities.TestRun;
import com.utms.utmswebapi.entities.TestSuite;
import com.utms.utmswebapi.models.ProjectDTO;
import com.utms.utmswebapi.repositories.ProjectRepository;
import com.utms.utmswebapi.services.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TestRunControllerTest {
    @Test
    public void testGetTestRunsByProjectId() {
        Project project = new Project();
        project.setId("42");
        ArrayList<TestRun> testRunList = new ArrayList<TestRun>();
        project.setTestRuns(testRunList);
        project.setName("Name");
        project.setDescription("The characteristics of someone or something");
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById(anyString())).thenReturn(Optional.<Project>of(project));
        List<TestRun> actualTestRunsByProjectId = (new TestRunController(new ProjectService(projectRepository)))
                .getTestRunsByProjectId("42");
        assertSame(testRunList, actualTestRunsByProjectId);
        assertTrue(actualTestRunsByProjectId.isEmpty());
        verify(projectRepository).findById(anyString());
    }

    @Test
    public void testCreateNewTestRun() {
        ProjectService projectService = mock(ProjectService.class);
        doNothing().when(projectService).createNewTestRun((ProjectDTO) any());
        TestRunController testRunController = new TestRunController(projectService);
        testRunController.createNewTestRun("42", new ProjectDTO());
        verify(projectService).createNewTestRun((ProjectDTO) any());
    }

    @Test
    public void testGetTestRunDetails2() {
        TestRun testRun = new TestRun();
        testRun.setSuites(new ArrayList<TestSuite>());
        testRun.setRunId(123L);
        testRun.setId(123L);

        ArrayList<TestRun> testRunList = new ArrayList<TestRun>();
        testRunList.add(testRun);

        Project project = new Project();
        project.setId("42");
        project.setTestRuns(testRunList);
        project.setName("Name");
        project.setDescription("The characteristics of someone or something");
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById(anyString())).thenReturn(Optional.<Project>of(project));
        assertSame(testRun,
                (new TestRunController(new ProjectService(projectRepository))).getTestRunDetails("myproject", 123L));
        verify(projectRepository).findById(anyString());
    }
}

