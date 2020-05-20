/**
 * Tester.java
 *
 * This class represents a single user in the emotion-based song
 * recommender. It has 7 features to service songs related with
 * user emotion including Show all song titles, Show all emotions
 * Show lyrics of a song, Find song from title, Find music from emotion
 * Remove song from emotion category, Add new emotion category.
 *
 *   Created by Sally Goldin, 2 October 2017
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Tester
{
    /** song text file name */
    private static final String songsFileName = "songs.txt";

    /** emotions text file name */
    private static final String emotionsFileName = "emotions.txt";

    /** removed song from emotion text file name */
    private static final String removedFileName = "removed.txt";

    /** facilitator instance */
    private static Facilitator facilitator = Facilitator.getInstance();

    /** buffer to get input data */
    private static Scanner inputLine = new Scanner(System.in);

    /**
     * Display options and get the selected option
     * from user
     * @return selected option
     */
    public static int getOption()
    {
        System.out.println("Options:");
        System.out.println("1. See all songs");
        System.out.println("2. See lyrics");
        System.out.println("3. See all emotions");
        System.out.println("4. Find song by title");
        System.out.println("5. Find song based on emotion");
        System.out.println("6. Add emotion");
        System.out.println("7. Remove song from emotion category");
        System.out.println("8. Exit");
        System.out.println("Please select an option: ");
        String  input = inputLine.nextLine();
        return Utils.parseOption(input);
    }

    /**
     * Ask a way to find a song from user
     * @return a way to find a song
     */
    public static int getHowFindSong()
    {
        System.out.println("How would you like to find a song? ");
        System.out.println("1 Find from all songs list");
        System.out.println("2 Find from keyword in title");
        System.out.println("Please enter an option");
        String  input = inputLine.nextLine();
        return Utils.parseOption(input);
    }

    /**
     * Display message and return the next line
     * @param message current message
     * @return next inputline
     */
    private static String getInputString(String message)
    {
        System.out.println(message);
        return inputLine.nextLine();
    }

    /**
     * see song lyrics feature by selecting option to see
     * from user and calling each function to display song lyrics
     */
    public static void seeLyrics()
    {
            int selectedChoice = getHowFindSong();
            switch (selectedChoice)
            {
                case 1:
                    /** see song lyrics by song number */
                    facilitator.seeLyricsFromList();
                    break;
                case 2:
                    /** see song lyrics by keyword */
                    String keyword = getInputString("Please enter keyword ");
                    facilitator.seeLyricsFromKeyWord(keyword);
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
    }

    /**
     * validate number in string 
     * @param strNum    analyzed string
     * @return true if found, false if it cannot find and number in string
     */
    private static boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    /**
     * set space to have only 1 space bar in each input
     * @param   input   inputting string
     * @return  string  with only set space bar
     */
    private static String setSpaces(String input)
    {
        String result = "";
        String[] splitWords = input.split(" ");
        for (String word:splitWords)
        {
            result += word + " ";
        }
        return result.trim();
    }

    /**
     * add emotion feature that ask for new emotion and 
     * words of related song. The input will be validated
     * and set to be legal.
     * @return true if sucess, false if it cannot add emotion
     */
    public static boolean addEmotion()
    {
        ArrayList<String> words = new ArrayList<>();
        String emotion = getInputString("Please enter emotion to add").trim();
        /** validate special letter */
        while(!emotion.matches("[a-zA-Z.!\\- ']+") || emotion.length()==0)
        {
            System.out.println("Emotion is not a valid word");
            emotion = getInputString("Please enter emotion to add again").trim();
        }
        /** insert words of current emotion */
        while(true)
        {
            /** get words of emotion */
            String word = getInputString("Enter words for emotion or _done_ : ").trim();
            /** check user want to add or finish end */
            if(word.compareToIgnoreCase("_done_")==0 && words.size()>0)
            {
                break;
            }
            /** there are no input word */
            else if(word.compareToIgnoreCase("_done_")==0 && words.size()==0)
            {
                System.out.println("No words have been added to word collection");
            }
            /** there are any numberic in input */
            else if(isNumeric(word))
            {
                System.out.println("Word should not be only number");
            }
            /** check user input empty */
            else if(word.isEmpty())
            {
                System.out.println("Word should not be empty");
            }
            else
            {
                word = setSpaces(word).trim();
                if(words.indexOf(word)!=-1)
                {
                    continue;
                }
                words.add(word);
            }
        }
        return facilitator.addEmotion(emotion,words);
    }

    /**
     * ask for a way to remove a song
     * @return the anwser
     */
    public static String findSongFromKeyword()
    {
        String answer = getInputString("Do you want to find song to remove from keyword? (y or n)");
        if(answer.toLowerCase().startsWith("y"))
        {
            return getInputString("Enter keyword ");
        }
        return "";
    }

    /**
     * find song by title feature that gets keyword from inputting
     * and calls facilitator to display songs from keywords
     */
    public static void findSongByTitle()
    {
        String keyword = getInputString("Enter keyword in song title: ");
        facilitator.printSongs(keyword);
    }

    /**
     * write new emotion and removed song to text file
     * by calling facilicator and close input
     * @return true if write, false if it cannot write text file
     */
    public static boolean endProgram()
    {
        inputLine.close();
        return facilitator.terminate();
    }
    public static void main(String[] args)
    {
        /** set songs, emotions and removed song from ctegory  */
        if(facilitator.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            boolean bOk;
            /** loop to ask option */
            while(true)
            {
                /** get selected option */
                int chosenChoice = Tester.getOption();
                switch (chosenChoice)
                {
                    /** See all songs */
                    case 1 :
                        facilitator.printAllSongs();
                        break;
                    /** See lyrics */
                    case 2 :
                        seeLyrics();
                        break;
                    /** See all emotions */
                    case 3 :
                        facilitator.printAllEmotions();
                        break;
                    /** Find song by title */
                    case 4 :
                        findSongByTitle();
                        break;
                    /** Find song based on emotion */
                    case 5 :
                        facilitator.findSongFromEmotion();
                        break;
                    /** Add emotion */
                    case 6 :
                        bOk = addEmotion();
                        if(bOk)
                        {
                            System.out.println("Emotion added successfully");
                        }
                        else
                        {
                            System.out.println("Cannot to add emotion");
                        }
                        break;
                    /** Remove song from emotion category */
                    case 7 :
                        bOk = facilitator.removeFromCategory();
                        if(bOk)
                        {
                            System.out.println("Song removed from emotion category successfully");
                        }
                        else
                        {
                            System.out.println("Cannot remove song from emotion category");
                        }
                        break;
                    /** exit program */
                    case 8 :
                        System.out.println("Exiting from the system\n");
                        System.out.println("--------------------------------------------------------\n");
                        boolean succeed = endProgram();
                        if (succeed)
                        {
                            System.exit(0);
                        } else
                        {
                            System.exit(1);
                        }
                        break;
                    case -1 :
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        break;
                }
            }
        }
    }

}