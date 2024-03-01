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

public class Accounts {

    public List<JSONObject> accounts;

    public Accounts() {
        this.accounts = new ArrayList<>();
    }

    private void retrieveAccountInformation() {
        String token = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty5KjMAz8lSnOoykeJgFuc9sf2A8QskhcAzZlm8xObe2_r8EQQio3uluPliX-Jsq5pElwVCB5MB_Oo-2VvrSovz7IDMl74qY2RNRpzmnXdoA55yDOaQ1125ZwSuuUZF4XbZWGYP4zJk0W1LxMC1G-Jwp9JLJTUc8EEplJ-1-ml2x_K7nWZq4ykPUpA5FVLVRIORRZKigTKAXNtb35Yh0zsG6zsqpyoIplcFMKqElUkAou264SAosiZISxPonYub1PLeoznGUR-vDcpz0zlFgVyGlwiHIemMzIc5PoFK6LVdA4cGMZ5duT4H_GJ0FJ1l51iu2R75XzB2YFUtpgsmGp_B1ExXuk68D3yB1_W-X5DSd_NVa5sDJQWqqbkhP2MbjFHjWt1gitBDLaW9PHRjOzakZ3yg7oldFgOugmLVcDNDlvhm0OHlCt2QNqiZ4byT0HHxtcwgb2GBA2FOAsbnjJHPGHeZMiWItEsAeBGvCy1oza_gneonZIs-c7Db2hMP1eOxJg5md4ZtcsazrVb61i7wO1RFkmVqM_AHeU4j4c3sIqHFzM7uPAraMeuKXOIxOH68Kzvyixiy9q7WIsSleWUx_-kTD2fkaOvQ8DTuMKR9zOJPz_4YrCMRkrH9of2a3vkX2RD-Zb33nPswEgd3umRtlF6nGnyyqel5z8-w-Gz27usgQAAA.evENJoxDbwCpYTfzRAVnLVvSzt49ItOtV9nK4mp49KRGj-PJ-6XfX0eoxL9Vm74ds2WRgujqbXkgW-KXGH7XhyJ5bPmvuSGR4D3Vp11FBXcAHd9RMR0W0q-mD4eLL6kAm6eNxlELwbTNku533mEXzaLr1NdwOaJfzL_TcV_YPbaeim6zcvVyuTmApLUQq5kMr3qOeVQwZHZOPdTxFNF3N8fJjxzTa-mhdBkX63iNCH3I7Yn57Xl47-ZkmATS_cxBh290AnbtIiIBtxWghGsIWI99I_abddzwhawz2kBsvN4b_tCZEDnms742_EQF0gixDlJ4KSxnjcT2j226em_UEIhhPLMUNUt8hhjALDId1fbRtQzSWeUT12LqidHGixeZ_-JkYtfR_tCSqpcRX8g2upVNPw3wu4GuPSv_jDtoR665S41TcFgArnvOgyHuj6_DJByueIYOWrY3dCv8D1DaQ21xCXC4NTZPnF9s5eJciN2mweCR9U0m_cOzA1RmmNmn7WqdQ1Q6XBNZnROgvhyXE5nlPEqJzFwzWJ-hmaTbaW7K5tA9AwYLkGU_8PG_N9NchOdECjnQG2IE60DCPBas_5eVaEUAo8Qxu_g7KaMJWYGMlzsA5BKRAPjqbpR10udoiry5k4wP7ur3H65trpHtUTW_V_fc6e6X8YJUqhLtsXY";

        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/accounts")) ///902ed248-f5b6-40d9-a990-17b69defd6eb/balance
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());


            // Extracting JSONArray from response and storing as field variable of Accounts instance
            JSONObject responseBody = new JSONObject(response.body());
            JSONArray accounts = responseBody.getJSONArray("accounts");
            for (int i = 0; i < accounts.length(); i++) {
                this.accounts.add(accounts.getJSONObject(i));
            }
//            JSONObject firstAccount = accounts.getJSONObject(0);
//            String accountUID = firstAccount.optString("accountUid");
//            System.out.println("ID: " + accountUID);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Accounts accounts = new Accounts();
        accounts.retrieveAccountInformation();
        List<JSONObject> accountList = accounts.accounts;
        for (JSONObject acc: accountList) {
            System.out.println(acc.toString());
        }
    }
}
