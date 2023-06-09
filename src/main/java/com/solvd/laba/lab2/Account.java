package com.solvd.laba.lab2;


import com.solvd.laba.lab2.enums.AccountType;
import com.solvd.laba.lab2.enums.TransactionType;
import com.solvd.laba.lab2.exception.InsufficientBalanceException;
import com.solvd.laba.lab2.interfaces.Payment;
import com.solvd.laba.lab2.interfaces.PrintList;
import com.solvd.laba.lab2.lambdas.RandGenerate;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.stream.Stream;

public class Account implements PrintList, Payment {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(Account.class);

    /*declare properties*/
    private long accountNumber;
    private double balance;
    private AccountType accountType;
    private Customer customer;
    private LinkedListCustom<Transaction> transactionList;

    /*constructors*/
    public Account(long accountNumber, double balance, AccountType accountType, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(long accountNumber, AccountType accountType, Customer customer) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(Customer customer, double balance) {
        this.accountNumber = generateNumber("1010");
        this.balance = balance;
        this.accountType = null;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
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
        transactionList.add(new Transaction(amount, TransactionType.DEPOSIT));
        LOGGER.info("Deposit " + amount + " successful to " + accountType);
    }

    //overloading method deposit to transfer amount with type
    public void deposit(double amount, TransactionType transactionType) {
        balance += amount;
        transactionList.add(new Transaction(amount, transactionType));
    }

    public void withdrawal(double amount) {
        if (amount > balance) {
            LOGGER.info("Withdrawal failed! the amount withdrawal excess balance");
        } else {
            balance -= amount;
            transactionList.add(new Transaction(amount, TransactionType.WITHDRAWAL));
            LOGGER.info("Withdrawal " + amount + " successful from " + accountType);
        }
    }

    //overloading method withdrawal to withdraw account with type
    public void withdrawal(double amount, TransactionType transactionType) {
        try {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance to withdraw! ");
            } else {
                balance -= amount;
                transactionList.add(new Transaction(amount, transactionType));
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
            transactionList.add(new Transaction(amount, TransactionType.PURCHASE));
            LOGGER.info("Purchase " + amount + " successful from " + accountType);
        }
    }

    //method print list of transaction
    public void printList() {
        Stream.of(transactionList).forEach(LOGGER::info);
    }

    public long IdAccount(String id, RandGenerate<Integer> rand) {
        String accountNum = id + rand.genRandNum() + AccountNumber.lastAccNum;
        AccountNumber.lastAccNum++;
        return Long.parseLong(accountNum);
    }

    public long generateNumber(String id) {
        return IdAccount(id, () -> {
            Random rand = new Random();
            return rand.nextInt(100000) + 1000;
        });
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", customer=" + customer +
                '}';
    }
}
