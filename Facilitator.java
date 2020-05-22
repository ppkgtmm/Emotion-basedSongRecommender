/**
 * Help to hide details of classes behind the scene
 * from UI. Also, Facilitates communication
 * of UI with internal classes.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;

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

    /**
     * call function to get all songs in the system
     * @return all songs in the system
     */
    public ArrayList<Song> getAllSongs()
    {
       return songManager.getAllSongs();
    }


    /**
     * call function to get songs using keyword in song title
     * @param keyword keyword in song title
     * @return songs which title contain the keyword
     */
    public ArrayList<Song> getSongs(String keyword)
    {
        return songManager.getSongs(keyword);
    }

    /**
     * call function to get all emotions stored in the system
     * @return  all emotions stored in the system so far.
     */
    public ArrayList<Emotion> getAllEmotions()
    {
        return emotionManager.getEmotions();
    }

    /**
     * get songs based on emotion
     * @param emotion emotion of which songs we want to get
     * @return songs related to specified emotion
     */
    public ArrayList<Song> getSongsFromEmotion(Emotion emotion)
    {
        return songEmotions.getSongsFromEmotion(emotion.getEmotion());
    }

    /**
     * Removes specified song from specified emotion category.
     * @param emotion emotion to remove song from
     * @param song song to remove from emotion category
     * @return true if succeed to remove else, false.
     */
    public boolean removeSongFromCategory(Emotion emotion,Song song)
    {
        return songEmotions.removeFromCategory(song,emotion.getEmotion());
    }

    /**
     * add new emotion and word related to the system and call function
     * to count the scores of songs for the new emotion.
     * @param emotion emotion to add
     * @return true if succeed else, false.
     */
    public boolean addEmotion(Emotion emotion)
    {
        boolean bOk = emotionManager.addEmotion(emotion);
        /* counting songs scores for new emotion */
        songEmotions.sync(emotion.getEmotion(),emotion.getWords(),songManager.getAllSongs());
        return bOk;

    }

    /**
     * do some settings before exiting from the system.
     * @return true if settings were done successfully else, false.
     */
    public boolean terminate()
    {
        /* writing emotions in the system to text file */
        boolean emotionsWritten = emotionManager.writeEmotions();
        /* writing songs removed from emotion to text file */
        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
