package utms.cli.facade;

import utms.cli.model.Project;
import utms.cli.producer.ReportProducer;
import utms.cli.runner.CommandRunner;
import utms.cli.runner.TestRunner;

public class UtmsFacade implements UtmsFacadeAPI {

    private CommandRunner commandRunner;
    private TestRunner testRunner;
    private Project project;
    private ReportProducer reportProducer;

    public UtmsFacade(Project project, CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
        this.project = project;
        this.testRunner = new TestRunner(project, commandRunner);
        this.reportProducer = new ReportProducer(project);
    }

    /**
     * Runs tests
     */
    @Override
    public void runTests() {
        this.testRunner.runTests();
    }

    @Override
    public String produceReport() {
        return reportProducer.generateReport();
    }
}
