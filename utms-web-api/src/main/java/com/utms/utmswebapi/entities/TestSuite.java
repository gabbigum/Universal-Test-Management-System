package com.utms.utmswebapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "suite")
@JsonIgnoreProperties("id")
public class TestSuite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestCase> tests;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TestCase> getTests() {
        return tests;
    }

    public void setTests(List<TestCase> tests) {
        this.tests = tests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
