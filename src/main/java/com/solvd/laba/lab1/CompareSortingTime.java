package com.solvd.laba.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;

public class CompareSortingTime {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(CompareSortingTime.class);


    public static void insertionSort(int[] arr) {
        //iterate over the array from second element
        for (int i = 1; i < arr.length; i++) {
            //set key to current element
            int key = arr[i];
            //initialize index to the left of the current element
            int j = i - 1;
            //shift the element to the right using while loop
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            //set key to next element
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        //initialize a big size array of size 10,000
        int[] array = new int[10000];

        //initialize a new random object
        Random rand = new Random();

        //iterate over array to store random number
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(10000) + 1;
        }

        //create copy of array
        int[] arr1 = Arrays.copyOf(array, array.length);

        //calculate time execution for insertion sort in milliseconds
        long startTime = System.currentTimeMillis();
        insertionSort(array);   //call method insertionSort
        long endTime = System.currentTimeMillis();

        //calculate time execution for selection sort in milliseconds
        long startTime2 = System.currentTimeMillis();
        SelectionSort.selectionSort(arr1);     //call method selectionSort from Selection class
        long endTime2 = System.currentTimeMillis();

        //Print the outputs
        //LOGGER.info(Arrays.toString(array));
        LOGGER.info("Insertion sort - Time duration: " + (endTime - startTime) + " milliseconds");
        LOGGER.info("Selection sort - Time duration: " + (endTime2 - startTime2) + " milliseconds");
    }
}
