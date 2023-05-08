package com.solvd.laba.lab2;

import com.solvd.laba.lab2.linkedList.LinkedListCustom;

import java.util.Random;

public class CheckingAccount extends Account {
    /*declare properties*/
    private double monthlyFee;

    /*constructors*/
    public CheckingAccount(long accountNumber, String accountType, Customer cus, double monthlyFee, double balance) {
        super(accountNumber, balance, accountType, cus);
        this.monthlyFee = monthlyFee;
    }

    public CheckingAccount(Account account, String accountType, double balance, double monthlyFee) {
        this(account.getAccountNumber(), accountType, account.getCustomer(), monthlyFee, balance);
    }

    public CheckingAccount(Account account, String accountType, double monthlyFee) {
        super(account.getCustomer(), account.getBalance());
        this.setAccountType(accountType);
        this.setAccountNumber(generateNumber());
        this.monthlyFee = monthlyFee;
    }

    /*getters and setters*/
    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    /*methods*/
    public boolean isMonthlyFee() {
        if (getBalance() < 500) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void printList() {
        logger.info(String.format("%60s", "Transaction List of Checking account "));
        LinkedListCustom<Transaction> checkingList = getTransactionList();
        for (int i = 0; i < getTransactionList().getSize(); i++) {
            logger.info(checkingList.get(i));
        }
    }

    @Override
    public long generateNumber() {
        String idChecking = "1213";
        Random random = new Random();
        int randChecking = random.nextInt(100000) + 1000;
        //Concat String with lastAccNum to not get same number generated
        String checkingAccNum = idChecking + String.valueOf(randChecking) + String.valueOf(lastAccNum);
        lastAccNum++;
        return Long.parseLong(checkingAccNum);
    }


}
