package com.utms.utmswebapi.services;

import com.utms.utmswebapi.entities.Project;
import com.utms.utmswebapi.exceptions.NoSuchProjectException;
import com.utms.utmswebapi.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProjectService.class})
@ExtendWith(SpringExtension.class)
public class ProjectServiceTest {


    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @Before
    public void init() {
        this.projectRepository = Mockito.mock(ProjectRepository.class);
        this.projectService = new ProjectService(projectRepository);
    }

    @Test(expected = NoSuchProjectException.class)
    public void testIfTestRunsByProjectIdThrowExceptionWhenSuchProjectDoesNotExist() {
        this.projectService.getTestRunsByProjectId(anyString());
    }

    @Test
    @Ignore
    public void testIfTestRunsByProjectIdRunsSuccessfully() {
        when(this.projectService.getTestRunsByProjectId(anyString())).thenReturn(any());
        verify(projectRepository).findProjectByName(anyString());
    }

    @Test
    public void testIfRegisterProjectByNameAndDescriptionUpdatesExistingProject() {
        Project p = new Project();
        p.setName("name");
        p.setDescription("description");

        when(projectRepository.findProjectByName(anyString())).thenReturn(p);

        projectService.registerProjectByNameAndDescription("name", "change the desc");

        String expectedDescription = "change the desc";
        String actualDescription = p.getDescription();

        verify(projectRepository).save(p);
        assertEquals(expectedDescription, actualDescription);
    }

    /*@Test
    public void testIfRegisterProjectByNameAndDescriptionCreatesNewProjectIfItDoesntExist() {
        when(projectRepository.findProjectByName(anyString())).thenReturn(null);
        verify(projectRepository).save(any());
    }*/

    @Test (expected = NoSuchProjectException.class)
    public void testIfGetTestRunsByProjectIdThrowsNoSuchProjectException() {
        projectService.getTestRunsByProjectId(anyString());
    }
}
