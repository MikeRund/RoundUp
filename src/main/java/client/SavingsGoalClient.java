package client;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class SavingsGoalClient {

    private String savingsGoalUid;

    public String getSavingsGoalUid() {
        return this.savingsGoalUid;
    }

    public static void addMoneyTosSavingsGoal(String savingsGoalUid, int amount) {
        UUID uuid = UUID.randomUUID();
        final String transferId = uuid.toString();
        System.out.println("Transfer UID: " + transferId);
        final String URL = "https://api-sandbox.starlingbank.com/api/v2/account/902ed248-f5b6-40d9-a990-17b69defd6eb/savings-goals/" +
                savingsGoalUid + "/add-money/" + transferId;

        // Preparing request body data
        JSONObject amountObj = new JSONObject();
        amountObj.put("currency", "GBP");
        amountObj.put("minorUnits", amount);
        JSONObject requestBody = new JSONObject();
        requestBody.put("amount", amountObj);

        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSavingsGoal() {
        try {
            // Prepare the request body data
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", "Round-Up Savings");
            requestBody.put("currency", "GBP");

            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/account/902ed248-f5b6-40d9-a990-17b6" +
                            "9defd6eb/savings-goals"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());

            // Extracting savingsGoalUid from JSON response
            if (response.statusCode() == 200) {
                JSONObject responseBody = new JSONObject(response.body());
                this.savingsGoalUid = responseBody.optString("savingsGoalUid");
            } else {
                System.out.println("Failed to retrieve account information. Status code: " + response.statusCode());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSavingsGoal(String savingsGoalUid) {
        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/account/902ed248-f5b6-40d9-a990-17b69defd6eb/savings-goals/" + savingsGoalUid))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewAllSavingsGoals() {
        try {
            // Prepare the request body data
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", "Round-Up Savings");
            requestBody.put("currency", "GBP");

            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/account/902ed248-f5b6-40d9-a990-17b69defd6eb/savings-goals"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        deleteSavingsGoal("b40ba445-4d85-4cf4-bc7a-2948449d2d8b");
//        SavingGoalClient savingGoalClient = new SavingGoalClient();
////        savingGoalClient.createSavingsGoal();
//        addMoneyTosSavingsGoal("b40fc39e-b34a-4240-9662-03e9118f5676",12345);
        viewAllSavingsGoals();
    }
}
