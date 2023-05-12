package com.solvd.laba.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInfo {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(UserInfo.class);

    public static void main(String[] args) {
        //check if args array has argument
        if (args.length > 0) {
            //assign name
            String name = args[0];

            //print out name
            LOGGER.info("Name: " + name);
        } else {
            LOGGER.info("Please include name");
        }
    }
}
