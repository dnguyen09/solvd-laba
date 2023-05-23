package com.solvd.laba.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionPool {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    /*properties*/
    private final int poolSize;
    private final ConcurrentLinkedDeque<Connection> connectionPool;           //store connections
    private int availableConnection;

    /*Constructor*/
    public ConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        this.availableConnection = 0;
        this.connectionPool = new ConcurrentLinkedDeque<>();
    }

    /*getter and setter*/
    public int getAvailableConnection() {
        return availableConnection;
    }

    /*Methods*/
    //initialize connection pool and available connection
    public void initialize() {
        for (int i = 0; i < poolSize; i++) {
            connectionPool.offer(createConnection());
            availableConnection++;
        }
    }

    //get connection from connection pool
    public Connection getConnection() throws InterruptedException {
        //synchronize to ensure only one thread at a time can acquire a connection
        synchronized (this) {
            //check if there are any available connection. If not, using wait method
            while (availableConnection == 0) {
                wait();                                         //wait till being notified
            }
            Connection connection = connectionPool.poll();      //connection is acquired
            availableConnection--;                              //update count of available connections
            return connection;
        }
    }

    //release connection back to connection pool after execution done
    public void releaseConnection(Connection connection) {
        //synchronize to ensure only one thread at a time can execute
        synchronized (this) {
            connectionPool.offer(connection);  //add the released connection back to the pool
            availableConnection++;             //update count of available connections
            notify();                          //notify a waiting thread, if any
        }
    }

    //establish connection
    private Connection createConnection() {
        return new Connection("Establish new connection");
    }


    /*-----Main-----*/
    public static void main(String[] args) {
        //initialize connection pool object size 5
        ConnectionPool connectionPool = new ConnectionPool(5);

        //initialize connection pool
        connectionPool.initialize();

        //create threadPool with 7 thread total
        ExecutorService threadPool = Executors.newFixedThreadPool(7);

        //5 threads representing that will get connection
        for (int i = 0; i < 5; i++) {
            int threadID = i + 1;       //assign thread id from 1 to 5
            threadPool.submit(() -> {
                try {
                    //get connection
                    Connection connection = connectionPool.getConnection();
                    LOGGER.info("Thread {} - Available connection:{}", threadID, connectionPool.getAvailableConnection());

                    //processing connection
                    LOGGER.info("Thread {} ", threadID + " " + connection.processing());

                    Thread.sleep(3000);

                    //release the connection back when done
                    connectionPool.releaseConnection(connection);
                    LOGGER.info("Thread {} - Process done! - Available connection: {}", threadID, connectionPool.getAvailableConnection());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //2 threads representing that will wait until next available connection
        for (int i = 0; i < 2; i++) {
            int threadID = i + 6;       //assign thread id from 6 to 7

            threadPool.submit(() -> {
                try {
                    //get connection
                    Connection connection = connectionPool.getConnection();
                    LOGGER.info("Thread {} - Available connection:{}", threadID, connectionPool.getAvailableConnection());

                    //processing connection
                    connection.processing();

                    //release the connection back when done
                    connectionPool.releaseConnection(connection);
                    LOGGER.info("Thread {} - Process done! - Available connection: {}", threadID, connectionPool.getAvailableConnection());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //shut down when all threads are completed
        threadPool.shutdown();
    }
}
