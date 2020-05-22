/**
 * UI.java
 * This class acts as interface between user and the system.
 * It communicates with the facilitator to proceed user
 * request.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.util.ArrayList;
import java.util.Scanner;

public class UI
{
    /** facilitator instance used to help communication in the system */
    private static Facilitator facilitator = Facilitator.getInstance();
    /** used to get input from user */
    private static Scanner inputLine = new Scanner(System.in);

    /**
     * Display options and get the selected option
     * from user
     * @return selected option
     */
    private static int getOption()
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
     * @return way to find a song
     */
    private static int getHowFindSong()
    {
        System.out.println("How would you like to find a song? ");
        System.out.println("1 Find from all songs list");
        System.out.println("2 Find from keyword in title");
        System.out.println("Please enter an option");
        String  input = inputLine.nextLine();
        return Utils.parseOption(input);
    }

    /**
     * Display message and get input related to message
     * from user
     * @param message message to display
     * @return user input
     */
    private static String getInputString(String message)
    {
        System.out.println(message);
        return inputLine.nextLine();
    }

    /**
     * Call function to display songs and then get user selected
     * song to print lyrics.
     * @param songs available songs
     */
    private static void getSongPrintLyrics(ArrayList<Song> songs)
    {
        Display.printSongs(songs);
        if(songs!=null && songs.size()>0)
        {
            String input = getInputString("Please enter song number");
            int selectedChoice = Utils.parseOption(input);
            /* check for valid song number */
            if(Utils.isValidChoice(selectedChoice,songs.size()))
            {
                selectedChoice--;
                /* call function to print lyrics of valid song choice */
                Display.printLyrics(songs.get(selectedChoice));
            }
            else
            {
                System.out.println("You have entered invalid song number");
            }
        }
    }
    /**
     * Help user to see song lyrics by calling function to
     * get way to find song and ask for more input if
     * needed. Lastly, call function to process user request
     * or report error.
     */
    private static void seeLyrics()
    {
        int selectedChoice = getHowFindSong();
        String input;
        ArrayList<Song> songs = null;
        switch (selectedChoice)
        {
            /* user want to choose song from all songs list to see lyrics */
            case 1:
                songs = facilitator.getAllSongs();
                getSongPrintLyrics(songs);
                break;
            /* user want to find song from keyword to see lyrics */
            case 2:
                String keyword = getInputString("Please enter keyword ");
                songs = facilitator.getSongs(keyword);
                getSongPrintLyrics(songs);
                break;
            /* invalid option entered */
            default:
                System.out.println("Please enter a valid option");
                break;
        }
    }

    /**
     * collect words from user related to new emotion to be added
     * to the system.
     * @return list of words related to user entered emotion
     */
    private static ArrayList<String> getEmotionWords()
    {
        ArrayList<String> words = new ArrayList<>();
        while(true)
        {
            String word = getInputString("Enter words for emotion or _done_ : ").trim();
            /* user have finished entering word */
            if(word.compareToIgnoreCase("_done_")==0 && words.size()>0)
            {
                break;
            }
            /* user want to finish but have not given any words */
            else if(word.compareToIgnoreCase("_done_")==0 && words.size()==0)
            {
                System.out.println("No words have been added to word collection");
            }
            /* word is a number */
            else if(Utils.isNumeric(word))
            {
                System.out.println("Word should not be only number");
            }
            /* nothing entered */
            else if(word.isEmpty())
            {
                System.out.println("Word should not be empty");
            }
            else
            {
                /* make word single spaced */
                word = Utils.setSpaces(word).trim();
                /* duplicate word found */
                if(words.indexOf(word)!=-1)
                {
                    continue;
                }
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Add emotion but first ask for new emotion and
     * words related to that emotion. The input will
     * be validated.
     */
    private static void addEmotion()
    {
        /* get emotion input */
        String emotion = getInputString("Please enter emotion to add").trim();
        /* validate emotion input. Ask until get valid input (if needed) */
        while(!emotion.matches("[a-zA-Z.!\\- ']+") || emotion.length()==0)
        {
            System.out.println("Emotion is not a valid word");
            emotion = getInputString("Please enter emotion to add again").trim();
        }
        /* get words related to emotion */
        ArrayList<String> words = getEmotionWords();
        Emotion newEmotion = new Emotion(emotion.trim().toLowerCase(),words);
        boolean bOk = facilitator.addEmotion(newEmotion);
        if(bOk)
        {
            System.out.println("Emotion added successfully");
        }
        else
        {
            System.out.println("Cannot to add emotion");
        }
    }

    /**
     * Get keyword used to find song from user and call
     * function to find and display song(s)
     *
     */
    private static void findSongByTitle()
    {
        String keyword = getInputString("Enter keyword in song title: ");
        Display.printSongs(facilitator.getSongs(keyword));

    }

    /**
     * Get emotion and song input from user and remove song from
     * that emotion category
     */
    public static void removeFromCategory()
    {
        Emotion emotion = getEmotionInput();
        /* there are emotions stored and user chose valid one */
        if(emotion!=null)
        {
            /* get songs to display and let user select */
            ArrayList<Song> songs = facilitator.getSongsFromEmotion(emotion);
            Display.printSongs(songs);
            if(songs!=null && songs.size()>0)
            {
                String input = getInputString("Enter song number");
                int option = Utils.parseOption(input);
                /* valid song number */
                if(Utils.isValidChoice(option,songs.size()))
                {
                    option--;
                    boolean succeed = facilitator.removeSongFromCategory(emotion,songs.get(option));
                    if(succeed)
                    {
                        System.out.println("Song removed from emotion category successfully");
                    }
                    else
                    {
                        System.out.println("Cannot remove song from emotion category");
                    }
                }
            }
        }
    }

    /**
     * write emotions and removed songs to text file
     * by calling function and close scanner object and
     * then exit from the system
     */
    private static void endProgram()
    {
        inputLine.close();
        boolean succeed = facilitator.terminate();
        System.out.println("Exiting from the system\n");
        System.out.println("--------------------------------------------------------\n");
        if (succeed)
        {
            System.exit(0);
        } else
        {
            System.exit(1);
        }
    }

    /**
     * call function to display emotion and then get emotion
     * input from user
     * @return user selected emotion or null if invalid
     * emotion number entered or no emotions in the system
     */
    private static Emotion getEmotionInput()
    {
        ArrayList<Emotion> allEmotion = facilitator.getAllEmotions();
        Display.printAllEmotions(allEmotion);
        if(allEmotion!=null && allEmotion.size()>0)
        {
            String input = getInputString("Please enter emotion number");
            int selectedChoice = Utils.parseOption(input);
            if(Utils.isValidChoice(selectedChoice,allEmotion.size()))
            {
                selectedChoice --;
                return allEmotion.get(selectedChoice);
            }
            else
            {
                System.out.println("Invalid emotion number entered");
            }
        }
        return null;
    }

    /**
     * call function to get emotion input from user then return
     * songs in that emotion category by help of facilitator
     * function.
     * @return  list of song that belongs to user selected
     * emotion
     */
    private static ArrayList<Song> findSongFromEmotion()
    {
        Emotion emotion = getEmotionInput();
        if(emotion!=null)
        {
            return facilitator.getSongsFromEmotion(emotion);
        }
        return null;
    }

    /**
     * process user selected option from the main menu.
     * @param option option that user has selected
     *
     */
    private static void proceedOption(int option)
    {
        switch (option)
        {
            /* See all songs */
            case 1 :
                Display.printSongs(facilitator.getAllSongs());
                break;
            /* See lyrics */
            case 2 :
                seeLyrics();
                break;
            /* See all emotions */
            case 3 :
                Display.printAllEmotions(facilitator.getAllEmotions());
                break;
            /* Find song by title */
            case 4 :
                findSongByTitle();
                break;
            /* Find song based on emotion */
            case 5 :
                Display.printSongs(findSongFromEmotion());
                break;
            /* Add emotion */
            case 6 :
                addEmotion();
                break;
            /* Remove song from emotion category */
            case 7 :
                removeFromCategory();
                break;
            /* exit the program */
            case 8 :
                endProgram();
                break;
            default:
                System.out.println("Please enter a valid option");
                break;
        }
    }

    /**
     * uses to run the program to be able to use its functionality.
     * Has public visibility specifier to help running in case
     * main method is in another file.
     * @param songsFileName song text file name
     * @param emotionsFileName emotion text file name
     * @param removedFileName removed song text file name
     */
    public static void run(String songsFileName, String emotionsFileName, String removedFileName)
    {
        if(facilitator.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            /* let user use system features */
            while(true)
            {
                int option = getOption();
                proceedOption(option);
            }
        }
        /* cannot set up data */
        else
        {
            System.out.println("Failed to do system set up --- exiting");
            System.exit(1);
        }
    }
    public static void main(String[] args)
    {
        /* not enough input file name provided */
        if(args.length<2)
        {
            System.out.println("You should provide at least SongFile and EmotionFile");
            System.exit(1);
        }
        /* all text file names provided (song, emotion, removed song)*/
        else if(args.length>2)
        {
            UI.run(args[0],args[1],args[2]);
        }
        /* removed song file name not provided */
        else
        {
            UI.run(args[0],args[1],"");
        }

    }
}
