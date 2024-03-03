import client.SavingsGoalClient;
import org.json.JSONArray;
import org.json.JSONObject;
import service.RoundUpService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {

        RoundUpService roundUpService = new RoundUpService();
        roundUpService.roundUpToSavingsGoal();

    }
}