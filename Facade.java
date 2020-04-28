import java.util.ArrayList;
import java.util.Scanner;

public class Facade
{
    private SongManager songManager=null;
    private SongEmotions songEmotions = null;
    private EmotionManager emotionManager = null;
    private static Facade facade = null;

    private Facade()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }
    public static Facade getInstance()
    {
        if (facade==null)
        {
            facade = new Facade();
        }
        return facade;
    }

    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        boolean songOK = songManager.readSongs(songFileName);
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        boolean removedSongsOK;
        boolean ok = false;
        if(!removedSongsFile.isEmpty())
        {
            removedSongsOK = syncDeletedSongs(removedSongsFile);
            if(songOK && emotionOK && removedSongsOK)
            {
                doSync();
                ok = true;
            }
        }
        else if(songOK && emotionOK)
        {
         doSync();
         ok = true;
        }
        return ok;
    }
    public void doSync()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        for(String emotion:allEmotions)
        {
            songEmotions.sync(emotion,emotionManager.getEmotionWords(emotion),allSongs);
        }
    }
    public boolean syncDeletedSongs(String fileName)
    {
        return songEmotions.initialize(fileName);
    }

    public void printAllEmotions()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        if(allEmotions==null || allEmotions.size()==0)
        {
            System.out.println("There are no emotions stored in the system");
        }
        else
        {
            System.out.println(">> Emotion List <<");
            for (int counter = 0; counter < allEmotions.size(); counter++)
            {
                System.out.println((counter+1)+ " " + allEmotions.get(counter));
            }
        }
    }
    public void printLyrics(int songID)
    {
        boolean bOk = true;
        ArrayList<Song> allSong = songManager.getAllSongs();
        if(songID>allSong.size() || songID<1)
        {
            System.out.println("Please enter a valid song number");
        }
        else
        {
            songID--;
            Song currentSong = allSong.get(songID);
            if(currentSong.getId()==(songID+1))
            {
                ArrayList<String> currentLyrics = currentSong.getLyrics();
                if(currentLyrics!=null && currentLyrics.size()>0)
                {
                    System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                    for (int counter = 0; counter < currentLyrics.size(); counter++)
                    {
                        System.out.println(currentLyrics.get(counter));
                    }
                }
                else
                {
                    System.out.println("There are no lyrics for " + currentSong.getTitle());
                }
            }
            else
            {
                System.out.println("Song mismatch error occurred");
            }

        }

    }
    public void printAllSongs()
    {
        ArrayList<Song> allSongs = songManager.getAllSongs();
        if(allSongs == null || allSongs.size()==0)
        {
            System.out.println("No songs available");
        }
        else
        {
            System.out.println(">> All Songs List <<");
            for (Song currentSong : allSongs) {
                System.out.println(currentSong.getId() + " " + currentSong.getTitle());
            }
        }
    }
    public void printSongs(String keyword)
    {
        ArrayList<Song> songsFound = songManager.getSongs(keyword);
        if(songsFound==null || songsFound.size()==0)
        {
            System.out.println("No songs found for "+keyword);
        }
        else
        {
            System.out.println(">> Song with Keyword List <<");
            for (int counter = 0; counter < songsFound.size(); counter++)
             {
                 Song currentSong = songsFound.get(counter);
                 System.out.println((counter+1) +" " + currentSong.getTitle());
             }
        }
    }

    public void findSongByTitle()
    {
        System.out.println("Enter keyword in song title: ");
        Scanner scan = new Scanner(System.in);
        String inputLine = scan.nextLine();
        printSongs(inputLine);
        System.out.println("\n");
    }

    public void seeLyricsFromList()
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
                printLyrics(songID);
            }
            else
            {
                System.out.println("Please input SongID");
            }
            System.out.println("\n");
        }
    }

    public void seeLyricsFromKeyWord(String keyword)
    {

    }

    public void findSongFromEmotion()
    {
        boolean returnToMenu = false;
        while(!returnToMenu)
        {
            printAllEmotions();
            System.out.println("Enter emotionID to show(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String inputLine = scan.nextLine();
            if(!inputLine.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else if(isNumeric(inputLine))
            {
                System.out.println("EmotionID need to be any words");
            }
            else if(!emotionManager.isEmotionID(inputLine))
            {
                System.out.println("There are not " + inputLine +" in emotionID");
            }
            else
            {
                printSongsFromEmotion(inputLine);
            }
            System.out.println("\n");
        }
    }

    public void addEmotion()
    {
        System.out.println("\n>>Option 6: Add emotion");
        boolean returnToMenu = false;
        printAllEmotions();
        while(!returnToMenu)
        {
            System.out.println("Enter emotionID name(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String newEmotionID = scan.nextLine();
            newEmotionID = properCase(newEmotionID);
            if(!newEmotionID.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else if(isNumeric(newEmotionID))
            {
                System.out.println("EmotionID need to be any words");
            }
            else if(emotionManager.isEmotionID(newEmotionID))
            {
                System.out.println("There already had "+ newEmotionID);
            }
            else
            {
                ArrayList<String> words = addEmotionWords(newEmotionID);
                if(words != null)
                {
                    emotionManager.addEmotion(newEmotionID, words);
                }
            }
            System.out.println("\n");
        }
    }

    public void removeSongFromEmotion()
    {
        System.out.println("\n>>Option 7: Remove a song from emotion");
        boolean returnToMenu = false;
        printAllEmotions();
        while(!returnToMenu)
        {
            System.out.println("Enter emotionID name(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String emotionID = scan.nextLine();
            emotionID = properCase(emotionID);
            if(!emotionID.matches(".*\\w.*"))
            {
                returnToMenu = true;
            }
            else if(isNumeric(emotionID))
            {
                System.out.println("EmotionID need to be any words");
            }
            else if(!emotionManager.isEmotionID(emotionID))
            {
                System.out.println("There are not "+ emotionID);
            }
            else
            {
                boolean returnToRemove = false;
                printSongsFromEmotion(emotionID);
                while(!returnToRemove)
                {
                    System.out.println("Enter songID to remove from"+ emotionID +"(Enter to return back): ");
                    Scanner scann = new Scanner(System.in);
                    String inputLine = scann.nextLine();
                    if(!inputLine.matches(".*\\w.*"))
                    {
                        returnToRemove = true;
                    }
                    else if(!isNumeric(inputLine))
                    {
                        System.out.println("SongID is an integer number");
                    }
                    else
                    {
                        int songID = Integer.parseInt(inputLine);
                        if(!removeFromCategory(songID,emotionID))
                            System.out.println("Please input any words to find a song again");
                    }
                    System.out.println("\n");
                }
            }
            System.out.println("\n");
        }
    }

    public void printSongsFromEmotion(String emotionID)
    {
        if(emotionManager.isEmotionID(emotionID))
        {
            ArrayList<Song> allSongsFromEmotion = songEmotions.getSongsFromEmotion(emotionID);
            System.out.println(">> All songs of "+ emotionID +" emotion <<");
            for (int counter = 0; counter < allSongsFromEmotion.size(); counter++)
            {
                Song currentSong = allSongsFromEmotion.get(counter);
                System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
            }
        }
        else
            System.out.println("There are no " + emotionID);
    }

    public boolean isNumeric(String checkString)
    {
        return checkString.chars().allMatch( Character::isDigit );
    }

    public String properCase (String inputVal)
    {
        // Empty strings should be returned as-is.
        if (inputVal.length() == 0) return "";
        // Strings with only one character uppercased.
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        // Otherwise uppercase first letter, lowercase the rest.
        return inputVal.substring(0,1).toUpperCase()
            + inputVal.substring(1).toLowerCase();
    }

    public ArrayList<String> addEmotionWords(String newEmotion)
    {
        ArrayList<String> words = new ArrayList<String>();
        boolean hasNext = true;
        while(hasNext)
        {
            System.out.println("Enter "+ newEmotion +" words to show(Enter to return back): ");
            Scanner scan = new Scanner(System.in);
            String word = scan.nextLine();
            if(!word.matches(".*\\w.*"))
            {
                hasNext = false;
            }
            else if(isNumeric(word))
            {
                System.out.println("Please input any words");
            }
            else
            {
                boolean found = false;
                int counter=0;
                while(counter<words.size())
                {
                    if(words.get(counter).equalsIgnoreCase(word))
                    {
                        found = true;
                        break;
                    }
                    counter++;
                }
                if(!found)
                {
                    words.add(word);
                }
                else
                    System.out.println("There already had "+ word);
            }
        }
        System.out.println(words);
        return words;
    }

   public boolean removeFromCategory(int songID,String emotionID)
   {
       boolean bOk = true;
       if(songManager.isSongID(songID)&&emotionManager.isEmotionID(emotionID))
       {
           Song currentSong = songManager.getASong(songID);
           if(!songEmotions.removeFromCategory(currentSong,emotionID))
           {
               System.out.println("Error remove " + songID);
               bOk = false;
           }
       }
       else if(!songManager.isSongID(songID))
       {
           System.out.println("There are no " + songID);
           bOk = false;
       }
       else if(!emotionManager.isEmotionID(emotionID))
       {
           System.out.println("There are no " + emotionID);
           bOk = false;
       }
       else
       {
           System.out.println("There are no " + songID + " and "+ emotionID);
           bOk = false;
       }
       return bOk;
   }

}
