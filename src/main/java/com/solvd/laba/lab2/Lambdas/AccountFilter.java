package com.solvd.laba.lab2.Lambdas;

@FunctionalInterface
public interface AccountFilter<T> {
    boolean check(T t);
}
