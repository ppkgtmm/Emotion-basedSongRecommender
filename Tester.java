import java.util.Scanner;
import java.util.ArrayList;

public class Tester
{
    private static final String songsFileName = "songs.txt";
    private static final String emotionsFileName = "emotions.txt";
    private static final String removedFileName = "removed.txt";
    private static Facilitator facilitator = Facilitator.getInstance();
    private static Scanner inputLine = new Scanner(System.in);
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

    public static int getHowFindSong()
    {
        System.out.println("How would you like to find a song? ");
        System.out.println("1 Find from all songs list");
        System.out.println("2 Find from keyword in title");
        System.out.println("Please enter an option");
        String  input = inputLine.nextLine();
        return Utils.parseOption(input);
    }

    public static void seeLyrics()
    {
            int selectedChoice = getHowFindSong();
            switch (selectedChoice)
            {
                case 1:
                    facilitator.seeLyricsFromList();
                    break;
                case 2:
                    System.out.println("Please enter keyword ");
                    String keyword = inputLine.nextLine();
                    facilitator.seeLyricsFromKeyWord(keyword);
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
    }

    public static boolean addEmotion()
    {
        ArrayList<String> words = new ArrayList<>();
        System.out.println("Please enter emotion to add");
        String emotion = inputLine.nextLine();
        while(true)
        {
            System.out.println("Enter words for emotion or _done_ : ");
            String word = inputLine.nextLine();
            if(word.compareToIgnoreCase("_done_")==0 && words.size()>0)
            {
                break;
            }
            else if(word.compareToIgnoreCase("_done_")==0)
            {
                System.out.println("You must enter at least a word for emotion");
            }
            words.add(word);
        }
        return facilitator.addEmotion(emotion,words);
    }

    public static String findSongFromKeyword()
    {
        System.out.println("Do you want to find song to remove from keyword? ");
        String answer = inputLine.nextLine();
        if(answer.toLowerCase().startsWith("y"))
        {
            System.out.println("Enter keyword ");
            return inputLine.nextLine();
        }
        return "";
    }

    public static void findSongByTitle()
    {
        System.out.println("Enter keyword in song title: ");
        String keyword = inputLine.nextLine();
        facilitator.printSongs(keyword);

    }

    public static boolean endProgram()
    {
        inputLine.close();
        return facilitator.terminate();
    }
    public static void main(String[] args)
    {
        if(facilitator.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            boolean bOk;
            while(true)
            {
                int chosenChoice = Tester.getOption();
                switch (chosenChoice)
                {
                    case 1 :
                        facilitator.printAllSongs();
                        break;
                    case 2 :
                        seeLyrics();
                        break;
                    case 3 :
                        facilitator.printAllEmotions();
                        break;
                    case 4 :
                        findSongByTitle();
                        break;
                    case 5 :
                        facilitator.findSongFromEmotion();
                        break;
                    case 6 :
                        bOk = addEmotion();
                        if(bOk)
                        {
                            System.out.println("Emotion added successfully");
                        }
                        else
                        {
                            System.out.println("Failed to add emotion");
                        }
                        break;
                    case 7 :
                        bOk = facilitator.removeFromCategory(findSongFromKeyword());
                        if(bOk)
                        {
                            System.out.println("Song removed from emotion category successfully");
                        }
                        else
                        {
                            System.out.println("Failed to remove song from emotion category");
                        }
                        break;
                    case 8 :
                        System.out.println("Exiting from the system\n");
                        System.out.println("\n--------------------------------------------------------\n");
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