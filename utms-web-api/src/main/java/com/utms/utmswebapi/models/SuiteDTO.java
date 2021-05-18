package com.utms.utmswebapi.models;

import java.util.List;


public class SuiteDTO {
    private String name;
    private String status;
    private List<TestDTO> tests;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TestDTO> getTests() {
        return tests;
    }

    public void setTests(List<TestDTO> tests) {
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
