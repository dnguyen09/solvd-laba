package com.solvd.laba.lab2.lambdas;

@FunctionalInterface
public interface AccountFilter<T> {
    boolean check(T t);
}
