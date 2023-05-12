package com.solvd.laba.lab2;


import com.solvd.laba.lab2.exception.InsufficientBalanceException;
import com.solvd.laba.lab2.interfaces.Payment;
import com.solvd.laba.lab2.interfaces.PrintList;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Account extends AccountNumber implements PrintList, Payment {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(Account.class);

    /*declare properties*/
    private long accountNumber;
    private double balance;
    private String accountType;
    private Customer customer;
    private LinkedListCustom<Transaction> transactionList;

    /*constructors*/
    public Account(long accountNumber, double balance, String accountType, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(long accountNumber, String accountType, Customer customer) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(Customer customer, double balance) {
        this.accountNumber = generateNumber();
        this.balance = balance;
        this.accountType = "";
        this.customer = customer;
        this.transactionList = new LinkedListCustom<>();
    }

    /*getters and setters*/
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LinkedListCustom<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(LinkedListCustom<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    /*Methods*/
    public void deposit(double amount) {
        balance += amount;
        transactionList.add(new Transaction(amount, "Deposit"));
        LOGGER.info("Deposit " + amount + " successful to " + accountType);
    }

    //overloading method deposit to transfer amount with type
    public void deposit(double amount, String accountType) {
        balance += amount;
        transactionList.add(new Transaction(amount, accountType));
    }

    public void withdrawal(double amount) {
        if (amount > balance) {
            LOGGER.info("Withdrawal failed! the amount withdrawal excess balance");
        } else {
            balance -= amount;
            transactionList.add(new Transaction(amount, "Withdraw"));
            LOGGER.info("Withdrawal " + amount + " successful from " + accountType);
        }
    }

    //overloading method withdrawal to withdraw account with type
    public void withdrawal(double amount, String accountType) {
        try {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance to withdraw! ");
            } else {
                balance -= amount;
                transactionList.add(new Transaction(amount, accountType));
            }
        } catch (InsufficientBalanceException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void makePurchase(double amount) {
        if (amount > balance) {
            LOGGER.info("Purchase failed! your account don't have enough balance for making purchase");
        } else {
            balance -= amount;
            transactionList.add(new Transaction(amount, "Purchase"));
            LOGGER.info("Purchase " + amount + " successful from " + accountType);
        }
    }

    //static block
    static {
        lastAccNum = 0;
    }

    //method print list of transaction
    public void printList() {
        for (int i = 0; i < transactionList.getSize(); i++) {
            LOGGER.info(transactionList.get(i));
        }
    }

    @Override
    public long generateNumber() {
        String idAccount = "1010";
        Random random = new Random();
        int randAccNum = random.nextInt(100000) + 1000;    //generate  integer (1000 - 99999)
        String accountNum = idAccount + String.valueOf(randAccNum) + String.valueOf(lastAccNum);
        lastAccNum++;
        return Long.parseLong(accountNum);
    }

}
