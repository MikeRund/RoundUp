package client;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Constants;
import utils.RequestBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

// Class to handle Transaction API calls and store relevant data
public class TransactionClient {
    private ArrayList<Integer> spendingList; // Necessary for calculating round up amount

    public TransactionClient() {
        intializeSpendingList();
    }

    // Getter for spendingList
    public ArrayList<Integer> getSpendingList() {
        if (this.spendingList == null) {
            throw new IllegalStateException("spendingList has not been initialized.");
        }
        return this.spendingList;
    }

    // Initialize spending list from fetchSpendingList() method. This is done on TransactionClient creation
    private void intializeSpendingList() {
        this.spendingList = fetchSpendingList();
    }

    // Method to perform api call and retrieve outgoing payments from sandbox customers account
    private ArrayList<Integer> fetchSpendingList() {

        try {
            // Initiate the Http client for performing API calls
            HttpClient client = HttpClient.newHttpClient();
            RequestBuilder requestBuilder = new RequestBuilder();
            Account account = new Account(); // Account instance needed to obtain accountUid

            // Build url for endpoint that fetches transactions for 1 weeks activity of customers account
            String url = Constants.BASE_URL + "/feed/account/" + account.getAccountUid() + "/category/" + account.getCategoryUid()
                    + "/transactions-between?minTransactionTimestamp=" + Constants.LAST_FRIDAY_TIMESTAMP + "&maxTransactionTimestamp="
                    + Constants.THIS_FRIDAY_TIMESTAMP;
            HttpRequest request = requestBuilder.buildGetRequest(url);

            // Store requests response for data extraction
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
;
            // Store feedItems of customers transaction in JSON array. Initilize integer arraylist for storing outgoing payments
            JSONObject responseBody = new JSONObject(response.body());
            JSONArray feed = responseBody.getJSONArray("feedItems");
            ArrayList<Integer> spending = new ArrayList<>();

            // Iterate through JSON array and extract only outgoing payments as integers
            for (int i = 0; i < feed.length(); i++){
                JSONObject transactionOBJ = feed.getJSONObject(i);
                String direction = transactionOBJ.getString("direction");
                if (direction.equals("OUT")) {
                    int amount = transactionOBJ.getJSONObject("amount").getInt("minorUnits");
                    spending.add(amount);
                }
            }
            return spending;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            // TODO - this should be handled, or at least logged.
            throw new RuntimeException("Failed to fetch spending list from sandbox API", e);
        }
    }
}
