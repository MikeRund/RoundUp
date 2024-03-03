package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Properties;

// Class to handle Http requests
public class RequestBuilder {
    // Headers the same amongst all Http requests.
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_AGENT_HEADER = "User-agent";

    private static final String CONFIG_FILE = "src/main/resources/config.properties"; // Directory for config file
    private String accessToken;

    public RequestBuilder() {
        // Loading the access token from config.properties
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE);
            properties.load(fileInputStream);
            fileInputStream.close();
            accessToken = properties.getProperty("api.token");
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }

    // Method to create Http request builder
    private HttpRequest.Builder getRequestBuilder(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(ACCEPT_HEADER, "application/json")
                .header(CONTENT_TYPE_HEADER, "application/json")
                .header(AUTHORIZATION_HEADER, "Bearer " + accessToken)
                .header(USER_AGENT_HEADER, "Cody Maverick");
    }

    // Method to build GET request
    public HttpRequest buildGetRequest(String url) throws URISyntaxException {
        return getRequestBuilder(url)
                .GET()
                .build();
    }

    // Method o build POST request
    public HttpRequest buildPUTRequest(String url, HttpRequest.BodyPublisher postBody) throws URISyntaxException {
        return getRequestBuilder(url)
                .PUT(postBody)
                .build();
    }
}
