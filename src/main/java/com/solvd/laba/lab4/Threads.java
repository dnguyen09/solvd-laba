package com.solvd.laba.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Threads {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(Threads.class);


    public static void main(String[] args) {

        //Runnable thread
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Thread using Runnable");
            LOGGER.info("Thread id: " + Thread.currentThread().getId() + "\n");
        };
        Thread runnableThread = new Thread(runnable);

        // Thread using thread
        Thread threadUsingThread = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Thread using Thread ");
            LOGGER.info("Thread id: " + Thread.currentThread().getId() + "\n");
        });

        //start each thread
        runnableThread.start();
        threadUsingThread.start();


    }
}
