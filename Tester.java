import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
    private static String getInputString(String message)
    {
        System.out.println(message);
        return inputLine.nextLine();
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


    private static boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

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
    public static boolean addEmotion()
    {
        ArrayList<String> words = new ArrayList<>();
        String emotion = getInputString("Please enter emotion to add");
        while(true)
        {
            String word = getInputString("Enter words for emotion or _done_ : ").trim();
            if(word.compareToIgnoreCase("_done_")==0 && words.size()>0)
            {
                break;
            }
            else if(word.compareToIgnoreCase("_done_")==0 && words.size()==0)
            {
                System.out.println("No words have been added to word collection");
            }
            else if(isNumeric(word))
            {
                System.out.println("Word should not be only number");
            }
            else if(word.isEmpty())
            {
                System.out.println("Word should not be empty");
            }
            else
            {
                word = setSpaces(word).trim();
                words.add(word);
            }
        }
        return facilitator.addEmotion(emotion,words);
    }

    public static String findSongFromKeyword()
    {
        String answer = getInputString("Do you want to find song to remove from keyword? (y or n)");
        if(answer.toLowerCase().startsWith("y"))
        {
            return getInputString("Enter keyword ");
        }
        return "";
    }

    public static void findSongByTitle()
    {
        String keyword = getInputString("Enter keyword in song title: ");
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