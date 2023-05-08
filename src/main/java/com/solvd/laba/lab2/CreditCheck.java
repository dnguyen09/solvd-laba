package com.solvd.laba.lab2;

public class CreditCheck {
    /*method to check if an account qualifies for a credit*/
    public static String isQualifyAcc(Customer customer) {
        //check both age and credit score
        if (customer.getCreditScore() < 580 && customer.getAge() < 18) {
            return """
                    Both your age and score are not met requirement\s
                    Your age is at least 18 to be qualified
                    Your credit score needs at least 580 to be qualified
                    """;
        }
        //check age
        else if (customer.getAge() < 18) {
            return "Your age is at least 18 to be qualified\n";
        }
        //check credit score
        else if (customer.getCreditScore() < 580) {

            return "Your credit score needs at least 580 to be qualified\n" ;
        }
        else {
            return "Qualified";
        }
    }
}
