package com.solvd.laba.lab2;


import com.solvd.laba.lab2.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    /*declare properties*/
    private double amount;
    private TransactionType type;
    private LocalDateTime time;

    /*constructor*/
    public Transaction(double amount, TransactionType type) {
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
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
}
