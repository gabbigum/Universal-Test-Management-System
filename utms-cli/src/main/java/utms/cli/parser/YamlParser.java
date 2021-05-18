package utms.cli.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utms.cli.exceptions.InvalidYAMLFormatException;
import utms.cli.model.Project;
import utms.cli.model.ProjectModel;
import utms.cli.model.Suite;
import utms.cli.model.Test;

import java.util.*;

public class YamlParser {

    /**
     * Parses yaml string to json and creates Project object with the details
     * specified in the yaml file.
     * @param yaml
     * @return Project
     * @throws JsonProcessingException
     * @throws InvalidYAMLFormatException
     * @throws JSONException
     */
    public static Project parseYamlToProject(String yaml) throws JsonProcessingException, InvalidYAMLFormatException, JSONException {
        if(yaml == null) {
            throw new IllegalArgumentException("You cannot pass empty yaml string.");
        }
        String json = yamlToJson(yaml);
        Project project = new Project();

        JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (JSONException ex){
            throw new InvalidYAMLFormatException("The given yaml file format was incorrect. Please try reformatting the file.");
        }

        Iterator iterator = object.keys();
        while (iterator.hasNext()) {
            String mainKeys = (String) iterator.next();

            if (mainKeys.equals("project")) {
                JSONObject jsonProject = object.getJSONObject(mainKeys);

                ProjectModel projectModel = new ProjectModel();

                projectModel.setName(jsonProject.getString("name"));
                projectModel.setDescription(jsonProject.getString("description"));
                project.setProject(projectModel);

            } else if (mainKeys.equals("suites")) {
                JSONArray suites = object.getJSONArray(mainKeys);

                List<Suite> suitesModel = new ArrayList<>();
                // parse suites
                for (int i = 0; i < suites.length(); i++) {
                    JSONObject suiteJson = suites.getJSONObject(i);

                    Iterator suiteKeys = suiteJson.keys();

                    while (suiteKeys.hasNext()) {
                        String suiteKey = (String) suiteKeys.next();

                        Suite suite = new Suite();
                        suite.setName(suiteKey);
                        suitesModel.add(suite);

                        JSONArray suiteTests = suiteJson.getJSONArray(suiteKey);
                        List<Test> testsModel = new ArrayList<>();

                        for (int j = 0; j < suiteTests.length(); j++) {
                            JSONObject testsJson = suiteTests.getJSONObject(j);
                            // get the suite tests keys
                            Iterator testKeys = testsJson.keys();

                            while (testKeys.hasNext()) {
                                String testKey = (String) testKeys.next();
                                JSONObject testValues = testsJson.getJSONObject(testKey);

                                String description = null;
                                String command = testValues.getString("command");
                                boolean enabled = testValues.getBoolean("enabled");

                                try {
                                    description = testValues.getString("description");
                                } catch (JSONException ex) {

                                }
                                Test testModel = new Test();

                                testModel.setName(testKey);
                                testModel.setDescription(description);
                                testModel.setCommand(command);
                                testModel.setEnabled(enabled);

                                // add the new test to the tests collection
                                testsModel.add(testModel);
                            }
                        }
                        // Add tests to the suite
                        suite.setTests(testsModel);
                    }
                    project.setSuites(suitesModel);

                }
            }

        }
        return project;
    }

    /**
     * Parses yaml to JSON string.
     * @param yaml
     * @return json string
     * @throws JsonProcessingException
     */
    private static String yamlToJson(String yaml) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }
}
