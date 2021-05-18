package utms.cli.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YamlParserTest {

    @Test(expected = InvalidYAMLFormatException.class)
    public void testIfYamlToProjectParsesInvalidYaml() throws InvalidYAMLFormatException, JSONException, JsonProcessingException {
        Project project = YamlParser.parseYamlToProject("invalid file");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfYamlToProjectParsesIfYamlStringIsNull() throws InvalidYAMLFormatException, JSONException, JsonProcessingException {
        Project project = YamlParser.parseYamlToProject(null);
    }

    @Test
    public void testIfYamlToProjectParsesValidYaml() throws InvalidYAMLFormatException, JSONException, JsonProcessingException {
        String yaml = "project:\n" +
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

        Project project = YamlParser.parseYamlToProject(yaml);

        String actualProjectName = project.getProject().getName();
        String expectedProjectName = "My Software Project";

        String actualSuiteName = project.getSuites().get(0).getName();
        String expectedSuiteName = "BackEndTests";

        String actualTestName = project.getSuites().get(0).getTests().get(0).getName();
        String expectedTestName = "Test1";

        boolean actualIsEnabled = project.getSuites().get(0).getTests().get(0).isEnabled();

        assertEquals("The name of the project must be My Software Project", expectedProjectName, actualProjectName);
        assertEquals("The name of the suite must be BackEndTests", expectedSuiteName, actualSuiteName);
        assertEquals("The name of the test must be Test1", expectedTestName, actualTestName);
        assertTrue("The test must be enabled",  actualIsEnabled);
    }

}
