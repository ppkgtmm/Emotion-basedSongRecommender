import java.util.Scanner;
import java.util.ArrayList;

public class Tester
{
    private static final String songsFileName = "songs.txt";
    private static final String emotionsFileName = "emotions.txt";
    private static final String removedFileName = "removed.txt";
    private static Facade facade = Facade.getInstance();
    private static Scanner inputLine = new Scanner(System.in);
    public static int parseOption(String option)
    {
        int parsedOption = -1;
        option = option.split(" ")[0];
        try
        {
            parsedOption = Integer.parseInt(option);
        }
        catch (Exception e)
        {
            if(e instanceof NumberFormatException)
            {
                System.out.println("Please enter integer for option");
            }
            else
            {
                e.printStackTrace();
            }
        }
        return parsedOption;
    }
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
        return parseOption(input);
    }

//    public static boolean isNumeric(String checkString)
//    {
//        return checkString.chars().allMatch( Character::isDigit );
//    }

//    public static String properCase (String inputVal)
//    {
//        // Empty strings should be returned as-is.
//        if (inputVal.length() == 0) return "";
//        // Strings with only one character uppercased.
//        if (inputVal.length() == 1) return inputVal.toUpperCase();
//        // Otherwise uppercase first letter, lowercase the rest.
//        return inputVal.substring(0,1).toUpperCase()
//            + inputVal.substring(1).toLowerCase();
//    }

    public static int getHowFindSong()
    {
        System.out.println("How would you like to find a song? ");
        System.out.println("1 Find from all songs list");
        System.out.println("2 Find from keyword in title");
        System.out.println("Please enter an option");
        String  input = inputLine.nextLine();
        return parseOption(input);
    }

    public static void seeLyrics()
    {
            int selectedChoice = getHowFindSong();
            switch (selectedChoice)
            {
                case 1:
                    facade.seeLyricsFromList();
                    break;
                case 2:
                    String keyword = inputLine.nextLine();
                    facade.seeLyricsFromKeyWord(keyword);
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
    }


//    public static void findSongFromEmotion()
//    {
//        System.out.println("\n>>Option 5: Find song from emotion");
//        boolean returnToMenu = false;
//        while(!returnToMenu)
//        {
//            Facade.printAllEmotions();
//            System.out.println("Enter emotionID to show(Enter to return back): ");
//            Scanner scan = new Scanner(System.in);
//            String inputLine = scan.nextLine();
//            if(!inputLine.matches(".*\\w.*"))
//            {
//                returnToMenu = true;
//            }
//            else
//            {
//                Facade.printSongsFromEmotion(inputLine);
//            }
//            System.out.println("\n");
//        }
//    }

//    public static void addEmotion()
//    {
//        System.out.println("\n>>Option 6: Add emotion");
//        boolean returnToMenu = false;
//        while(!returnToMenu)
//        {
//            Facade.printAllEmotions();
//            System.out.println("Enter emotionID name(Enter to return back): ");
//            Scanner scan = new Scanner(System.in);
//            String newEmotionID = scan.nextLine();
//            if(!newEmotionID.matches(".*\\w.*"))
//            {
//                returnToMenu = true;
//            }
//            else
//            {
//                boolean addedEmotion = false;
//                ArrayList<String> words = new ArrayList<>();
//                while(!addedEmotion)
//                {
//                    newEmotionID = properCase(newEmotionID);
//                    System.out.println("Enter "+ newEmotionID +" words to show(Enter to return back): ");
//                    Scanner scan2 = new Scanner(System.in);
//                    String inputLine = scan2.nextLine();
//                    if(!inputLine.matches(".*\\w.*"))
//                    {
//                        boolean checkeAdded = Facade.addEmotion(inputLine, words);
//                        if(checkeAdded)
//                        {
//                            addedEmotion = true;
//                            returnToMenu = true;
//                        }
//                    }
//                    else
//                    {
//                        words.add(inputLine);
//                    }
//                    System.out.println(words+"\n");
//                }
//            }
//            System.out.println("\n");
//        }
//    }

    public static void removeSongFromEmotion()
    {
        
    }

    public static void main(String[] args)
    {
        if(facade.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            boolean notExit = true;
            while(notExit)
            {
                int chosenChoice = Tester.getOption();
                switch (chosenChoice)
                {
                    case 1 :
                        facade.printAllSongs();
                        break;
                    case 2 :
                        seeLyrics();
                        break;
                    case 3 :
                        facade.printAllEmotions();
                        break;
                    case 4 :
                        facade.findSongByTitle();
                        break;
                    case 5 :
                        //findSongFromEmotion()
                        System.out.println("Option 5");
                        break;
                    case 6 :
                        
                        break;
                    case 7 :
                        System.out.println("Option 7");
                        break;
                    case 8 :
                        System.out.println("Exiting from the system\n");
                        notExit = false;
                        break;
                    case -1 :
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        break;
                }
                System.out.println("\n--------------------------------------------------------\n");
                inputLine.close();
            }
        }
    }

}