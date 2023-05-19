package com.solvd.laba.lab2;

import com.solvd.laba.lab2.enums.AccountType;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SavingAccount extends Account {
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

    public SavingAccount(Account account, AccountType accountType, double minimumBalance, double interestRate) {
        super(account.getCustomer(), 0.0);
        this.setAccountType(accountType);
        this.setAccountNumber(generateNumber("1314"));
        this.interestRate = interestRate;
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
    //method printing saving account transaction list
    @Override
    public void printList() {
        LOGGER.info(String.format("%60s", "Transaction List of Saving account "));
        LinkedListCustom<Transaction> savingList = getTransactionList();
        for (int i = 0; i < savingList.getSize(); i++) {
            LOGGER.info(savingList.get(i));
        }
    }
}
