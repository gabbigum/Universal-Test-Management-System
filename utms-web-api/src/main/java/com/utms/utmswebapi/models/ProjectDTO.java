package com.utms.utmswebapi.models;

import java.util.List;

public class ProjectDTO {
    private Long runId;
    private ProjectModelDTO project;
    private String status;
    private List<SuiteDTO> suites;

    public ProjectModelDTO getProject() {
        return project;
    }

    public void setProject(ProjectModelDTO project) {
        this.project = project;
    }

    public List<SuiteDTO> getSuites() {
        return suites;
    }

    public void setSuites(List<SuiteDTO> suites) {
        this.suites = suites;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project=" + project +
                ", suites=" + suites +
                '}';
    }
}
