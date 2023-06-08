package com.solvd.laba.lab3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FindUniqueCharacter {
    //count the number of times letter 'a' appears
    public static String countALetter(String text) {
        return "Number of 'a' letter: " + StringUtils.countMatches(text, "a");
    }

    //count number of time letter 't' in uppercase or lowercase
    public static String countTLetter(String text) {
        return "Number of 't' letter: " + StringUtils.countMatches(text.toLowerCase(), "t");
    }

    //count all word in text
    public static String countAllUniqueWord(String text) {
        // Split the text into words
        String[] words = StringUtils.split(text);

        // Iterate through the words and clean them
        for (int i = 0; i < words.length; i++) {

            // Remove trailing punctuation marks
            words[i] = words[i].replaceAll("[.,!?]+$", "");
        }

        // Create a set to store unique words
        Set<String> uniqueWords = new HashSet<>();

        // Iterate through the cleaned words
        for (String word : words) {
            // Exclude empty words
            if (!word.isEmpty()) {
                uniqueWords.add(word);
            }
        }

        return "Total unique words: " + uniqueWords.size();
    }

    //method to write to file
    public static void countAndWriteToFile(String filePath, String method) {
        try {
            FileUtils.writeStringToFile(new File(filePath), "\n" + method, "UTF-8", true);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        String text;
        String fileName = "src/main/resources/FindUniqueTextWord.txt";

        try {
            //reading file content from a file using FileUtil
            text = FileUtils.readFileToString(new File("src/main/resources/TextWord.txt"), "UTF-8");

            //print result to a file
            FileUtils.writeStringToFile(new File("src/main/resources/FindUniqueTextWord.txt"), text, "UTF-8");

            //call method count letter a and append to file Unique Text Word
            countAndWriteToFile(fileName, countALetter(text));

            //call method count letter t and append to file Unique Text Word
            countAndWriteToFile(fileName, countTLetter(text));

            //call method count all word and append to file Unique Text Word
            countAndWriteToFile(fileName, countAllUniqueWord(text));

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file name" + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("File is empty" + e.getMessage());
        }
    }
}
