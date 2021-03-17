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

    public static int parseOption(String option)
    {
        String[] options = option.split(" ");
        if(option.length()>0)
        {
            option = options[0];
            return convertToInt(option);

        }
        return -1;
    }

    public static boolean isNumeric(String numericString) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (numericString == null) {
            return false;
        }
        return pattern.matcher(numericString).matches();
    }

    public static String setSpaces(String input)
    {
        StringBuilder result = new StringBuilder();
        String[] splitWords = input.split(" ");
        for (String word:splitWords)
        {
            if(word.trim().length() == 0){
                continue;
            }
            result.append(word).append(" ");
        }
        return result.toString().trim();
    }

    public static boolean isValidChoice(int choice, int limit)
    {
        return choice >= 0 && choice <= limit;
    }

    public static void printStringList(ArrayList<String> strings){
        for (String currentString: strings) {
            System.out.println(currentString);
        }
    }

    public static boolean gotEnoughStrings(String input, String stopSignal, ArrayList<String> stringsGot, String errMessage){
        if(input.compareTo(stopSignal) == 0){
            if(stringsGot.size()==0){
                System.out.println(errMessage);
            }
            return stringsGot.size()==0;
        }
        return false;
    }
}
