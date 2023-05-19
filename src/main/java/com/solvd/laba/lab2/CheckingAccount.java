package com.solvd.laba.lab2;

import com.solvd.laba.lab2.enums.AccountType;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckingAccount extends Account {
    //Logger
    private static final Logger LOGGER = LogManager.getLogger(CheckingAccount.class);

    /*declare properties*/
    private double monthlyFee;

    /*constructors*/
    public CheckingAccount(long accountNumber, AccountType accountType, Customer cus, double monthlyFee, double balance) {
        super(accountNumber, balance, accountType, cus);
        this.monthlyFee = monthlyFee;
    }

    public CheckingAccount(Account account, AccountType accountType, double balance, double monthlyFee) {
        this(account.getAccountNumber(), accountType, account.getCustomer(), monthlyFee, balance);
    }

    public CheckingAccount(Account account, AccountType accountType, double monthlyFee) {
        super(account.getCustomer(), account.getBalance());
        this.setAccountType(accountType);
        this.setAccountNumber(generateNumber("1213"));
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
        LOGGER.info(String.format("%60s", "Transaction List of Checking account "));
        LinkedListCustom<Transaction> checkingList = getTransactionList();
        for (int i = 0; i < getTransactionList().getSize(); i++) {
            LOGGER.info(checkingList.get(i));
        }
    }

//    @Override
//    public long generateNumber() {
//        String idChecking = "1213";
//        Random random = new Random();
//        int randChecking = random.nextInt(100000) + 1000;
//        //Concat String with lastAccNum to not get same number generated
//        String checkingAccNum = idChecking + randChecking + AccountNumber.lastAccNum;
//        AccountNumber.lastAccNum++;
//        return Long.parseLong(checkingAccNum);
//    }
}
