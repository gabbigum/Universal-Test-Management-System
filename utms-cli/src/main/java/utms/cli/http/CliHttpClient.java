package utms.cli.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CliHttpClient {

    private final String url;
    private HttpClient client;

    public CliHttpClient(String url) {
        this.url = url;
        client = HttpClient.newHttpClient();
    }

    /**
     * Sends POST request to register project with name and description.
     * @param name
     * @param description
     */
    public void registerProject(String name, String description) {
        String url = this.url + "/project?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&desc=" + URLEncoder.encode(description, StandardCharsets.UTF_8);

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends POST request to create new test run with specified json body.
     * @param body
     */
    public void createNewTestRun(String body) {
        String url = this.url + "/project/runs";
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .setHeader("Content-Type", "application/json")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }
}
