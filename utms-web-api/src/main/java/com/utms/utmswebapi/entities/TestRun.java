package com.utms.utmswebapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "suites")
@JsonIgnoreProperties("id")
public class TestRun {

    @Id
    @SequenceGenerator(
            name = "test_run_sequence",
            sequenceName = "test_run_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "test_run_sequence"
    )
    private Long id;

    private String status;

    private Long runId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestSuite> suites;

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public List<TestSuite> getSuites() {
        return suites;
    }

    public void setSuites(List<TestSuite> suites) {
        this.suites = suites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
