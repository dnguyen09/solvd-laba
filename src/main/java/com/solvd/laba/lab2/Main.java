package com.solvd.laba.lab2;

import com.solvd.laba.lab2.exception.CreditCheckException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //create bank and customer
        Bank bank = new Bank("Chase", "Irvine, WY");
        Customer cus1 = new Customer("John", "12-15-1991", 570);
        Customer cus2 = new Customer("Doe", "11-22-1979", 660);

        try {
            String result1 = bank.makeCreditCheck(cus1);
            logger.info(result1);
            String result2 = bank.makeCreditCheck(cus2);
            logger.info(result2);
        } catch (CreditCheckException e) {
            logger.info("Error while processing credit check for customer " + e.getMessage());
        }

        //register account from customer in bank
        Account account1 = bank.createAccount(cus1, 400);
        logger.info("Account number: " + bank.getAccountNumber(account1) + "\n");
        Account account2 = bank.createAccount(cus2, 4000);
        logger.info("Account number: " + bank.getAccountNumber(account2));
        System.out.println();

        //create checking account from account
        CheckingAccount check1 = bank.createCheckingAccount(account1);
        logger.info("Checking number: " + bank.getAccountNumber(check1));
        CheckingAccount check2 = bank.createCheckingAccount(account2);
        logger.info("Checking number: " + bank.getAccountNumber(check2) + "\n");

        bank.checkMonthlyFee(check1);
        bank.deposit(check1, 1000);
        logger.info("Balance check1: " + bank.checkBalance(check1));
        bank.checkMonthlyFee(check1);
        logger.info("Balance check2: " + bank.checkBalance(check2));
        bank.transferMoney(check2, check1, 500);

        logger.info("Balance check1: " + bank.checkBalance(check1));
        logger.info("Balance check2: " + bank.checkBalance(check2));
        bank.printList(check1);
        System.out.println();

        //create saving account from account
        SavingAccount save1 = bank.createSavingAccount(account1);
        logger.info("Saving number: " + bank.getAccountNumber(save1));
        logger.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);
        bank.deposit(save1, 2000);
        logger.info(bank.checkBalance(save1));
        bank.checkInterestRate(save1);
        System.out.println();

        //create debit card from checking account
        DebitCard debitCard1 = bank.createDebitCard(check1);
        logger.info(bank.getInfo(debitCard1));
        logger.info(bank.checkBalance(debitCard1));
        bank.makePurchase(debitCard1, 100);
        bank.deposit(debitCard1, 432);
        bank.withdrawal(debitCard1, 32);
        bank.deposit(debitCard1, 1000);
        logger.info(bank.checkBalance(debitCard1));
        bank.printList(debitCard1);
        System.out.println();

        //create credit card from account
        CreditCard creditCard1 = bank.createCreditCard(account1);
        logger.info(bank.getInfo(creditCard1));
        logger.info(bank.checkBalance(creditCard1));
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
    }
}
