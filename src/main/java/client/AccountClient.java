package client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AccountClient {

    private String accountUid;

    public void setAccountUid() {

        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/accounts")) ///902ed248-f5b6-40d9-a990-17b69defd6eb/balance
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            // System.out.println(response.body());


            // Extracting accountUid from JSON response
            if (response.statusCode() == 200) {
                JSONObject responseBody = new JSONObject(response.body());
                JSONArray accountArray = responseBody.getJSONArray("accounts");

                if (!accountArray.isEmpty()) {
                    JSONObject accountDetails = accountArray.getJSONObject(0);
                    //                    System.out.println("Account UID: " + accountUid);
                    this.accountUid = accountDetails.optString("accountUid");
                } else {
                    System.out.println("No accounts found in response");
                }
            } else {
                System.out.println("Failed to retrieve account information. Status code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String getAccountUid() {
        if (accountUid == null) {
            System.out.println("Account information not available");
            return null;
        } else {
            System.out.println("Account UID: " + accountUid);
            return accountUid;
        }
    }

    public static void main(String[] args) {
        AccountClient accountClient = new AccountClient();
        accountClient.setAccountUid();
        accountClient.getAccountUid();
    }
}
