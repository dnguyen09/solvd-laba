package com.solvd.laba.lab2;

import com.solvd.laba.lab2.enums.AccountType;
import com.solvd.laba.lab2.interfaces.InterestRate;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class SavingAccount extends Account implements InterestRate {
    //Logger
    private static final Logger LOGGER = LogManager.getLogger(SavingAccount.class);

    /*declare properties*/
    private double interestRate;
    private double minimumBalance;

    /*constructor*/
    public SavingAccount(int accountNumber, double balance, AccountType accountType, Customer customer, double interestRate, double minimumBalance) {
        super(accountNumber, balance, accountType, customer);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public SavingAccount(Account account, AccountType accountType, double minimumBalance) {
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
        LOGGER.info(String.format("%60s", "Transaction List of Saving account "));
        LinkedListCustom<Transaction> savingList = getTransactionList();
        for (int i = 0; i < savingList.getSize(); i++) {
            LOGGER.info(savingList.get(i));
        }
    }

    @Override
    public long generateNumber() {
        String idSaving = "1314";
        Random random = new Random();
        int randSaving = random.nextInt(100000) + 1000;
        //Concat String with lastAccNum to not get same number generated
        String SavingAccNum = idSaving + randSaving + lastAccNum;
        lastAccNum++;
        return Long.parseLong(SavingAccNum);
    }
}
