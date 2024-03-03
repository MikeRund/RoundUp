package client;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import utils.Constants;
import utils.RequestBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

// Class to handle savings goal API calls and store relevant data
public class SavingsGoalClient {

    public SavingsGoalClient() {
    }
    private String savingsGoalUid; // Necessary for future endpoint calls

    // Getter for Savings Goal UID
    public String getSavingsGoalUid() {
        if (this.savingsGoalUid == null) {
            throw new IllegalStateException("savingsGoalUid has not been initialized.");
        }
        return this.savingsGoalUid;
    }

    // Method to add money to existing saving goal
    public void addMoneyTosSavingsGoal(String savingsGoalUid, int amount) {
        // Generate a unique UID for the transactionUid needed for following endpoint
        UUID uuid = UUID.randomUUID();
        String transferId = uuid.toString();

        // Build url for adding money to savings goal endpoint
        Account account = new Account(); // Needed to obtain accountUid
        String url = Constants.BASE_URL + "/account/" + account.getAccountUid() + "/savings-goals/" + savingsGoalUid + "/add-money/" + transferId;

        // Preparing request body data
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", "GBP");
        amountObj.put("minorUnits", amount);
        JSONObject requestBody = new JSONObject();
        requestBody.put("amount", amountObj);

        try {
            // Initiate the Http client for performing API calls
            HttpClient client = HttpClient.newHttpClient();
            RequestBuilder requestBuilder = new RequestBuilder();
            HttpRequest request = requestBuilder.buildPUTRequest(url, HttpRequest.BodyPublishers.ofString(requestBody.toString()));
            client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Failed to add money to savings goal", e);
        }
    }

    // Method for creating a savings goal
    public void createSavingsGoal() {
        try {
            // Prepare the request body data
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", "Round-Up Savings");
            requestBody.put("currency", "GBP");

            // Initiate the Http client for performing API calls
            HttpClient client = HttpClient.newHttpClient();
            RequestBuilder requestBuilder = new RequestBuilder();

            // Build url for creating savings goal endpoint
            Account account = new Account(); // Needed for accountUid
            String url = Constants.BASE_URL + "/account/" + account.getAccountUid() + "/savings-goals";
            HttpRequest request = requestBuilder.buildPUTRequest(url, HttpRequest.BodyPublishers.ofString(requestBody.toString()));
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Extracting savingsGoalUid from JSON response
            if (response.statusCode() == HttpStatus.SC_OK) {
                JSONObject responseBody = new JSONObject(response.body());
                this.savingsGoalUid = responseBody.optString("savingsGoalUid");
            } else {
                System.out.println("Failed to retrieve account information. Status code: " + response.statusCode());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException("Failed to create savings goal", e);
        }
    }
}
