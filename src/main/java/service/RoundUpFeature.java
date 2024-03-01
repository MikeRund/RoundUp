package service;

import client.Transactions;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoundUpFeature {

    public static ArrayList<Integer> getSpendingList(Transactions transactions) {

        ArrayList<Integer> spendingList = new ArrayList<>();

        for (int i = 0; i < transactions.feedItems.size(); i++) {
            JSONObject transaction = transactions.feedItems.get(i);
            String direction = transaction.getString("direction");
            if (direction.equals("OUT")) {
                JSONObject amountItem = transaction.getJSONObject("amount");
                int amount = amountItem.getInt("minorUnits");
                spendingList.add(amount);
            }
        }
        return spendingList;
    }

    public static int calculateRoundUpAmount(ArrayList<Integer> spendingList) {
        int total = 0;
        for (int payment: spendingList) {
            payment = payment % 100;
            total += (100 - payment);
            System.out.println(total);
        }
        return total;
    }

    public static void main(String[] args) {
        Transactions transactions = new Transactions();
        transactions.retrieveFeedItems();
        ArrayList<Integer> spending = getSpendingList(transactions);
        calculateRoundUpAmount(spending);

        for(int i : spending) {
            System.out.println(i);
        }
//        int num = 255566;
//        int mod = 100;
//        int ans = num % mod;
//        System.out.println(num + " mod " + mod + " = " + ans);
    }
}
