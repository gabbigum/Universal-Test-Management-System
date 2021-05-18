package utms.cli.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;
import utms.cli.parser.YamlParser;
import utms.cli.runner.CommandRunner;

public class UtmsFacadeTest {

    private UtmsFacade utmsFacade;
    private Project project;
    private CommandRunner commandRunner;

    @Before
    public void init() throws InvalidYAMLFormatException, JSONException, JsonProcessingException {
        project = YamlParser.parseYamlToProject("project:\n" +
        "  name: My Software Project\n" +
                "  description: The description of the project\n" +
                "suites:\n" +
                "  - BackEndTests:\n" +
                "      - Test1:\n" +
                "          enabled: true\n" +
                "          command: echo Test\n" +
                "  - UITests:\n" +
                "      - Test3:\n" +
                "          enabled: true\n" +
                "          command: echo Test 3\n" +
                "          description: Hello world\n");

        commandRunner = new CommandRunner();
        utmsFacade = new UtmsFacade(project, commandRunner);
    }

    @Test
    public void testIfUtmsFacadeProducesReport() {
        utmsFacade.runTests();
        utmsFacade.produceReport();
    }
}
