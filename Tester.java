/**
 * Tester.java
 *
 * This class let user interact with the system. It has 7 features
 * to serve user including Show all song titles, Show all emotions
 * Show lyrics of a song, Find song from title, Find music from emotion
 * Remove song from emotion category, Add new emotion category.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Tester
{
//    private static final String songsFileName = "songs.txt";
//    private static final String emotionsFileName = "emotions.txt";
//    private static final String removedFileName = "removed.txt";
//    /** facilitator instance used to help communication in the system */
//    private static Facilitator facilitator = Facilitator.getInstance();
//    /** used to get input from user */
//    private static Scanner inputLine = new Scanner(System.in);
//
//    /**
//     * Display options and get the selected option
//     * from user
//     * @return selected option
//     */
//    public static int getOption()
//    {
//        System.out.println("Options:");
//        System.out.println("1. See all songs");
//        System.out.println("2. See lyrics");
//        System.out.println("3. See all emotions");
//        System.out.println("4. Find song by title");
//        System.out.println("5. Find song based on emotion");
//        System.out.println("6. Add emotion");
//        System.out.println("7. Remove song from emotion category");
//        System.out.println("8. Exit");
//        System.out.println("Please select an option: ");
//        String  input = inputLine.nextLine();
//        return Utils.parseOption(input);
//    }
//
//    /**
//     * Ask a way to find a song from user
//     * @return way to find a song
//     */
//    private static int getHowFindSong()
//    {
//        System.out.println("How would you like to find a song? ");
//        System.out.println("1 Find from all songs list");
//        System.out.println("2 Find from keyword in title");
//        System.out.println("Please enter an option");
//        String  input = inputLine.nextLine();
//        return Utils.parseOption(input);
//    }
//
//    /**
//     * Display message and get input related to message
//     * from user
//     * @param message message to display
//     * @return user input
//     */
//    private static String getInputString(String message)
//    {
//        System.out.println(message);
//        return inputLine.nextLine();
//    }
//
//    /**
//     * Help user to see song lyrics by calling function to
//     * get way to find song and ask for more input if
//     * needed. Lastly, call function to process user request
//     * or report error.
//     */
//    private static void seeLyrics()
//    {
//            int selectedChoice = getHowFindSong();
//            switch (selectedChoice)
//            {
//                /* user want to choose song from all songs list to see lyrics */
//                case 1:
//                    facilitator.seeLyricsFromList();
//                    break;
//                /* user want to find song from keyword to see lyrics */
//                case 2:
//                    String keyword = getInputString("Please enter keyword ");
//                    facilitator.seeLyricsFromKeyWord(keyword);
//                    break;
//                /* invalid option entered */
//                default:
//                    System.out.println("Please enter a valid option");
//                    break;
//            }
//    }
//
//    /**
//     * Check if string is a number or not
//     * @param numericString string to check
//     * @return true if string is a number, else false
//     */
//    private static boolean isNumeric(String numericString) {
//        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
//        if (numericString == null) {
//            return false;
//        }
//        return pattern.matcher(numericString).matches();
//    }
//
//    /**
//     * set space to have only single spaced input
//     * @param   input   input string
//     * @return  input string as single spaced
//     */
//    private static String setSpaces(String input)
//    {
//        String result = "";
//        String[] splitWords = input.split(" ");
//        for (String word:splitWords)
//        {
//            /* to make string single spaced */
//            result += word + " ";
//        }
//        /* remove extra space */
//        return result.trim();
//    }
//
//    /**
//     * Add emotion but first ask for new emotion and
//     * words related to that emotion. The input will
//     * be validated.
//     * @return true if succeed, false if it cannot add emotion
//     */
//    private static boolean addEmotion()
//    {
//        ArrayList<String> words = new ArrayList<>();
//        /* get emotion input */
//        String emotion = getInputString("Please enter emotion to add").trim();
//        /* validate emotion input. Ask until get valid input (if needed) */
//        while(!emotion.matches("[a-zA-Z.!\\- ']+") || emotion.length()==0)
//        {
//            System.out.println("Emotion is not a valid word");
//            emotion = getInputString("Please enter emotion to add again").trim();
//        }
//        /* get words related to emotion */
//        while(true)
//        {
//            String word = getInputString("Enter words for emotion or _done_ : ").trim();
//            /* user have finished entering word */
//            if(word.compareToIgnoreCase("_done_")==0 && words.size()>0)
//            {
//                break;
//            }
//            /* user want to finish but have not given any words */
//            else if(word.compareToIgnoreCase("_done_")==0 && words.size()==0)
//            {
//                System.out.println("No words have been added to word collection");
//            }
//            /* word is a number */
//            else if(isNumeric(word))
//            {
//                System.out.println("Word should not be only number");
//            }
//            /* nothing entered */
//            else if(word.isEmpty())
//            {
//                System.out.println("Word should not be empty");
//            }
//            else
//            {
//                /* make word single spaced */
//                word = setSpaces(word).trim();
//                /* duplicate word found */
//                if(words.indexOf(word)!=-1)
//                {
//                    continue;
//                }
//                words.add(word);
//            }
//        }
//        return facilitator.addEmotion(emotion,words);
//    }
//
//   /**
//    * Get keyword used to find song from user and call
//    * function to find song
//    *
//    */
//    private static void findSongByTitle()
//    {
//        String keyword = getInputString("Enter keyword in song title: ");
//        facilitator.printSongs(keyword);
//
//    }
//
//    /**
//     * write emotions and removed songs to text file
//     * by calling function and close scanner object
//     * @return true if written successfully, false if
//     * it error occurred
//     */
//    private static boolean endProgram()
//    {
//        inputLine.close();
//        return facilitator.terminate();
//    }

    public static void main(String[] args)
    {
//        /* set songs, emotions and removed song info in the program successfully */
//        if(facilitator.doSetting(songsFileName,emotionsFileName,removedFileName))
//        {
//            boolean bOk;
//            while(true)
//            {
//                /* get user choice of what to do */
//                int chosenChoice = Tester.getOption();
//                switch (chosenChoice)
//                {
//                    /* See all songs */
//                    case 1 :
//                        facilitator.printAllSongs();
//                        break;
//                    /* See lyrics */
//                    case 2 :
//                        seeLyrics();
//                        break;
//                    /* See all emotions */
//                    case 3 :
//                        facilitator.printAllEmotions();
//                        break;
//                    /* Find song by title */
//                    case 4 :
//                        findSongByTitle();
//                        break;
//                    /* Find song based on emotion */
//                    case 5 :
//                        facilitator.findSongFromEmotion();
//                        break;
//                    /* Add emotion */
//                    case 6 :
//                        bOk = addEmotion();
//                        if(bOk)
//                        {
//                            System.out.println("Emotion added successfully");
//                        }
//                        else
//                        {
//                            System.out.println("Cannot to add emotion");
//                        }
//                        break;
//                    /* Remove song from emotion category */
//                    case 7 :
//                        bOk = facilitator.removeFromCategory();
//                        if(bOk)
//                        {
//                            System.out.println("Song removed from emotion category successfully");
//                        }
//                        else
//                        {
//                            System.out.println("Cannot remove song from emotion category");
//                        }
//                        break;
//                    /* exit the program */
//                    case 8 :
//                        System.out.println("Exiting from the system\n");
//                        System.out.println("--------------------------------------------------------\n");
//                        boolean succeed = endProgram();
//                        if (succeed)
//                        {
//                            System.exit(0);
//                        } else
//                        {
//                            System.exit(1);
//                        }
//                        break;
//                    default:
//                        System.out.println("Please enter a valid option");
//                        break;
//                }
//            }
//        }
    }

}