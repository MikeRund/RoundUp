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

public class Transactions {

    public List<JSONObject> feedItems;

    public Transactions() {
        this.feedItems = new ArrayList<>();
    }

    public void retrieveFeedItems() {

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
                    .header("Authorization", "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty27jMAz8lcLnsvAzftx62x_YD6ApKhFqS4Ykp1ss9t9Xjuw4DnLLzJCcoan8TZRzSZfgpEDwaD6cRzsofe5Rf32QGZP3xM19qGjTnFPZS8CccyjrtIW27ys4pW1KIm-LvklDMf-Zki4LalY0VdG-Jwp9JNIqzxYCicys_S8zCLa_lVhnMzcZiPaUQZk1PTRIORRZWlJWoihpme3NF-vYgfkJJTWhpK4rKKu-h56IQOailKe6bNqyCR1hrU8idm73acu2hloUwYcXn75mqLApkNPsVKBYFiYz8WISk8LlFhU0jtxZRvH2JPif6UlQgrVXUrE98oNy_sCsQAgbQnYslL-DqHiPdBn5Xrnjb6s8v-HsL8YqF04GSgt1VWLGIRb3OKCmNRqhFUBGe2uGaLQwq2a0VHZEr4wGI0HOWqwBaHbejNsePKJau0fUAj13ggcOOTZ4KxvZY0DYUYCLuOFb54Q_zJsUwTokgr0I1IjndWbU9p_gLWqHtGS-0zAYCtvvsyMBZvkMz-zaZY1Uw2YVvQ_UrcoysZr8AbijFO_h8BpO4eBs9hwHbl31wN3mPDJxORk--4sRu_hi1i7GoXRhMQ8sIKy9PyPH3ocF52mFE27PJPz_wysKj8lY8WB_ZDffI_uiH8y3vvOelwBA7vpMTUJG6vGmt1M8Hzn59x_GHTj8sgQAAA.th7AQ8rq-JRdKjwIHM2fqSOq9QqD3T5Wlwfi6578fWWhtZJarNYSm8jPzuJ-wNxeuBhSpNSBzVOYoCct7uU-BMYI9Wvh-UcqJb0c3cj21_uheUDG8OayPkfV3GAk_4eo35257exWZIyabdmkvOIXKAWtQVYivKX2aRWwLhbc70mPiNs2Eo3HQUPUMTbGPEY7qb41e1pF-ZZUPFb9z9vhK1fMgNNzJ_NMeBlnlRe4pcDsYbBeTdFtYN5FCqyWAFDvf9DM4fcT9go3ZoqUK83n1cQOlL-iTqTSq--h-gXqZvLWNxT1ZP0f8c7-ejHzkESa7P6ML3OooTZsv6r0_Ufnlpz9v7Pf8MdHo1HtCarh0lrM1O-qhOubP2DTDT7j6gL8OoPUBpgvyh-62xME_LvAYqAUIUJFltvjzaS-mgGingFZTlWOIKAvNrJTcg6EZfBh33Ot5_yiCsuE2pjK3XYCzG00ZwHSxeisL8rn-lHcUBDc9FSxSQGMEJXhzo4Rv5TAeJ24Ire8niRPl4WCbZygTVGDEwnLeIEEY2CP6ZY8eAmpfVbSeyxZh0SWwdZxV-m0PNwdrFty_aRv9EdXrPiWawFP7103JfJiMsIuI_NoTuwimbHgsT51eyAg1ufJXSIxFycUApAyrzNPqLZhnTph-xRJcWdzLr5tFS8PNZ_JgfU")
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            // View the response body
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());

            // Store feedItems as field variable of Transactions instance
            JSONObject responseBody = new JSONObject(response.body());
            JSONArray feed = responseBody.getJSONArray("feedItems");
            for (int i = 0; i < feed.length(); i++){
                this.feedItems.add(feed.getJSONObject(i));
            }


        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Transactions transactions = new Transactions();
        transactions.retrieveFeedItems();
        for (JSONObject feedItem: transactions.feedItems) {
            System.out.println(feedItem.toString());
        }
    }
}
