package com.solvd.laba.lab2;

import com.solvd.laba.lab2.interfaces.InterestRate;
import com.solvd.laba.lab2.linkedList.LinkedListCustom;

import java.util.Random;

public class SavingAccount extends Account implements InterestRate {
    /*declare properties*/
    private double interestRate;
    private double minimumBalance;

    /*constructor*/

    public SavingAccount(int accountNumber, double balance, String accountType, Customer customer, double interestRate, double minimumBalance) {
        super(accountNumber, balance, accountType, customer);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public SavingAccount(Account account, String accountType, double minimumBalance) {
        super(account.getCustomer(), 0.0);
        this.setAccountType(accountType);
        this.setAccountNumber(generateNumber());
        this.interestRate = calculateInterestRate();
        this.minimumBalance = minimumBalance;
    }

    /*Getter and setters*/
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /*methods*/
    //method return the amount get based on interest rate
    public double getInterestEarned() {
        return this.getBalance() * (interestRate / 100);
    }

    //Interest rate for saving account
    @Override
    public double calculateInterestRate() {
        return 0.15;
    }

    //method printing saving account transaction list
    @Override
    public void printList() {
        logger.info(String.format("%60s", "Transaction List of Saving account "));
        LinkedListCustom<Transaction> savingList = getTransactionList();
        for (int i = 0; i < savingList.getSize(); i++) {
            logger.info(savingList.get(i));
        }
    }

    @Override
    public long generateNumber() {
        String idSaving = "1314";
        Random random = new Random();
        int randSaving = random.nextInt(100000) + 1000;
        //Concat String with lastAccNum to not get same number generated
        String SavingAccNum = idSaving + String.valueOf(randSaving) + String.valueOf(lastAccNum);
        lastAccNum++;
        return Long.parseLong(SavingAccNum);
    }
}
