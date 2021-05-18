package com.utms.utmswebapi.entities;

import com.utms.utmswebapi.uidgenerator.StringPrefixSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "project_seq"
    )
    @GenericGenerator(
            name = "project_seq",
            strategy = "com.utms.utmswebapi.uidgenerator.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "project_"),
            }
    )
    private String id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestRun> testRuns;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }
}
