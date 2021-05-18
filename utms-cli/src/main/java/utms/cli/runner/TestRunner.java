package utms.cli.runner;

import utms.cli.enums.TestStatus;
import utms.cli.model.Project;
import utms.cli.model.Suite;
import utms.cli.model.Test;

import java.util.List;

public class TestRunner {

    private Project project;
    private CommandRunner commandRunner;

    public TestRunner(Project project, CommandRunner commandRunner) {
        this.project = project;
        this.commandRunner = commandRunner;
    }

    public List<Suite> runTests() {
        List<Suite> suites = project.getSuites();

        boolean overallTestsPass = true;

        for (Suite suite : suites) {
            List<Test> tests = suite.getTests();

            boolean suitePassed = true;

            for (Test test : tests) {
                if(test.isEnabled()) {
                    commandRunner.runTest(test);
                    if(test.getStatus().equals(TestStatus.FAILED.getStatus())) {
                        suitePassed = false;
                    }
                } else {
                    test.setStatus(TestStatus.SKIPPED.getStatus());
                }
            }
            if(suitePassed) {
                suite.setStatus(TestStatus.PASSED.getStatus());
            } else {
                overallTestsPass = false;
                suite.setStatus(TestStatus.FAILED.getStatus());
            }

        }

        if(overallTestsPass) {
            project.setStatus(TestStatus.PASSED.getStatus());
        } else {
            project.setStatus(TestStatus.FAILED.getStatus());
        }

        return suites;
    }
}
