import java.util.regex.Pattern;

/*
 * This class is helper class for converting option
 * from menu that user has input to integer.
 * get from user.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
public class Utils
{
    /**
     * convert user input string to integer if possible.
     * @param option option input as string
     * @return integer or -1 in case failed to convert
     */
    public static int parseOption(String option)
    {
        int parsedOption = -1;
        String[] options = option.split(" ");
        /* if option has not only spaces */
        if(option.length()>0)
        {
            /* getting first portion of input string */
            option = options[0];
            try
            {
                /* converting type to integer */
                parsedOption = Integer.parseInt(option);
            }
            catch (Exception e)
            {
                /* non number input entered */
                if(e instanceof NumberFormatException)
                {
                    System.out.println("Please enter integer for option");
                }
                /* other error occurs */
                else
                {
                    e.printStackTrace();
                }
            }
        }
        return parsedOption;
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
        String result = "";
        String[] splitWords = input.split(" ");
        for (String word:splitWords)
        {
            /* to make string single spaced */
            result += word + " ";
        }
        /* remove extra space */
        return result.trim();
    }

    public static boolean isValidChoice(int choice, int limit)
    {
        return choice > 0 && choice <= limit;
    }
}
