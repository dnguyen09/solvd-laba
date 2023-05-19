package com.solvd.laba.lab2.enums;

public enum AccountType {
    CHECKING("Checking accoutn"),
    SAVING("Saving account");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
