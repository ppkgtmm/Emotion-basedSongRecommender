package utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

/*
 *  utils.Utils.java
 *
 *  This class is helper class for UI to convert
 *  user input or check it.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
public class Utils
{
    private static int convertToInt(String number){
        try {
            return Integer.parseInt(number);
        }
        catch (Exception e) {
            System.out.println("Please enter integer for option");
            return -1;
        }
    }

    /**
     * convert user input string to integer if possible.
     * @param option string input
     * @return integer or -1 in case failed to convert
     */
    public static int parseOption(String option)
    {
        String[] options = option.split(" ");
        /* if option has not only spaces */
        if(option.length()>0)
        {
            /* getting first portion of input string if there were space between */
            option = options[0];
            return convertToInt(option);

        }
        return -1;
    }

    /**
     * Check if string is a number or not
     * @param numericString string to check
     * @return true if string is a number, else false
     */
    public static boolean isNumeric(String numericString) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (numericString == null) {
            return false;
        }
        return pattern.matcher(numericString).matches();
    }

    /**
     * set space to have only single spaced input
     * @param   input   input string
     * @return  input string as single spaced
     */
    public static String setSpaces(String input)
    {
        StringBuilder result = new StringBuilder();
        String[] splitWords = input.split(" ");
        for (String word:splitWords)
        {
            if(word.trim().length() == 0){
                continue;
            }
            /* to make string single spaced */
            result.append(word).append(" ");
        }
        /* remove extra space */
        return result.toString().trim();
    }

    /**
     * check if user entered choice is valid
     * @param choice entered choice
     * @param limit upper limit of choice
     * @return true if valid, else false.
     */
    public static boolean isValidChoice(int choice, int limit)
    {
        return choice >= 0 && choice <= limit;
    }

    public static void printStringList(ArrayList<String> strings){
        for (String currentString: strings) {
            System.out.println(currentString);
        }
    }
}
