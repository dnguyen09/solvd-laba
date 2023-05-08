package com.solvd.laba.lab2;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Customer {
    /*declare properties*/
    private String customerName;
    private LocalDate dateOfBirth;
    private int creditScore;

    /*constructors*/
    public Customer(String customerName, String dateOfBirth, int creditScore) {
        this.customerName = customerName;
        //convert dob from String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
        this.creditScore = creditScore;
    }

    public Customer() {
        this("","",0);
    }

    /*Getters and setters*/
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }

    @Override
    public String toString() {
        return "Name: " + customerName + "\nAge: " + getAge() + "\nCredit Score: " + creditScore + "\n";
    }

}
