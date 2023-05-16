package com.solvd.laba.lab2;

import com.solvd.laba.lab2.enums.CardType;
import com.solvd.laba.lab2.enums.TransactionType;
import com.solvd.laba.lab2.exception.CVVException;
import com.solvd.laba.lab2.interfaces.CardCreating;
import com.solvd.laba.lab2.linkedllst.LinkedListCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DebitCard extends Account implements CardCreating {
    //logger
    private static final Logger LOGGER = LogManager.getLogger(DebitCard.class);

    /*declare properties*/
    private long debitCardNumb;
    private String expirationDate;
    private int cvv;
    private int pin;

    /*constructor*/
    public DebitCard(long accountNumber, CardType cardType, Customer customer, long debitCardNumb, String expirationDate, int cvv, int pin) {
        super(accountNumber, cardType, customer);
        this.debitCardNumb = debitCardNumb;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.pin = pin;
    }

    public DebitCard(Account account, CardType cardType, int pin) {
        super(account.getCustomer(), account.getBalance());
        this.setCardType(cardType);
        this.debitCardNumb = generateNumber();
        this.expirationDate = generateExpirationDate();
        this.cvv = generateCVV();
        this.pin = pin;
    }


    /*Getters and Setters*/
    public long getDebitCardNumb() {
        return debitCardNumb;
    }

    public void setDebitCardNumb(int debitCardNumb) {
        this.debitCardNumb = debitCardNumb;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /*methods*/
    //method to generate debit card number
    @Override
    public long generateNumber() {
        String idDebit = "6677";
        Random random = new Random();
        int randDebit = random.nextInt(100000) + 1000;
        //Concat String with lastAccNum to not get same number generated
        String DebitCardNum = idDebit + randDebit + lastAccNum;
        lastAccNum++;
        return Long.parseLong(DebitCardNum);
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
            LOGGER.error("Error: " + e.getMessage());
            return -1;
        }
    }

    //method to check if a CVV is valid
    public boolean isValidCVV(int cvv) {
        return cvv < 100 || cvv > 999;
    }

    //override method withdrawal in Account class
    @Override
    public void withdrawal(double amount) {
        if (amount > getBalance()) {
            LOGGER.info("Withdrawal failed! the amount withdrawal excess balance");
        } else {
            setBalance(getBalance() - amount);
            getTransactionList().add(new Transaction(amount, TransactionType.WITHDRAWAL));
            LOGGER.info("Withdrawal " + amount + " successful from " + getCardType());
        }
    }

    //override method deposit form Account class
    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        getTransactionList().add(new Transaction(amount, TransactionType.DEPOSIT));
        LOGGER.info("Deposit " + amount + " successful to " + getCardType());
    }

    //override method makePurchase from Account class
    @Override
    public void makePurchase(double amount) {
        if (amount > getBalance()) {
            LOGGER.info("Purchase failed! your account don't have enough balance for making purchase");
        } else {
            setBalance(getBalance() - amount);
            getTransactionList().add(new Transaction(amount, TransactionType.PURCHASE));
            LOGGER.info("Purchase " + amount + " successful from " + getCardType());
        }
    }

    //method printing debit card transaction list
    @Override
    public void printList() {
        LOGGER.info(String.format("%60s", "Transaction List of Debit card "));
        LinkedListCustom<Transaction> debitCardList = getTransactionList();
        for (int i = 0; i < debitCardList.getSize(); i++) {
            LOGGER.info(debitCardList.get(i));
        }
    }

    @Override
    public String toString() {
        return ("Name: " + getCustomer().getCustomerName() + "\n" +
                "Debit card number: " + getDebitCardNumb() + "\n" +
                "Expiration date : " + getExpirationDate() + "    " +
                "CVV: " + getCvv() + " \n" + "Pin: " + getPin() + "\n");
    }
}
