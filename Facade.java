import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class Facade
{
    private SongManager songManager;
    private SongEmotions songEmotions;
    private EmotionManager emotionManager;

    public boolean doSetting(String songFileName,String emotionFileName,String songEmotionFileName)
    {
        boolean bOk = true;
        SongManager songManager = SongManager.getInstance();
        SongEmotions songEmotions = SongEmotions.getInstance();
        EmotionManager emotionManager = EmotionManager.getInstance();
        if(!songManager.readSongs(songFileName))
        {
            System.out.println("Error reading song file "+ songFileName);
            bOk = false;
        }
        if(!emotionManager.readEmotions(emotionFileName))
        {
            System.out.println("Error reading emotion file "+ emotionFileName);
            bOk = false;
        }
        if(!songEmotions.initialize(songEmotionFileName))
        {
            System.out.println("Error reading song emotion file "+ songEmotionFileName);
            bOk = false;
        }
        if(bOk)
        {
            System.out.println("Successfully reading file "+songFileName+"," + emotionFileName +","+ songEmotionFileName);
            syncSongEmotion();
        }
        return bOk;
    }

    public void syncSongEmotion()
    {
        SongManager songManager = SongManager.getInstance();
        SongEmotions songEmotions = SongEmotions.getInstance();
        EmotionManager emotionManager = EmotionManager.getInstance();
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        //System.out.println("AllSongs "+ allSongs);
        //System.out.println("AllEmotions "+ allEmotions);
        for (int counter = 0; counter < allEmotions.size(); counter++)
        {
            String currentEmotion = allEmotions.get(counter);
            ArrayList<String> currentWords = emotionManager.getEmotionWords(currentEmotion);
            //System.out.println("Emotion: " + currentEmotion);
            //System.out.println("Words: " + currentWords);
            songEmotions.sync(currentEmotion,currentWords,allSongs);
        }
    }

    public boolean syncDeletedSongs()
    {
        boolean bOk = true;
        //SongEmotions songEmotions = SongEmotions.getInstance();
        if( !songEmotions.writeRemovedSongs() )
        {
            System.out.println("Error writing RemovedSongs file");
            bOk = false;
        }
        else
        {
            System.out.println("Successfully writing RemovedSongs file");
        }
        return bOk;
    }

    public void printAllEmotions()
    {
        //EmotionManager emotionManager = EmotionManager.getInstance();
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        System.out.println(">> Emotion List <<");
        if(allEmotions == null)
        {
            System.out.println("No any emotions ");
        }
        else
        {
            for (int counter = 0; counter < allEmotions.size(); counter++)
            {
                System.out.println("Emotion: " + allEmotions.get(counter));
            }
        }
    }

    public void printLyrics(int songID)
    {
        //SongManager songManager = SongManager.getInstance();
        Song currentSong = songManager.getASong(songID);
        ArrayList<String> currentLyric = currentSong.getLyrics();
        System.out.println(">> Lyric of "+ currentSong + " <<");
        if(currentLyric == null)
        {
            System.out.println("There are not any lyric of " + currentSong);
        }
        else
        {
            for (int counter = 0; counter < currentLyric.size(); counter++)
            {
                System.out.println(currentLyric.get(counter));
            }
        }
    }

    public void printAllSongs()
    {
        //SongManager songManager = SongManager.getInstance();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        System.out.println(">> Songs List <<");
        if(allSongs == null)
        {
            System.out.println("No any song ");
        }
        else
        {
            for (int counter = 0; counter < allSongs.size(); counter++)
            {
                Song currentSong = allSongs.get(counter);
                System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
            }
        }
    }

    ///บ่อด้ายยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยงงงงงงงงงงงงง
    public void printSongs(String keyword)
    {
        //SongManager songManager = SongManager.getInstance();
        ArrayList<Song> allSongKeyword = songManager.getSongs(keyword);
        System.out.println(">> Song with Keyword List <<");
        System.out.println(allSongKeyword);
        // for (int counter = 0; counter < allSongKeyword.size(); counter++)
        // {
        //     Song currentSong = allSongKeyword.get(counter);
        //     System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
        // }
    }

    public boolean removeFromCategory(int songID,String emotionID)
    {
        boolean bOk = true;
        //SongManager songManager = SongManager.getInstance();
        //SongEmotions songEmotions = SongEmotions.getInstance();
        Song currentSong = songManager.getASong(songID);
        if(songEmotions.removeFromCategory(currentSong,emotionID))
            bOk = false;
        return bOk;
    }

    public boolean addEmotion(String emotion,ArrayList<String> words)
    {
        boolean bOk = true;
        //EmotionManager emotionManager = EmotionManager.getInstance();
        if(emotionManager.addEmotion(emotion,words))
            bOk = false;
        return bOk;
    }

    ///บ่อด้ายยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยงงงงงงงงงงงงง
    public void printSongsFromEmotion(String emotionID)
    {
        //SongEmotions songEmotions = SongEmotions.getInstance();
        ArrayList<Song> allSongsFromEmotion = songEmotions.getSongsFromEmotion(emotionID);
        System.out.println(">> All songs of "+ emotionID +" emotion <<");
        if(allSongsFromEmotion == null)
        {
            System.out.println("No any song of " + emotionID);
        }
        else
        {
            for (int counter = 0; counter < allSongsFromEmotion.size(); counter++)
            {
                Song currentSong = allSongsFromEmotion.get(counter);
                System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
            }
        }
    }

    public static void main(String[] args)
    {
        Facade manager = new Facade();
        if(manager.doSetting("songs.txt","emotions.txt","removed.txt"))
        {
            System.out.println("Reading file");
        }

        // ArrayList<String> words = new ArrayList<String>();
        // words.add("Volvo");
        // words.add("BMW");
        // words.add("Ford");
        // System.out.println(words);
        // manager.addEmotion("Hungry",words);
        // manager.printAllEmotions();

        //manager.printSongsFromEmotion("Love");
        //manager.removeFromCategory(1,"Happy");
    }
}