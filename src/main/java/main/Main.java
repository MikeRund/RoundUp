package main;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {

        // accountUid: 902ed248-f5b6-40d9-a990-17b69defd6eb
        // category: 902ebc6d-b224-4f73-bf02-d8b45bcb75e3
        final String TOKEN = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty5KjMAz8lSnOoyleCY_b3PYH9gOELBLXgE3ZJrNTW_vva7AJIZUb3a1HyxJ_E2lt0iY4SRA86g_r0AxSXTpUXx-kx-Q9sXPnI5o057TvesCccyirtIGm605wTpuURN4UXZ36YP4zJW3m1aKuz1nznkh0kUjzfCGQSM_K_dKDYPNbilibuc5ANOcMyqzuoEbKocjSkrISRUlLbae_WIWMLu_yE1UNcNVVUKbnEuoqR8jOTV_6nOxE5DP8WJ9EbO3epymbCipR-D689OkqhhPWBXKanQsUy8CkJ14eJTiF62oVFI7cGkbx9iS4n-lJkIKVk71kc-QHad2BiUAI4022LKS7g6A4h3Qd-R65428jHb_h7K7aSOtXBlIJeZNixiEEdzigomiN0AggrZzRQ2i0MFHTqpdmRCe1At1DPysRDdBsnR63OXhEGbNHVAIdt4IH9j42uIaN7NAjbMnDRdzwmjnhD_MmBRCLBLAHgRzxEmsGbf8EZ1BZpMXznYZBk59-rx0I0MszPLMxy-heDlur0PtArVGGieXkDsAepbAPize_CgsXvfs4cHHUA7fWeWTCcL1_9hcldvFFrV0MRenKYh5YgB97PyPLzvkB5ynCCbcz8f-_vyJ_TNqIh_ZHdut7ZF_kg_5Wd97xYgDI3p6pSfSBetzpuornJSf__gOZGbQcsgQAAA.xT8t0qvHPpovCA_atxr5FUSqyS_nopyUGSuDL_s8339S62Yq9gRwioj5uZ0pWDCgO8_kqxylUKMeuOyUHpg2184c4OMHps3lUMnHhdBpJ4siIF4I9AdydyJ5OjdWgxSBb5DV6HuraC-FF1kANHVrlRN_9HiG39FHqaUGw8Q-O1MOENL4Avxq5lFUQFKNfdS5llQWwJzstefP9j0iCfzgxcxNT1_7ky72RpVQtdKl5BDxP-7YB23QHV0VC6gpCBm1k6XvYlwZP07rC6MVZZ5dga6bctGRB7vUD9gsYOvKn-ewGSoCJShY_oImdFTUnYk5iuyBpi1RFMJfbj3gtVW94QBke7xZRr5yRCOkRWC4ZCHXDlWso3XZJzkUuqf22Rs7dU_GjlzUSLiTzTcjCvW2QAjWyGXV0Nj8Tu6lio6ungGKvvnHXSghSWd97A0Dgu6s8Bs2He1vG11wBrO97iK6p6fgv92P-81YSwnCOb5MIr-Ggr1t88ou_NK2K3jWlnpkb3xzotusyGMdu3q4x9hrNsiHeMPuX9C3sy8UEFTgfrekcK8AVU2eox1HHAbMiWFCtQsfjrDDN6DXyUQcivYxrY9EWmEvlalboQQmMIDeHNeWjaeYUt_8MyN2mfUfnOEu9s-GnXya--2g2-o6azEvKSNM44VV0ozbP3u3FHMYjdI";
        try {
            // Initiate the client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/accounts"))
//                    .uri(new URI("https://api-sandbox.starlingbank.com/api/v2/feed/account/902ed248-f5b6-40d9-" +
//                            "a990-17b69defd6eb/category/902ebc6d-b224-4f73-bf02-d8b45bcb75e3/transactions-between" +
//                            "?minTransactionTimestamp=2024-02-20T12:34:56.000Z" +
//                            "&maxTransactionTimestamp=2024-02-27T00:00:00.000Z")) ///902ed248-f5b6-40d9-a990-17b69defd6eb/balance
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + TOKEN)
                    .header("User-agent", "Cody Maverick")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println(response.body());

            JSONObject responseBody = new JSONObject(response.body());
            JSONArray accountArray = responseBody.getJSONArray("accounts");
            JSONObject accountDetails = accountArray.getJSONObject(0);
            String accountUid = accountDetails.optString("accountUid");
            System.out.println("UID: " + accountUid);

//            for (int i = 0; i < feedItems.length(); i++){
//                System.out.println(feedItems.get(i).toString());
//            }

            // Extracting accountUid from JSON response
//            JSONObject responseBody = new JSONObject(response.body());
//            JSONArray accounts = responseBody.getJSONArray("accounts");
//            JSONObject firstAccount = accounts.getJSONObject(0);
//            String accountUID = firstAccount.optString("accountUid");
//            System.out.println("ID: " + accountUID);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}