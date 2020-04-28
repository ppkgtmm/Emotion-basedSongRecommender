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

    public static void main(String[] args)
    {
        if(facilitator.doSetting(songsFileName,emotionsFileName,removedFileName))
        {
            boolean notExit = true;
            while(notExit)
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
                        facilitator.findSongByTitle();
                        break;
                    case 5 :
                        facilitator.findSongFromEmotion();
                        break;
                    case 6 :
                        
                        break;
                    case 7 :
                        boolean bOk = facilitator.removeFromCategory(findSongFromKeyword());
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
                        //
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