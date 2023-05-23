package com.solvd.laba.lab2.Lambdas;

@FunctionalInterface
public interface InterestEarning<T> {
    T calculate(double interestRate, int year);
}
