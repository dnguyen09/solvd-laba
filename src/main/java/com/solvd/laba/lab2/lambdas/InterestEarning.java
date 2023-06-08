package com.solvd.laba.lab2.lambdas;

@FunctionalInterface
public interface InterestEarning<T> {
    T calculate(double interestRate, int year);
}
