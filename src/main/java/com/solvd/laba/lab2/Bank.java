package com.solvd.laba.lab2;

import com.solvd.laba.lab2.enums.AccountType;
import com.solvd.laba.lab2.enums.CardType;
import com.solvd.laba.lab2.enums.TransactionType;
import com.solvd.laba.lab2.exception.CreditCheckException;
import com.solvd.laba.lab2.exception.DepositException;
import com.solvd.laba.lab2.exception.WithdrawalException;
import com.solvd.laba.lab2.lambdas.AccountFilter;
import com.solvd.laba.lab2.lambdas.InterestEarning;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

final public class Bank {
    //Logger
    private static final Logger LOGGER = LogManager.getLogger(Bank.class);

    /*declare properties*/
    private String name;
    private String location;
    private final ArrayList<Customer> customerList;
    private final List<Account> accountList;

    /*constructor*/
    public Bank(String name, String location) {
        this.name = name;
        this.location = location;
        this.customerList = new ArrayList<>();
        this.accountList = new ArrayList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<?> getCustomerList() {
        return customerList;
    }

    public LinkedListCustom<Transaction> getTransactionsHistory(Account account) {
        return account.getTransactionList();
    }

    public List<Account> getAccountList() {
        return accountList;
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
        CheckingAccount checking = new CheckingAccount(account, AccountType.CHECKING, 12);
        accountList.add(checking);
        return checking;
    }

    //method create saving account for customer
    public SavingAccount createSavingAccount(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " open saving account successful");
        SavingAccount saving = new SavingAccount(account, AccountType.SAVING, 10, 0.15);
        accountList.add(saving);
        return saving;
    }

    //method creating debit card
    public DebitCard createDebitCard(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " has created a debit card");
        DebitCard debit = new DebitCard(account, CardType.DEBIT, 1990);
        accountList.add(debit);
        return debit;
    }

    //method creating credit card
    public CreditCard createCreditCard(Account account) {
        LOGGER.info("Customer " + account.getCustomer().getCustomerName() + " has created a credit card");
        CreditCard credit = new CreditCard(account, CardType.CREDIT, 1021);
        accountList.add(credit);
        return credit;
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
        }
    }

    //calculating amount earned after a specified year for saving account
    public void calculator(double interestRate, int numOfYear, InterestEarning<Double> amountEarned) {
        double result = amountEarned.calculate(interestRate, numOfYear);
        LOGGER.info("Based on your interest rate: {}", interestRate);
        LOGGER.info("Your balance after {} year(s) is {}", numOfYear, result);
    }

    //method get card information
    public String getInfo(Account account) {
        return account.toString();
    }

    public void deposit(Account acc, double amount) {
        if (acc instanceof CreditCard) {
            throw new DepositException("Cannot deposit to credit card");
        }
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
        try {
            // Withdraw from the source account
            fromAccount.withdrawal(amount, TransactionType.TRANSFER);

            // If withdrawal is successful, deposit into the destination account
            toAccount.deposit(amount, TransactionType.TRANSFER);

            LOGGER.info("Transfer of " + amount + " from account " + fromAccount.getAccountNumber() +
                    " to account " + toAccount.getAccountNumber() + " successful");
        } catch (WithdrawalException withdrawalException) {
            LOGGER.error("Withdrawal failed: " + withdrawalException.getMessage());

            // Compensate for the failed withdrawal by reversing the successful deposit
            toAccount.withdrawal(amount, TransactionType.CANCEL);
            LOGGER.info("Cancel of " + amount + " from account " + toAccount.getAccountNumber() + " successful");
        } catch (DepositException e) {
            LOGGER.error("Deposit failed: " + e.getMessage());

            // cancel for the failed deposit by adding back amount to source account
            fromAccount.deposit(amount, TransactionType.CANCEL);
            LOGGER.info("Cancel of " + amount + " from account " + toAccount.getAccountNumber() + " successful");
        }
    }

    //method print list of account
    public List<Account> printFilterAccount(AccountFilter<Account> customFilter) {
        List<Account> accountFilterList = new ArrayList<>();
        for (Account account : this.accountList) {
            if (customFilter.check(account)) {
                accountFilterList.add(account);
            }
        }
        return accountFilterList;
    }

    //method print list of transaction from account
    public void printList(Account account) {
        account.printList();
    }

    //method to get account number from account
    public long getAccountNumber(Account acc) {
        return acc.getAccountNumber();
    }

    //method check balance of credit card
    public String checkBalance(CreditCard acc) {
        return "Your Outstanding balance: " + acc.getBalance() + "\n";
    }

    public String checkBalance(Account acc) {
        return "Your balance: " + acc.getBalance() + "\n";
    }

    /*----Lambda----*/
    //method using lambda function Predicate to get customers based on requirement
    public List<Customer> getCustomerWithFilter(Predicate<Customer> filterCustomer) {
        List<Customer> customerFilterList = new ArrayList<>();
        for (Customer customer : customerList) {
            if (filterCustomer.test(customer)) {
                customerFilterList.add(customer);
            }
        }
        return customerFilterList;
    }

    //print list of customer credit score with lambda based on range
    public void printCustomerCredScoreInRange(int start, int end) {
        LOGGER.info(getCustomerWithFilter(customer -> customer.getCreditScore() > start
                && customer.getCreditScore() < end));
    }

    /*----Stream----*/
    //Using stream to print list of customer credit score within range
    public void printStreamCusCredScoreRange(int startCred, int endCred) {
        List<String> streamCusCred = customerList.stream()
                .filter((Customer customer) -> customer.getCreditScore() > startCred
                        && customer.getCreditScore() < endCred)
                .map(customer -> "Name: " + customer.getCustomerName() + " - Credit Score: " + customer.getCreditScore())
                .collect(Collectors.toList());
        LOGGER.info(streamCusCred);
    }

    //using stream to print list of customer age within range
    public void printStreamCusWithAge(int startAge, int endAge) {
        List<String> cusAge = customerList.stream()
                .filter(customer -> customer.getAge() > startAge && customer.getAge() < endAge)
                .map(customer -> "Name: " + customer.getCustomerName() + " - Age: " + customer.getAge())
                .collect(Collectors.toList());

        cusAge.forEach(LOGGER::info);
    }

    //using stream to print average credit score of certain customers age
    public void printAverageCredWithAge(int startAge, int endAge) {
        double average = customerList.stream()
                .filter(customer -> customer.getAge() > startAge && customer.getAge() < endAge)
                .mapToInt(Customer::getCreditScore)
                .average()
                .orElse(0);

        LOGGER.info("Average Credit Score: " + average);
    }

}
