package client;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Constants;
import utils.RequestBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// Class for handling Account API calls and store relevant data
public class Account {

    // Both needed for future API calls
    private String accountUid;
    private String categoryUid;

    public Account() {
        initalizeAccountUids();
    }

    // Method to fetch the necessary Aids needed for later endpoints
    public void initalizeAccountUids() {

        String url = Constants.BASE_URL + "/accounts";
        try {
            // Initiate the Http client for performing api calls
            HttpClient client = HttpClient.newHttpClient();
            RequestBuilder requestBuilder = new RequestBuilder();
            HttpRequest request = requestBuilder.buildGetRequest(url);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Following logic block extracts and stores desired data form JSON file given by request
            // Handle bad request
            if (response.statusCode() != HttpStatus.SC_OK) {
                System.out.println("Error: Response status code is not OK");
                return; // Early return to prevent nesting
            }

            // Parse JSON response
            JSONObject responseBody = new JSONObject(response.body());
            JSONArray accountArray = responseBody.getJSONArray("accounts");

            if (accountArray.isEmpty()) {
                // Handle empty account array
                System.out.println("No accounts found in response");
                return; // Early return to prevent nesting
            }

            // Retrieve our sandbox customers account details from JSON array
            JSONObject accountDetails = accountArray.getJSONObject(0);
            this.accountUid = accountDetails.optString("accountUid");
            this.categoryUid = accountDetails.optString("defaultCategory");

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Failed to intialize account and category UID's", e);
        }

    }
    // Getter for accountUid
    public String getAccountUid() {
        if (this.accountUid == null) {
            throw new IllegalStateException("accountUid has not been initialized");
        }
        return this.accountUid;
    }
    // Getter for categoryUid
    public String getCategoryUid() {
        if (this.categoryUid == null) {
            throw new IllegalStateException("categoryUid has not been initialized");
        }
        return this.categoryUid;
    }
}
