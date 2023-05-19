package com.solvd.laba.lab2.interfaces;

import com.solvd.laba.lab2.exception.CVVException;

public interface CardCreating {
    String generateExpirationDate();

    int generateCVV() throws CVVException;

    boolean isValidCVV(int cvv);
}

