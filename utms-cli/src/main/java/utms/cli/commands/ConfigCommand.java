package utms.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import picocli.CommandLine;
import utms.cli.enums.ErrorCodes;
import utms.cli.errors.ErrorHandler;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.facade.UtmsFacade;
import utms.cli.http.CliHttpClient;
import utms.cli.model.Project;
import utms.cli.parser.YamlParser;
import utms.cli.runner.CommandRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class contains the core CLI logic.
 */
public class ConfigCommand implements Runnable {

    @CommandLine.Option(names = {"--config", "-c"})
    private String filename = "testing.yaml";

    @CommandLine.Option(names = {"--server", "-s"})
    private String server;

    @CommandLine.Option(names = {"--debug", "-d"})
    private boolean debugOn = false;

    @Override
    public void run() {
        ErrorHandler errorHandler = new ErrorHandler(new ObjectMapper());

        String fileData;
        try {
            fileData = Files.readString(Path.of(filename));
        } catch (IOException ex) {
            String errorMessage = errorHandler.handleError(ErrorCodes.FILE_NOT_FOUND.getMessage());
            System.out.println(errorMessage);
            return;
        }

        Project project = null;
        try {
            project = YamlParser.parseYamlToProject(fileData);
        } catch (InvalidYAMLFormatException | JsonProcessingException e) {
            String errorMessage = errorHandler.handleError(ErrorCodes.INVALID_FILE.getMessage());
            System.out.println(errorMessage);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommandRunner runner = new CommandRunner();

        UtmsFacade app = new UtmsFacade(
                project,
                runner
        );

        app.runTests();
        String report = app.produceReport();

        if (debugOn) {
            System.out.println(report);
        } else if (server != null) {
            CliHttpClient http = new CliHttpClient(server);

            http.registerProject(project.getProject().getName(), project.getProject().getDescription());
            http.createNewTestRun(report);
        }
    }
}
