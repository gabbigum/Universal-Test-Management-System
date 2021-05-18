package utms.cli.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utms.cli.enums.TestStatus;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;
import utms.cli.model.Suite;
import utms.cli.parser.YamlParser;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestRunnerTest {

    private static final String exampleYaml = "project:\n" +
            "  name: My Software Project\n" +
            "  description: The description of the project\n" +
            "suites:\n" +
            "  - BackEndTests:\n" +
            "      - Test1:\n" +
            "          enabled: true\n" +
            "          command: echo Test\n" +
            "      - Test2:\n" +
            "          enabled: true\n" +
            "          command: echo Test 2\n" +
            "          description: This test does something too\n" +
            "  - UITests:\n" +
            "      - Test3:\n" +
            "          enabled: true\n" +
            "          command: echo Test 3\n" +
            "          description: Hello world\n" +
            "      - Test4:\n" +
            "          enabled: false\n" +
            "          command: echo Test 4\n";

    private TestRunner testRunner;
    private CommandRunner commandRunner;
    private Project project;

    @Before
    public void init() throws InvalidYAMLFormatException, JSONException, JsonProcessingException {
        commandRunner = new CommandRunner();
        project = YamlParser.parseYamlToProject(exampleYaml);
        testRunner = new TestRunner(project, commandRunner);
    }

    @Test
    public void testIfTestRunnerSetsStatusProjectToFailedOnIllegalTest() {
        project.getSuites().get(0).getTests().get(0).setCommand("echoo test"); // illegal command
        List<Suite> result = testRunner.runTests();

        String expected = TestStatus.FAILED.getStatus();
        String actual = project.getStatus();

        assertEquals("Project status should be failed.", expected, actual);
    }

    @Test
    public void testIfTestRunnerSetsStatusProjectToPassedWhenTestsAreOkay() {
        List<Suite> result = testRunner.runTests();

        String expected = TestStatus.PASSED.getStatus();
        String actual = project.getStatus();

        assertEquals("Project status should be failed.", expected, actual);
    }
}
