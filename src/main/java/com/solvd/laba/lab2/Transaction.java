package com.solvd.laba.lab2;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Transaction implements Iterable<Transaction> {
    /*declare properties*/
    private double amount;
    private String type;
    private LocalDateTime time;

    /*constructor*/
    public Transaction(double amount, String type) {
        this.amount = amount;
        this.type = type;
        this.time = LocalDateTime.now();
    }

    /*getters and setters*/
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /*Methods*/
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss.SS");
        String formattedTime = time.format(formatter);
        return formattedTime + " - " + type + " - " + amount;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return new TransactionIterator();
    }

    private class TransactionIterator implements Iterator<Transaction> {
        private Transaction currentTransaction = Transaction.this;

        @Override
        public boolean hasNext() {
            return currentTransaction != null;
        }

        @Override
        public Transaction next() {
            Transaction nextTransaction = currentTransaction;
            currentTransaction = null;
            return nextTransaction;
        }
    }
}
