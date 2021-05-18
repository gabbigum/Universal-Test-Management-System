package utms.cli.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;
import utms.cli.parser.YamlParser;
import utms.cli.runner.CommandRunner;
import utms.cli.runner.TestRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReportProducer {
    private Project project;
    private DateFormat dateFormat;

    public ReportProducer(Project project) {
        this.project = project;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
    }

    public ReportProducer(Project project, DateFormat dateFormat) {
        this.project = project;
        this.dateFormat = dateFormat;
    }

    /**
     * Generates JSON report based on project data.
     * @returns json report string
     */
    public String generateReport() {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());

            objectMapper.setDateFormat(dateFormat);

            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.project);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
