package com.solvd.laba.lab1;

import com.solvd.laba.lab2.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInfo {
    //logger
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //check if args array has argument
        if (args.length > 0) {
            //assign name
            String name = args[0];

            //print out name
            logger.info("Name: " + name);
        } else {
            logger.info("Please include name");
        }
    }
}
