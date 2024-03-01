package service;

import client.SavingsGoalClient;
import client.TransactionClient;

import java.util.ArrayList;

public class RoundUpService {

    private static final TransactionClient TRANSACTION_CLIENT = new TransactionClient();
    private static final SavingsGoalClient SAVINGS_GOAL_CLIENT = new SavingsGoalClient();

    public static int calculateRoundUpAmount() {
        ArrayList<Integer> spendingList = TRANSACTION_CLIENT.getSpendingList();
        int total = 0;
        for (int payment: spendingList) {
            payment = payment % 100;
            total += (100 - payment);
            //System.out.println(total);
        }
        return total;
    }

    public static void roundUpToSavingsGoal() {
        int amount = calculateRoundUpAmount();
        SAVINGS_GOAL_CLIENT.createSavingsGoal();
        SavingsGoalClient.addMoneyTosSavingsGoal(SAVINGS_GOAL_CLIENT.getSavingsGoalUid(), amount);
        SavingsGoalClient.viewAllSavingsGoals();
    }

    public static void main(String[] args) {

//        roundUpToSavingsGoal();
//        SavingsGoalClient.deleteSavingsGoal("b42b2fb7-479a-4e74-b275-ad4f617663ba");
//        SavingsGoalClient.deleteSavingsGoal("b40fc39e-b34a-4240-9662-03e9118f5676");
        SavingsGoalClient.viewAllSavingsGoals();
    }
}
