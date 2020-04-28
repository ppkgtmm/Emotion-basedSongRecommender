import java.util.ArrayList;
import java.util.Scanner;

public class Facilitator
{
    private SongManager songManager=null;
    private SongEmotions songEmotions = null;
    private EmotionManager emotionManager = null;
    private static Facilitator facilitator = null;

    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }
    public static Facilitator getInstance()
    {
        if (facilitator==null)
        {
            facilitator = new Facilitator();
        }
        return facilitator;
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

    }
    public void seeLyricsFromKeyWord(String keyword)
    {

    }

//    public boolean removeFromCategory(int songID,String emotionID)
//    {
//        boolean bOk = true;
//        if(songManager.isSongID(songID)&&emotionManager.isEmotionID(emotionID))
//        {
//            Song currentSong = songManager.getASong(songID);
//            if(!songEmotions.removeFromCategory(currentSong,emotionID))
//            {
//                System.out.println("Error remove " + songID);
//                bOk = false;
//            }
//        }
//        else if(!songManager.isSongID(songID))
//        {
//            System.out.println("There are no " + songID);
//            bOk = false;
//        }
//        else if(!emotionManager.isEmotionID(emotionID))
//        {
//            System.out.println("There are no " + emotionID);
//            bOk = false;
//        }
//        else
//        {
//            System.out.println("There are no " + songID + " and "+ emotionID);
//            bOk = false;
//        }
//        return bOk;
//    }

}
