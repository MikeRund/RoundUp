package service;

import client.SavingsGoalClient;
import client.TransactionClient;

import java.util.ArrayList;

// Class for rounding up a customers spending to the nearest pound and transferring amount to a new savings space
public class RoundUpService {
    private final TransactionClient transactionClient; // To obtain spending list
    private final SavingsGoalClient savingsGoalClient; // For saving space API calls

    // Initialize both field variables on object creation
    public RoundUpService(){
        this.transactionClient = new TransactionClient();
        this.savingsGoalClient = new SavingsGoalClient();
    }

    // Method to round up all spending in arraylist to the nearest pound and return that amount
    public int calculateRoundUpAmount() {
        ArrayList<Integer> spendingList = this.transactionClient.getSpendingList();
        int total = 0;

        for (int payment: spendingList) {
            payment = payment % 100; // Allows us to work with the last 2 digits of n integer (pennies)
            total += (100 - payment); // Obtains amount needed to round up to a pound
        }
        return total;
    }

    // Method to place round up amount into a newly created saving space
    public void roundUpToSavingsGoal() {
        try {
            int amount = calculateRoundUpAmount();
            this.savingsGoalClient.createSavingsGoal();
            this.savingsGoalClient.addMoneyTosSavingsGoal(this.savingsGoalClient.getSavingsGoalUid(), amount);
            System.out.println("Savings goal creation success. Savings Goal UID: " + this.savingsGoalClient.getSavingsGoalUid());
            System.out.println("Transaction of " + amount + " minor units successfully added to Savings Goal");

        } catch (Exception e){
            System.out.println("Error occurred performing round up service: " + e);
        }
    }
}
