import java.util.Scanner;
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

    public static void seeAllSongs()
    {
        System.out.println("Option 1: See all songs");
        Facade.printAllSongs();
    }

    public static void seeLyrics()
    {
        System.out.println("Option 2: See lyrics");
        System.out.println("Enter songID to show: ");
        Scanner inputLine = new Scanner(System.in);
        int songID = inputLine.nextInt();
        Facade.printLyrics(songID);
    }

    public static void seeAllEmotions()
    {

    }

    public static void findSongByTitle()
    {
        
    }

    public static void findSongFromEmotion()
    {
        
    }

    public static void addEmotion()
    {
        
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
                        //System.out.println("Option 1");
                        seeAllSongs();
                        break;
                    case 2 :
                        //System.out.println("Option 2");
                        seeLyrics();
                        break;
                    case 3 :
                        System.out.println("Option 3");
                        break;
                    case 4 :
                        System.out.println("Option 4");
                        break;
                    case 5 :
                        System.out.println("Option 5");
                        break;
                    case 6 :
                        System.out.println("Option 6");
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