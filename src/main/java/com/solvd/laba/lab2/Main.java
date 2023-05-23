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

        //create checking account from account
        LOGGER.info("\n=======Checking Account=======");

        //create checking account from  account1
        CheckingAccount check1 = bank.createCheckingAccount(account1);

        //checking account number of check1
        LOGGER.info("Checking number: " + bank.getAccountNumber(check1));

        //create checking account from  account2
        CheckingAccount check2 = bank.createCheckingAccount(account2);

        //checking account number of check2
        LOGGER.info("Checking number: " + bank.getAccountNumber(check2) + "\n");

        //adding more account
        bank.createCheckingAccount(bank.createAccount(cus3, 1000));
        bank.createCheckingAccount(bank.createAccount(cus4, 5000));
        bank.createCheckingAccount(bank.createAccount(cus5, 12000));
        bank.createCheckingAccount(bank.createAccount(cus6, 3200));

        //print list of checking account that balance greater than 2000
        LOGGER.info("Checking accounts have balance greater than 2000 \n" +
                bank.printFilterAccount(account -> account.getBalance() > 2000) + "\n");

        //Checking monthly fee
        bank.checkMonthlyFee(check1);
        bank.checkMonthlyFee(check2);

        //deposit to check1
        bank.deposit(check1, 1000);
        LOGGER.info("Balance check1: " + bank.checkBalance(check1));

        //checking monthly fee again check1
        bank.checkMonthlyFee(check1);

        //balance check2 before transfer money to check1
        LOGGER.info("Balance check2: " + bank.checkBalance(check2));

        //transfer money from check2 to check1
        bank.transferMoney(check2, check1, 500);

        //checking balance check1 after receive money from check2
        LOGGER.info("Balance check1: " + bank.checkBalance(check1));

        //balance check2 after transfer money to check1
        LOGGER.info("Balance check2: " + bank.checkBalance(check2));

        //print transaction list of check1
        bank.printList(check1);
        System.out.println();

        //create saving account from account1
        System.out.println("\n=======Saving Account=======");
        SavingAccount save1 = bank.createSavingAccount(account1);
        LOGGER.info("Saving number: " + bank.getAccountNumber(save1));
        LOGGER.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);
        bank.deposit(save1, 2000);
        LOGGER.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);
        bank.calculator(0.15, 2, (interestRate, numOfYear) -> {
            return save1.getBalance() * (interestRate / 100) * numOfYear + save1.getBalance();
        });

        //create debit card from checking account1
        LOGGER.info("\n=======Debit Card=======");
        DebitCard debitCard1 = bank.createDebitCard(check1);
        LOGGER.info(bank.getInfo(debitCard1));
        LOGGER.info(bank.checkBalance(debitCard1));
        bank.makePurchase(debitCard1, 100);
        bank.deposit(debitCard1, 432);
        bank.withdrawal(debitCard1, 32);
        bank.deposit(debitCard1, 1000);
        LOGGER.info(bank.checkBalance(debitCard1));
        bank.printList(debitCard1);

        //create credit card from account1
        LOGGER.info("\n=======Credit Card=======");
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
        LOGGER.info("\n=======Lambda=======");

        //print customers credit score within range
        bank.printCustomerCredScoreInRange(500, 700);

        //Stream
        LOGGER.info("\n=======Stream=======");

        //print customer credit score within range using stream
        bank.printStreamCusCredScoreRange(400, 1200);

        //print customer age within range using stream
        bank.printStreamCusWithAge(20, 30);

        //print customer average credit score of certain age
        bank.printAverageCredWithAge(30, 50);
    }
}
