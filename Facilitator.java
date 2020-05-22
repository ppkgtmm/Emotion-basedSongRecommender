/**
 *
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Facilitator
{
    /** SongManager instance to help dealing with songs. */
    private SongManager songManager = null;

    /**
     *  SongEmotions instance to help dealing with songs
     *  related to each emotion.
     */
    private SongEmotions songEmotions = null;

    /** EmotionManager instance to help dealing with emotions. */
    private EmotionManager emotionManager = null;

    /** Facilitator instance which let other classes use its methods*/
    private static Facilitator facilitator = null;

    /** Scanner object used to get input from user */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor which initialize songManager, songEmotions
     * and emotionManager instance
     */
    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }

    /**
     * Getter for instance of facilitator which is created only
     * once.
     * @return facilitator instance
     */
    public static Facilitator getInstance()
    {
        if (facilitator==null)
        {
            facilitator = new Facilitator();
        }
        return facilitator;
    }

    /**
     * set up data inside the program like songs, emotions and
     * removed songs which will be used in the program
     * @param songFileName name of song text file
     * @param emotionFileName name of emotion text file
     * @param removedSongsFile name of removed song text file
     * @return true if succeed , false if error occurred
     */
    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        /* to read and store emotions */
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        /* to read and store songs */
        boolean songOK = songManager.readSongs(songFileName);
        /* to store result from doing set up for songs removed from emotion category */
        boolean removedSongsOK;
        boolean ok = false;
        /* user has provided removed songs file name */
        if(!removedSongsFile.isEmpty())
        {
            /* to read and store removed songs */
            removedSongsOK = songEmotions.initialize(removedSongsFile);
            /* check if no errors found */
            if(songOK && emotionOK && removedSongsOK)
            {
                /* to count emotions scores of song and store song sorted
                in emotion category */
                doSync();
                ok = true; /* no errors */
            }
        }
        /* user does not provide removed song file name */
        else if(songOK && emotionOK)
        {
            /* to count emotions scores of song and store song sorted
                in emotion category */
             doSync();
             ok = true; /* no errors */
        }
        return ok;
    }

    /**
     * call function to count emotions scores of songs and store songs
     * in sorted order in each emotion category.
     */
    private void doSync()
    {
        ArrayList<Emotion> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        /* to count score of songs for each emotion */
        for(Emotion emotion:allEmotions)
        {
            songEmotions.sync(emotion.getEmotion(),emotion.getWords(),allSongs);
        }
    }

    public ArrayList<Song> getAllSongs()
    {
       return songManager.getAllSongs();
    }


    public ArrayList<Song> getSongs(String keyword)
    {
        return songManager.getSongs(keyword);
    }

    public ArrayList<Emotion> getAllEmotions()
    {
        return emotionManager.getEmotions();
    }

    public ArrayList<Song> getSongsFromEmotion(Emotion emotion)
    {
        return songEmotions.getSongsFromEmotion(emotion.getEmotion());
    }

    public boolean removeSongFromCategory(Emotion emotion,Song song)
    {
        return songEmotions.removeFromCategory(song,emotion.getEmotion());
    }

    public boolean addEmotion(Emotion emotion)
    {
        boolean bOk = emotionManager.addEmotion(emotion);
        songEmotions.sync(emotion.getEmotion(),emotion.getWords(),songManager.getAllSongs());
        return bOk;

    }
    public boolean terminate()
    {
        scanner.close();
        boolean emotionsWritten = emotionManager.writeEmotions();
        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
