package com.solvd.laba.lab2;

import com.solvd.laba.lab2.exception.CreditCheckException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    //logger
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //create bank and customer
        Bank bank = new Bank("Chase", "Irvine, WY");
        Customer cus1 = new Customer("Bryce", "12-15-1991", 570);
        Customer cus2 = new Customer("Armando", "11-22-1979", 660);
        Customer cus3 = new Customer("Amad", "03-19-1972", 880);
        Customer cus4 = new Customer("Seanna", "05-29-1984", 732);
        Customer cus5 = new Customer("Cyrus", "09-19-1993", 1000);
        Customer cus6 = new Customer("Diago", "02-08-2002", 600);


        try {
            String result1 = bank.makeCreditCheck(cus1);
            LOGGER.info(result1);
            String result2 = bank.makeCreditCheck(cus2);
            LOGGER.info(result2);
        } catch (CreditCheckException e) {
            LOGGER.error("Error while processing credit check for customer " + e.getMessage());
        }

        //register account from customer in bank
        Account account1 = bank.createAccount(cus1, 400);
        LOGGER.info("Account number: " + bank.getAccountNumber(account1) + "\n");
        Account account2 = bank.createAccount(cus2, 4000);
        LOGGER.info("Account number: " + bank.getAccountNumber(account2));
        bank.createAccount(cus3, 2000);
        bank.createAccount(cus4, 5000);
        bank.createAccount(cus5, 12000);
        bank.createAccount(cus6, 2000);


        //create checking account from account
        System.out.println("\n=======Checking Account=======");
        CheckingAccount check1 = bank.createCheckingAccount(account1);
        LOGGER.info("Checking number: " + bank.getAccountNumber(check1));
        CheckingAccount check2 = bank.createCheckingAccount(account2);
        LOGGER.info("Checking number: " + bank.getAccountNumber(check2) + "\n");

        bank.checkMonthlyFee(check1);
        bank.deposit(check1, 1000);
        LOGGER.info("Balance check1: " + bank.checkBalance(check1));
        bank.checkMonthlyFee(check1);
        LOGGER.info("Balance check2: " + bank.checkBalance(check2));
        bank.transferMoney(check2, check1, 500);

        LOGGER.info("Balance check1: " + bank.checkBalance(check1));
        LOGGER.info("Balance check2: " + bank.checkBalance(check2));
        bank.printList(check1);
        System.out.println();

        //create saving account from account
        System.out.println("\n=======Saving Account=======");
        SavingAccount save1 = bank.createSavingAccount(account1);
        LOGGER.info("Saving number: " + bank.getAccountNumber(save1));
        LOGGER.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);
        bank.deposit(save1, 2000);
        LOGGER.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);

        //create debit card from checking account
        System.out.println("\n=======Debit Card=======");
        DebitCard debitCard1 = bank.createDebitCard(check1);
        LOGGER.info(bank.getInfo(debitCard1));
        LOGGER.info(bank.checkBalance(debitCard1));
        bank.makePurchase(debitCard1, 100);
        bank.deposit(debitCard1, 432);
        bank.withdrawal(debitCard1, 32);
        bank.deposit(debitCard1, 1000);
        LOGGER.info(bank.checkBalance(debitCard1));
        bank.printList(debitCard1);

        //create credit card from account
        System.out.println("\n=======Credit Card=======");
        CreditCard creditCard1 = bank.createCreditCard(account1);
        LOGGER.info(bank.getInfo(creditCard1));
        LOGGER.info(bank.checkBalance(creditCard1));
        bank.makePurchase(creditCard1, 500);
        bank.makePurchase(creditCard1, 76);
        bank.makePurchase(creditCard1, 43);
        bank.makePurchase(creditCard1, 105);
        bank.calculateMinPayment(creditCard1);
        bank.checkMinPayment(creditCard1);
        bank.withdrawal(creditCard1, 200);
        bank.payMinPayment(creditCard1, check1);
        bank.checkMinPayment(creditCard1);
        bank.printList(creditCard1);

        //lambda
        System.out.println("\n=======Lambda=======");

        //print customers credit score within range
        bank.printCustomerCredScoreInRange(500, 700);

        //Stream
        System.out.println("\n=======Stream=======");

        //print customer credit score within range using stream
        bank.printStreamCusCredScoreRange(400, 1200);

        //print customer age within range using stream
        bank.printStreamCusWithAge(20, 30);

        //print customer average credit score of certain age
        bank.printAverageCredWithAge(30, 50);
    }
}
