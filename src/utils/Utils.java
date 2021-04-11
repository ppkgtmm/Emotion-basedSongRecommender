package utils;

import interfaces.Data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


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
        if (isInvalidString(option)) return -1;
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
        if (input == null) throw new InvalidParameterException("Invalid input to reformat");
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


    public static Data getChosenItem(int option, ArrayList<Data> data) {
        if (data == null || option == 0) return null;
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

    public static boolean isValidData(Data data, String errorMessage) {
        if (!isValidData(data)) {
            if (errorMessage != null) {
                System.out.println(errorMessage);
            }
            return false;
        }
        return true;
    }

    public static boolean isValidData(Data data) {
        return data != null && data.getTitle() != null && data.getDetails() != null && !data.getTitle().trim().isEmpty() && !data.getDetails().isEmpty();
    }


    public static boolean shouldStopGetStrings(
            String input,
            String stopSignal,
            ArrayList<String> stringsGot,
            String errMessage
    ) {
        if (input == null || stopSignal == null || stringsGot == null) {
            throw new InvalidParameterException("Invalid argument(s) provided");
        }
        if (input.compareTo(stopSignal) == 0) {
            if (stringsGot.size() == 0 && errMessage != null) {
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
        if (word == null || word.trim().isEmpty()) {
            System.out.println("Word should not be empty");
            return null;
        }
        if (isNumeric(word)) {
            System.out.println("Word should not be only number");
            return null;
        }
        return setSpaces(word);
    }

    public static boolean isInvalidString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isInvalidList(List list) {
        return list == null || list.isEmpty();
    }
}
