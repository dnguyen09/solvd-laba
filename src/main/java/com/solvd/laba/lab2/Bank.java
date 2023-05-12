package com.solvd.laba.lab2;

import com.solvd.laba.lab2.exception.CreditCheckException;
import com.solvd.laba.lab2.exception.WithdrawalException;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

final public class Bank {
    //Logger
    private static final Logger LOGGER = LogManager.getLogger(Bank.class);

    /*declare properties*/
    private String name;
    private String location;
    private final ArrayList<Customer> customerList;

    /*constructor*/
    public Bank(String name, String location) {
        this.name = name;
        this.location = location;
        this.customerList = new ArrayList<>();
    }

    public Bank() {
        this("", "");
    }

    /*getter and setter*/
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    /*methods*/

    //method to print message if customer is qualified
    public String makeCreditCheck(Customer customer) throws CreditCheckException {
        String qualificationStatus = CreditCheck.isQualifyAcc(customer);
        LOGGER.info("Hi " + customer.getCustomerName() + "! based on your date of birth " +
                customer.getDateOfBirth() + " and your credit score " + customer.getCreditScore() + "\n" +
                "Here are your result:");
        if (qualificationStatus.equals("Qualified")) {
            return "Congratulation! You are qualified for credit.\n";
        } else {
            return ("Sorry, your account does not qualify \n" + qualificationStatus);
        }
    }

    //method to store customer information
    public Account createAccount(Customer customer, int balance) {
        LOGGER.info("Congrats " + customer.getCustomerName() + ", you have became our customer!");
        customerList.add(customer);
        return new Account(customer, balance);
    }

    //method create checking account for customer
    public CheckingAccount createCheckingAccount(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " open checking account successful");
        return new CheckingAccount(account, "Checking account", 12);
    }

    //method create saving account for customer
    public SavingAccount createSavingAccount(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " open saving account successful");
        return new SavingAccount(account, "Saving account", 10);
    }

    //method creating debit card
    public DebitCard createDebitCard(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " has created a debit card");
        return new DebitCard(account, "Debit card", 1990);
    }

    //method creating credit card
    public CreditCard createCreditCard(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " has created a credit card");
        return new CreditCard(account, "Credit card", 1021);
    }

    //method check monthly service fee for checking account
    public void checkMonthlyFee(CheckingAccount checkingAccount) {
        if (checkingAccount.isMonthlyFee()) {
            LOGGER.info("Checking your monthly fee status...");
            LOGGER.info("Your balance: " + checkingAccount.getBalance() + "\n" +
                    "Your monthly fee is: " + checkingAccount.getMonthlyFee());
            checkingAccount.setBalance(checkingAccount.getBalance() - checkingAccount.getMonthlyFee());
            LOGGER.info("$" + checkingAccount.getMonthlyFee() + " has been deducted as monthly service fee" + "\n" +
                    "Your balance: " + checkingAccount.getBalance() + "\n" +
                    "To avoid monthly fee, please have your checking account balance greater than 500 \n");
        } else {
            LOGGER.info("Checking your monthly fee status...");
            LOGGER.info("You have no monthly service fee\n");
        }
    }

    //method checking minimum payment for credit card
    public void checkMinPayment(CreditCard creditCard) {
        if (creditCard.getMinimumPayment() != 0) {
            LOGGER.info("Your minimum payment: " + creditCard.getMinimumPayment());
        } else {
            LOGGER.info("No minimum payment this month.");
        }
    }

    //method to calculate minimum payment of credit card  from bank
    public void calculateMinPayment(CreditCard creditCard) {
        creditCard.calculateMinPayment();
        LOGGER.info("Checking your monthly minimum payment...");
        LOGGER.info("Minimum payment this month: " + creditCard.getMinimumPayment());
        LOGGER.info("Your outstanding balance: " + creditCard.getBalance());
    }

    //method paying min payment for credit card from checking account
    public void payMinPayment(CreditCard creditCard, CheckingAccount account) {
        creditCard.payMinPayment(account);
    }

    //method checking interest rate for saving account
    public void checkInterestRate(SavingAccount acc) {
        if (acc.getBalance() < acc.getMinimumBalance()) {
            LOGGER.info("Checking your interest rate...");
            LOGGER.info("Your Saving account required " + acc.getMinimumBalance() +
                    " minimum balance to keep the account open");
        } else {
            LOGGER.info("Checking your interest rate...");
            LOGGER.info("Your interest rate: " + acc.getInterestRate());
            LOGGER.info("Your interest earned " + acc.getInterestEarned() + "\n");
        }
    }

    //method get card information
    public String getInfo(Account account) {
        return account.toString();
    }

    public void deposit(Account acc, double amount) {
        acc.deposit(amount);
    }

    public void withdrawal(Account account, double amount) {
        //throw exception if a withdrawal is made from credit card
        try {
            if (account instanceof CreditCard) {
                throw new WithdrawalException("Cannot withdraw from a credit card.");
            }
            account.withdrawal(amount);
        } catch (WithdrawalException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void makePurchase(Account account, double amount) {
        account.makePurchase(amount);
    }

    //method transfer money from account to account
    public void transferMoney(CheckingAccount fromAccount, Account toAccount, double amount) {
        fromAccount.withdrawal(amount, "Transfer to account " + toAccount.getAccountNumber());
        toAccount.deposit(amount, "Transfer from account " + fromAccount.getAccountNumber());
        LOGGER.info("Transfer " + amount + " from account " + fromAccount.getAccountNumber() + " to account " + toAccount.getAccountNumber() + " successful");
    }

    //method print list of transaction from account
    public void printList(Account account) {
        account.printList();
    }

    //method to get transaction history of account
    public LinkedListCustom<Transaction> getTransactionsHistory(Account account) {
        return account.getTransactionList();
    }

    //method to get account number from account
    public long getAccountNumber(Account acc) {
        return acc.getAccountNumber();
    }

    public String checkBalance(CreditCard acc) {
        return "Your Outstanding balance: " + acc.getBalance() + "\n";
    }

    public String checkBalance(Account acc) {
        return "Your balance: " + acc.getBalance() + "\n";
    }
}
