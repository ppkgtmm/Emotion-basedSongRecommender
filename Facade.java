import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class Facade
{
    private static SongManager songManager = SongManager.getInstance();
    private static SongEmotions songEmotions = SongEmotions.getInstance();
    private static EmotionManager emotionManager = EmotionManager.getInstance();

    public static boolean doSetting(String songFileName,String emotionFileName,String songEmotionFileName)
    {
        boolean bOk = true;
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
            System.out.println("Successfully reading file "+songFileName+"," + emotionFileName +","+ songEmotionFileName+"\n");
            //syncSongEmotion();
            //syncDeletedSongs();
        }
        return bOk;
    }

    public static void syncSongEmotion()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        for (int counter = 0; counter < emotionManager.getAmountEmotions(); counter++)
        {
            String currentEmotion = allEmotions.get(counter);
            ArrayList<String> currentWords = emotionManager.getEmotionWords(currentEmotion);
            songEmotions.sync(currentEmotion,currentWords,allSongs);
        }
    }

    public static boolean syncDeletedSongs()
    {
        boolean bOk = true;
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

    public static void printAllEmotions()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        System.out.println(">> Emotion List <<");
        if(allEmotions == null)
        {
            System.out.println("No any emotions ");
        }
        else
        {
            for (int counter = 0; counter < emotionManager.getAmountEmotions(); counter++)
            {
                System.out.println("Emotion: " + allEmotions.get(counter));
            }
        }
    }

    public static boolean printLyrics(int songID)
    {
        boolean bOk = true;
        if(songManager.isSongID(songID))
        {
            Song currentSong = songManager.getASong(songID);
            ArrayList<String> currentLyric = currentSong.getLyrics();
            System.out.println(">> Lyric of "+ currentSong.getTitle() + " <<");
            if(currentLyric == null)
            {
                System.out.println("There are not any lyric of " + currentSong);
                bOk = false;
            }
            else
            {
                for (int counter = 0; counter < currentLyric.size(); counter++)
                {
                    System.out.println(currentLyric.get(counter));
                }
            }
        }
        else
            {
                System.out.println("There are no "+ songID);
                bOk = false;
            }
        return bOk;
    }

    public static void printAllSongs()
    {
        ArrayList<Song> allSongs = songManager.getAllSongs();
        System.out.println(">> Songs List <<");
        if(allSongs == null)
        {
            System.out.println("No any song ");
        }
        else
        {
            for (int counter = 0; counter < songManager.getAmountSongs(); counter++)
            {
                Song currentSong = allSongs.get(counter);
                System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
            }
        }
    }

    ///บ่อด้ายยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยงงงงงงงงงงงงง
    public static boolean printSongs(String keyword)
    {
        boolean bOk = true;
        ArrayList<Song> allSongKeyword = songManager.getSongs(keyword);
        System.out.println(">> Song with Keyword List <<");
        System.out.println(allSongKeyword);
        if(allSongKeyword==null)
        {
            System.out.println("There are no any song with " + keyword);
            bOk = false;
        }
        else
        {
            for (int counter = 0; counter < allSongKeyword.size(); counter++)
            {
                Song currentSong = allSongKeyword.get(counter);
                System.out.println(currentSong. getId()+" Tilte: " + currentSong.getTitle());
            }
        }
        return bOk;
    }

    public static boolean removeFromCategory(int songID,String emotionID)
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

    public static boolean addEmotion(String emotion,ArrayList<String> words)
    {
        boolean bOk = true;
        if(emotionManager.isEmotionID(emotion))
        {
            System.out.println("There already had " + emotion);
            bOk = false;
        }
        else
        {
            if(!emotionManager.addEmotion(emotion,words))
                {
                    bOk = false;
                    System.out.println("Error cannot added this "+ emotion);
                }
        }
        return bOk;
    }

    public static boolean isEmotionID(String emotion)
    {
        boolean bOk = true;
        if(!emotionManager.isEmotionID(emotion))
            bOk = false;
        return bOk;
    }

    public static boolean isSongID(Integer songID)
    {
        boolean bOk = true;
        if(!songManager.isSongID(songID))
            bOk = false;
        return bOk;
    }

    ///บ่อด้ายยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยยงงงงงงงงงงงงง
    public static void printSongsFromEmotion(String emotionID)
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

    public static boolean writeToFile()
    {
        boolean bOk = true;
        if(!songEmotions.writeRemovedSongs())
        {
            System.out.println("Error cannot write removed songs file");
            bOk = false;
        }
        else
        {
            if(!emotionManager.writeEmotions())
            {
                System.out.println("Error cannot write emotion file");
                bOk = false;
            }
        }
        return bOk;
    }

    public static void main(String[] args)
    {
        Facade manager = new Facade();
        if(manager.doSetting("songs.txt","emotions.txt","removed.txt"))
        {
            System.out.println("Reading file");
        }

        ArrayList<String> words = new ArrayList<String>();
        words.add("Volvo");
        words.add("BMW");
        words.add("Ford");
        System.out.println(words);
        manager.addEmotion("Hungry",words);
        manager.printAllEmotions();

        //manager.printSongsFromEmotion("Love");
        //manager.removeFromCategory(5,"Happy");
        //manager.writeToFile();
    }
}