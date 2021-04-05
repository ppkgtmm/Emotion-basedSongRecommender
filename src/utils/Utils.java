package utils;

import interfaces.Data;
import reader.dto.ReaderDTO;

import java.util.ArrayList;
import java.util.Scanner;
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
public class Utils {
    private static int convertToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            System.out.println("Please enter integer for option");
            return -1;
        }
    }

    public static int parseOption(String option) {
        String[] options = option.split(" ");
        if (option.length() > 0) {
            return convertToInt(options[0]);

        }
        return -1;
    }

    public static boolean isNumeric(String numericString) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (numericString == null) {
            return false;
        }
        return pattern.matcher(numericString.trim()).matches();
    }

    public static String setSpaces(String input) {
        StringBuilder result = new StringBuilder();
        String[] splitWords = input.split(" ");
        for (String word : splitWords) {
            if (word.trim().length() == 0) {
                continue;
            }
            result.append(word).append(" ");
        }
        return result.toString().trim();
    }

    public static boolean isValidChoice(int choice, int start, int end) {
        return choice >= start && choice <= end;
    }

    public static void printStringList(ArrayList<String> strings) {
        for (String currentString : strings) {
            System.out.println(currentString);
        }
    }

    public static Data getChosenItem(int option, ArrayList<Data> data) {
        if (option == 0) return null;
        if (!isValidChoice(option, 1, data.size())) {
            System.out.println("Invalid choice selected");
            return null;
        }
        return data.get(option - 1);
    }

    public static Data getChosenItem(String option, ArrayList<Data> data) {
        int choice = parseOption(option);
        if (choice == -1) {
            return null;
        }
        return getChosenItem(choice, data);
    }


    public static boolean gotSomeStrings(
            String input,
            String stopSignal,
            ArrayList<String> stringsGot,
            String errMessage
    ) {
        if (input.compareTo(stopSignal) == 0) {
            if (stringsGot.size() == 0) {
                System.out.println(errMessage);
            }
            return stringsGot.size() != 0;
        }
        return false;
    }

    public static String getInputString(String message, Scanner scanner) {
        System.out.println(message);
        return scanner.nextLine().trim();
    }

    public static String getValidWord(String word) {
        if (word.isEmpty()) {
            System.out.println("Word should not be empty");
            return null;
        }
        if (Utils.isNumeric(word)) {
            System.out.println("Word should not be only number");
            return null;
        }
        return setSpaces(word);
    }

    public static boolean isValidData(Data data, String errorMessage) {
        if (!isValidData(data)) {
            System.out.println(errorMessage);
            return false;
        }
        return true;
    }

    public static boolean isValidData(Data data) {
        return data.getTitle() != null && data.getDetails() != null && !data.getDetails().isEmpty();
    }
}
