package com.solvd.laba.lab2;

import com.solvd.laba.lab2.exception.CVVException;
import com.solvd.laba.lab2.interfaces.CardCreating;
import com.solvd.laba.lab2.interfaces.InterestRate;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreditCard extends Account implements CardCreating, InterestRate {
    //Logger
    private static final Logger LOGGER = LogManager.getLogger(CreditCard.class);

    /*declare properties*/
    private long creditCardNum;
    private double creditLimit;
    private String expirationDate;
    private int cvv;
    private int pin;
    private double interestRate;
    private double minimumPayment;

    /*constructor*/

    public CreditCard(long accountNumber, String accountType, Customer customer, int creditCardNum, double creditLimit, String expirationDate, int cvv, int pin) {
        super(accountNumber, accountType, customer);
        this.creditCardNum = creditCardNum;
        this.creditLimit = creditLimit;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.pin = pin;
    }

    public CreditCard(Account account, String accountType, int pin) {
        super(account.getCustomer(), 0);
        this.setAccountType(accountType);
        this.creditCardNum = generateNumber();
        this.expirationDate = generateExpirationDate();
        this.creditLimit = 5000;
        this.cvv = generateCVV();
        this.pin = pin;
    }

    /*Getters and setters*/
    public long getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(int creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {

        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(double minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    /*methods*/
    //method to generate credit card number
    @Override
    public long generateNumber() {
        String idCredit = "6677";
        Random random = new Random();
        //generate number in range 1000 - 99999
        int randCredit = random.nextInt(100000) + 1000;
        //Concat String with lastAccNum to not get same number generated
        String creditCardNum = idCredit + String.valueOf(randCredit) + String.valueOf(lastAccNum);
        lastAccNum++;
        return Long.parseLong(creditCardNum);
    }

    //method to create expiration date
    public String generateExpirationDate() {
        Calendar dateNow = Calendar.getInstance();
        dateNow.setTime(new Date());
        dateNow.add(Calendar.YEAR, 3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        return dateFormat.format(dateNow.getTime());
    }

    //method to create CVV
    public int generateCVV() {
        try {
            Random random = new Random();
            int cvv = random.nextInt(900) + 100;

            //check if cvv follow format
            if (isValidCVV(cvv)) {
                throw new CVVException("Invalid cvv: " + cvv);
            }
            return cvv;
        } catch (CVVException e) {
            LOGGER.info("Error: " + e.getMessage());
            return -1;
        }
    }

    //method to check if a CVV is valid
    public boolean isValidCVV(int cvv) {
        return cvv < 100 || cvv > 999;
    }

    //method to calculate minimum payment
    public void calculateMinPayment() {

        //if balance greater than  1000, take 2% of balance
        if (getBalance() > 1000) {
            minimumPayment = 0.02 * getBalance();
        }
        //if balance between 25 and 1000, fixed amount 25
        else if (getBalance() >= 25 && getBalance() <= 1000) {
            minimumPayment = 25;
        }
        //if balance less than 25, take full balance
        else if (getBalance() < 25) {
            minimumPayment = getBalance();
        }
    }

    //method paying min payment from checking account
    public void payMinPayment(CheckingAccount account) {
        account.withdrawal(minimumPayment);
        minimumPayment = 0;
    }

    //method checking if min payment has been paid
    public boolean hasMinPayment() {
        return minimumPayment == 0;
    }

    //method calculating interest rate for late min payment
    @Override
    public double calculateInterestRate() {
        if (getCustomer().getCreditScore() >= 750) {
            interestRate = 0.1;
        } else if (getCustomer().getCreditScore() >= 650) {
            interestRate = 0.15;
        } else {
            interestRate = 0.2;
        }
        return interestRate;
    }

    //method to calculate for minimum payment
    @Override
    public void makePurchase(double amount) {
        double outStandingBalance = getBalance();
        if (getBalance() + amount < getCreditLimit()) {
            outStandingBalance += amount;
            setBalance(outStandingBalance);
            LOGGER.info("Purchase successful");
            getTransactionList().add(new Transaction(amount, "Purchase"));
            LOGGER.info("Purchase " + amount + " successful from " + getAccountType());
        } else {
            LOGGER.info("Purchase failed! the purchase amount excess credit limit on your card");
        }
    }

    //method print list credit card
    @Override
    public void printList() {
        LOGGER.info(String.format("%60s", "Transaction List of Credit card "));
        LinkedListCustom<Transaction> creditCardList = getTransactionList();
        for (int i = 0; i < creditCardList.getSize(); i++) {
            LOGGER.info(creditCardList.get(i));
        }
    }

    @Override
    public String toString() {
        return ("Name: " + getCustomer().getCustomerName() + "\n" +
                "Credit card number: " + getCreditCardNum() + "\n" +
                "Expiration date : " + getExpirationDate() + "    " +
                "CVV: " + getCvv() + " \n" + "Pin: " + getPin() + "\n" +
                "Credit limit: " + getCreditLimit() + "\n");
    }

}
