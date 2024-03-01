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

public class TransactionClient {

    private ArrayList<Integer> spendingList;

    public TransactionClient() {
        setSpendingList();
    }

    public ArrayList<Integer> getSpendingList() {
        return this.spendingList;
    }

    private void setSpendingList() {
        ArrayList<JSONObject> transactions = getTransactions();
        this.spendingList = calculateSpending(transactions);
    }

    private ArrayList<Integer> calculateSpending(ArrayList<JSONObject> transactions) {
        ArrayList<Integer> spendingList = new ArrayList<>();
        for (JSONObject transaction : transactions) {
            String direction = transaction.getString("direction");
            if (direction.equals("OUT")) {
                JSONObject payment = transaction.getJSONObject("amount");
                int amount = payment.getInt("minorUnits");
                spendingList.add(amount);
            }
        }
        return spendingList;
    }

    private ArrayList<JSONObject> getTransactions() {

        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            // Perform http request with transactions between given dates endpoint
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/feed/account/902ed248-f5b6-40d9-" +
                            "a990-17b69defd6eb/category/902ebc6d-b224-4f73-bf02-d8b45bcb75e3/transactions-between" +
                            "?minTransactionTimestamp=2024-02-20T12:34:56.000Z" +
                            "&maxTransactionTimestamp=2024-02-27T00:00:00.000Z"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AccessClient.TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            // View the response body
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());

            // Store feedItems in Array List
            JSONObject responseBody = new JSONObject(response.body());
            JSONArray feed = responseBody.getJSONArray("feedItems");
            ArrayList<JSONObject> transactions = new ArrayList<>();
            for (int i = 0; i < feed.length(); i++){
                transactions.add(feed.getJSONObject(i));
            }
            return transactions;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        TransactionClient transactionClient = new TransactionClient();
        ArrayList<Integer> spending = transactionClient.getSpendingList();
        for (int payment: spending) {
            System.out.println(payment);
        }


    }
}
