import java.util.Scanner;
import java.util.ArrayList;

public class Tester
{
    private static final String songsFileName = "songs.txt";
    private static final String emotionsFileName = "emotions.txt";
    private static final String removedFileName = "removed.txt";

    public static int selectChoice()
    {
        System.out.println("Options:");
        System.out.println("1. See all songs");
        System.out.println("2. See lyrics");
        System.out.println("3. See all emotions");
        System.out.println("4. Find song by title");
        System.out.println("5. Find song from emotion");
        System.out.println("6. Add emotion");
        System.out.println("7. Remove song from emotion");
        System.out.println("8. Exit");
        System.out.println("Please select features:");
        Scanner inputLine = new Scanner(System.in);
        int selectChoice = inputLine.nextInt();
        switch (selectChoice)
        {
            case 1 :
                selectChoice = 1;
                break;
            case 2 :
                selectChoice = 2;
                break;
            case 3 :
                selectChoice = 3;
                break;
            case 4 :
                selectChoice = 4;
                break;
            case 5 :
                selectChoice = 5;
                break;
            case 6 :
                selectChoice = 6;
                break;
            case 7 :
                selectChoice = 7;
                break;
            case 8 :
                selectChoice = 8;
                break;
            default:
                selectChoice = -1;
        }
        return selectChoice;
    }

    public static boolean isNumeric(String checkString)
    {
        return checkString.chars().allMatch( Character::isDigit );
    }

    public static String properCase (String inputVal) 
    {
        // Empty strings should be returned as-is.
        if (inputVal.length() == 0) return "";
        // Strings with only one character uppercased.
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        // Otherwise uppercase first letter, lowercase the rest.
        return inputVal.substring(0,1).toUpperCase()
            + inputVal.substring(1).toLowerCase();
    }

    public static void seeAllSongs()
    {
        System.out.println("\n>>Option 1: See all songs");
        Facade.printAllSongs();
    }

    public static void seeLyrics()
    {
        System.out.println("\n>>Option 2: See lyrics");
        boolean returnToMenu = false;
        while(!returnToMenu)
        {
            System.out.println("Enter songID to show(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String inputLine = scan.nextLine();
            if(!inputLine.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else if(isNumeric(inputLine))
            {
                int songID = Integer.parseInt(inputLine);
                Facade.printLyrics(songID);
            }
            else
            {
                System.out.println("Please input SongID");
            }
            System.out.println("\n");
        }
    }

    public static void seeAllEmotions()
    {
        System.out.println("\n>>Option 3: See all emotions");
        Facade.printAllEmotions();
    }

    public static void findSongByTitle()
    {
        System.out.println("\n>>Option 4: Find songs by title");
        boolean returnToMenu = false;
        while(!returnToMenu)
        {
            System.out.println("Enter songID to show(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String inputLine = scan.nextLine();
            if(!inputLine.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else
            {
                Facade.printSongs(inputLine);
            }
            System.out.println("\n");
        }
    }

    public static void findSongFromEmotion()
    {
        System.out.println("\n>>Option 5: Find song from emotion");
        boolean returnToMenu = false;
        while(!returnToMenu)
        {
            Facade.printAllEmotions();
            System.out.println("Enter emotionID to show(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String inputLine = scan.nextLine();
            if(!inputLine.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else
            {
                Facade.printSongsFromEmotion(inputLine);
            }
            System.out.println("\n");
        }
    }

    public static void addEmotion()
    {
        System.out.println("\n>>Option 6: Add emotion");
        boolean returnToMenu = false;
        while(!returnToMenu)
        {
            Facade.printAllEmotions();
            System.out.println("Enter emotionID name(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String newEmotionID = scan.nextLine();
            if(!newEmotionID.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else
            {
                boolean addedEmotion = false;
                ArrayList<String> words = new ArrayList<>();
                while(!addedEmotion)
                {
                    newEmotionID = properCase(newEmotionID);
                    System.out.println("Enter "+ newEmotionID +" words to show(Enter to return back): ");
                    Scanner scan2 = new Scanner(System.in);
                    String inputLine = scan2.nextLine();
                    if(!inputLine.matches(".*\\w.*"))
                    {
                        boolean checkeAdded = Facade.addEmotion(inputLine, words);
                        if(checkeAdded)
                        {
                            addedEmotion = true;
                            returnToMenu = true;
                        }
                    }
                    else
                    {
                        words.add(inputLine);
                    }
                    System.out.println(words+"\n");
                }
            }
            System.out.println("\n");
        }
    }

    public static void removeSongFromEmotion()
    {
        
    }

    public static void main(String[] args)
    {
        if(Facade.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            boolean notExit = true;
            while(notExit)
            {
                int chooseChoice = Tester.selectChoice();
                switch (chooseChoice)
                {
                    case 1 :
                        seeAllSongs();
                        break;
                    case 2 :
                        seeLyrics();
                        break;
                    case 3 :
                        seeAllEmotions();
                        break;
                    case 4 :
                        //findSongByTitle();
                        System.out.println("Option 4");
                        break;
                    case 5 :
                        //findSongFromEmotion()
                        System.out.println("Option 5");
                        break;
                    case 6 :
                        addEmotion();
                        break;
                    case 7 :
                        System.out.println("Option 7");
                        break;
                    case 8 :
                        System.out.println("Bye Bye\n");
                        notExit = false;
                        break;
                    default:
                        System.out.println("Please select 1-8 choice");
                }
                System.out.println("\n--------------------------------------------------------\n");
            }
        }
    }

}