package service;

import client.TransactionClient;

import java.util.ArrayList;

public class RoundUpService {

    private static final TransactionClient transactionClient = new TransactionClient();

    public static int calculateRoundUpAmount() {
        ArrayList<Integer> spendingList = transactionClient.getSpendingList();
        int total = 0;
        for (int payment: spendingList) {
            payment = payment % 100;
            total += (100 - payment);
            //System.out.println(total);
        }
        return total;
    }

    public static void main(String[] args) {
        int money = calculateRoundUpAmount();
    }
}
