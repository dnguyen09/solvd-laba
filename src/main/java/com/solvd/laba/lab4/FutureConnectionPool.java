package com.solvd.laba.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureConnectionPool {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    /*properties*/
    private final int poolSize;
    private final ConcurrentLinkedDeque<Connection> futureConnectionPool;           //store connections
    private ExecutorService executor;
    private int availableConnection;

    /*Constructor*/
    public FutureConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        this.availableConnection = 0;
        this.futureConnectionPool = new ConcurrentLinkedDeque<>();
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    /*getter and setter*/
    public int getAvailableConnection() {
        return availableConnection;
    }

    /*Methods*/
    //initialize connection pool and available connection
    public void initialize() {
        for (int i = 0; i < poolSize; i++) {
            futureConnectionPool.offer(createConnection());
            availableConnection++;
        }
    }

    //get connection from connection pool using CompletableFuture
    public CompletableFuture<Connection> getConnection() throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            synchronized (this) {
                while (availableConnection == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
                Connection connection = futureConnectionPool.poll();
                availableConnection--;
                return connection;
            }
        });
    }

    //release connection back to connection pool after execution done
    public void releaseConnection(Connection connection) {
        //synchronize to ensure only one thread at a time can execute
        synchronized (this) {
            futureConnectionPool.offer(connection);
            availableConnection++;
            notify();
        }
    }

    //establish connection
    public Connection createConnection() {
        return new Connection("New connection");
    }

    /*-----Main-----*/
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //initialize connection pool object size 5
        FutureConnectionPool futureConnectionPool = new FutureConnectionPool(5);

        //initialize connection pool
        futureConnectionPool.initialize();

        //create threadPool with 7 thread total
        ExecutorService threadPool = Executors.newFixedThreadPool(7);

        //list of future
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        //5 threads representing that will get connection
        for (int i = 0; i < 5; i++) {
            int threadID = i + 1;       //assign thread id from 1 to 5

            // Get connection
            CompletableFuture<Connection> connectionFuture = futureConnectionPool.getConnection();

            // Perform execution on the connection
            CompletableFuture<Void> task = connectionFuture.thenAccept(connection -> {
                LOGGER.info("Thread {} get connection - Available connection left: {}", threadID, futureConnectionPool.getAvailableConnection());

                // Processing connection
                LOGGER.info("Thread {} - {}", threadID, connection.processing());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Release the connection back when done
                futureConnectionPool.releaseConnection(connection);
                LOGGER.info("Thread {} - Process done! - Available connection: {}", threadID, futureConnectionPool.getAvailableConnection());
            });

            // Add the task to the list of futures
            futures.add(task);

        }

        //2 threads representing that will wait until next available connection
        for (int i = 0; i < 2; i++) {
            int threadID = i + 6;       //assign thread id from 1 to 5

            // Get connection
            CompletableFuture<Connection> connectionFuture = futureConnectionPool.getConnection();

            // Perform execution on the connection
            CompletableFuture<Void> task = connectionFuture.thenAccept(connection -> {
                //print message when there is no connection
                if (futureConnectionPool.getAvailableConnection() == 0) {
                    LOGGER.info("Thread {} is waiting for an available connection...", threadID);
                }

                LOGGER.info("Thread {} get connection - Available connection: {}", threadID, futureConnectionPool.getAvailableConnection());

                // Processing connection
                LOGGER.info("Thread {} - {}", threadID, connection.processing());

                // Release the connection back when done
                futureConnectionPool.releaseConnection(connection);
                LOGGER.info("Thread {} - Process done! - Available connection: {}", threadID, futureConnectionPool.getAvailableConnection());
            });

            // Add the task to the list of futures
            futures.add(task);
        }

        // return a CompletableFuture that is completed
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Shut down the thread pool
        threadPool.shutdown();
    }
}
