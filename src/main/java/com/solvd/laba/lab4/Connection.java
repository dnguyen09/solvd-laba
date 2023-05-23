package com.solvd.laba.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Connection {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);

    /*properties*/
    private final String message;

    /*constructor*/
    public Connection(String message) {
        this.message = message;
    }

    /*methods*/
    public String processing() {
        return "processing...";
    }
}
