package com.utms.utmswebapi.services;

import com.utms.utmswebapi.entities.*;
import com.utms.utmswebapi.exceptions.NoSuchProjectException;
import com.utms.utmswebapi.models.ProjectDTO;
import com.utms.utmswebapi.models.SuiteDTO;
import com.utms.utmswebapi.models.TestDTO;
import com.utms.utmswebapi.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

    }

    public List<Project> getProjects() {
        return this.projectRepository.findAll();
    }

    public Project getProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }

    /**
     *
     * @param name
     * @param description
     * @return
     */
    public Project registerProjectByNameAndDescription(String name, String description) {
        Project project = this.projectRepository.findProjectByName(name);

        if (project != null) {
            project.setDescription(description);
            project.setName(name);
        } else {
            project = new Project();
            project.setName(name);
            project.setDescription(description);
        }
        this.projectRepository.save(project);
        return project;
    }

    public List<TestRun> getTestRunsByProjectId(String id) {
        Optional<Project> projectOptional = this.projectRepository.findById(id);

        if (projectOptional.isEmpty()) {
            throw new NoSuchProjectException(); //"Project with such id does not exist."
        }

        return projectOptional.get().getTestRuns();
    }

    public TestRun getTestRunDetails(String projectId, Long runId) {
        Optional<Project> projectOptional = this.projectRepository.findById(projectId);

        if (projectOptional.isEmpty()) {
            throw new NoSuchProjectException();
        }

        TestRun result = projectOptional.
                get().
                getTestRuns().
                stream().
                filter(testRun -> runId.equals(testRun.getRunId())).
                findAny().
                orElse(null);

        if (result == null) {
            throw new NoSuchProjectException();
        }

        return result;
    }

    public void createNewTestRun(ProjectDTO project) {
        String name = project.getProject().getName();
        Project projectByName = this.projectRepository.findProjectByName(name);

        if (projectByName == null) {
            throw new NoSuchProjectException();
        }

        //     1. Recreate test cases
        //     2. Recreate List of tests
        //     3. Recreate Suite
        //     4. Recreate List of Suites
        //     5. Make a TestRun and add the suites there

        List<TestSuite> suitesResult = new ArrayList<>();

        for (SuiteDTO suite : project.getSuites()) {
            TestSuite suiteEntity = new TestSuite();

            suiteEntity.setName(suite.getName());
            suiteEntity.setStatus(suite.getStatus());

            List<TestCase> entityTests = new ArrayList<>();
            for (TestDTO test : suite.getTests()) {
                TestCase testCase = new TestCase();

                testCase.setName(test.getName());
                testCase.setDescription(test.getDescription());
                testCase.setStatus(test.getStatus());
                testCase.setStartTime(test.getStartDate());
                testCase.setEndTime(test.getEndDate());
                testCase.setOutput(test.getOutput());
                testCase.setError(test.getError());
                testCase.setEnabled(test.isEnabled());// bug

                entityTests.add(testCase);
            }
            suiteEntity.setTests(entityTests);
            suitesResult.add(suiteEntity);
        }

        TestRun testRun = new TestRun();

        testRun.setStatus(project.getStatus());

        testRun.setRunId((long) projectByName.getTestRuns().size() + 1);
        testRun.setSuites(suitesResult);

        projectByName.getTestRuns().add(testRun);

        this.projectRepository.save(projectByName); // might throw some exception
    }
}
