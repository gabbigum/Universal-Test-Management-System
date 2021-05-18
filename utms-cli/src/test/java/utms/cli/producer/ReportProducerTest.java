package utms.cli.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;
import utms.cli.parser.YamlParser;

import static org.junit.Assert.assertEquals;

public class ReportProducerTest {

    private Project project;
    private ReportProducer reportProducer;

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
        reportProducer = new ReportProducer(project);
    }

    @Test
    public void testIfReportProducerGeneratesReport() {
        String actual = reportProducer.generateReport();
        String expected = "{\n" +
                "  \"runId\" : null,\n" +
                "  \"project\" : {\n" +
                "    \"name\" : \"My Software Project\",\n" +
                "    \"description\" : \"The description of the project\"\n" +
                "  },\n" +
                "  \"status\" : null,\n" +
                "  \"suites\" : [ {\n" +
                "    \"name\" : \"BackEndTests\",\n" +
                "    \"status\" : null,\n" +
                "    \"tests\" : [ {\n" +
                "      \"name\" : \"Test1\",\n" +
                "      \"description\" : null,\n" +
                "      \"enabled\" : true,\n" +
                "      \"output\" : null,\n" +
                "      \"error\" : null,\n" +
                "      \"status\" : null,\n" +
                "      \"startDate\" : null,\n" +
                "      \"endDate\" : null\n" +
                "    } ]\n" +
                "  }, {\n" +
                "    \"name\" : \"UITests\",\n" +
                "    \"status\" : null,\n" +
                "    \"tests\" : [ {\n" +
                "      \"name\" : \"Test3\",\n" +
                "      \"description\" : \"Hello world\",\n" +
                "      \"enabled\" : true,\n" +
                "      \"output\" : null,\n" +
                "      \"error\" : null,\n" +
                "      \"status\" : null,\n" +
                "      \"startDate\" : null,\n" +
                "      \"endDate\" : null\n" +
                "    } ]\n" +
                "  } ]\n" +
                "}";
        assertEquals(expected, actual);
    }

}
