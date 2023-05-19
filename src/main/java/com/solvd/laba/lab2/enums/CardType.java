package com.solvd.laba.lab2.enums;

public enum CardType {
    DEBIT("Debit card "),
    CREDIT("Credit card");

    private final String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
