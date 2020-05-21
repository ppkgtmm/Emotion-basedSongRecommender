/**
 *  SongEmotions.java
 *
 *  This class represents organization to manage songs related with
 *  emotions like getting songs from emotion or removing song from
 *  emotion category etc.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions
{
    /** to store songs related to emotions in sorted order
     * keys : emotion category
     * values : list of songs related
     */
    private HashMap<String,TreeSet<Song>> songsWithEmotions;

    /** instance of SongEmotions to for managing songs related to emotions */
    private static SongEmotions songEmotions = null;

    /** store songs removed from emotion category in the past
     * keys : emotion category
     * values : list of song titles that had been removed
     */
    private HashMap<String, ArrayList<String>> songsRemoved;

    /** reader that knows how to read and parse the removed song file  */
    private RemovedSongReader removedSongReader = null;

    /**
     * Constructor which instantiates HashMaps that will be
     * used later.
     */

    private SongEmotions()
    {
        songsWithEmotions = new HashMap<>();
        songsRemoved = new HashMap<>();
    }

    /**
     * Getter for songEmotions instance which is created
     * only once.
     * @return  songEmotions instance
     */
    public static SongEmotions getInstance()
    {
        if(songEmotions==null)
        {
            songEmotions = new SongEmotions();
        }
        return songEmotions;
    }

    /**
     * Store details about songs removed from emotion by
     * calling function to read removed song file.
     * @param   fileName  removed song text file
     * @return true if successful, false if error occurred
     */
    public boolean initialize(String fileName)
    {
        boolean succeed = false;
        removedSongReader = new RemovedSongReader();
        /* trying to open file */
        if (!removedSongReader.open(fileName))
        {
            System.out.println("Error opening removed songs file " + fileName);
        }
        else
        {
            /* details read from file which contains song title and emotion */
            RemovedSongs removedSongs;
            /* getting details from removed song file */
            while ((removedSongs = removedSongReader.readRemovedSong()) != null)
            {
                /* storing details (emotion and song titles) */
                songsRemoved.put(removedSongs.getEmotion(),removedSongs.getSongs());
            }
            succeed = true;
        }
        return succeed;
    }

    /**
     * Getter for songs related to emotion
     * @return songs in emotion category
     */
    public ArrayList<Song> getSongsFromEmotion(String emotion)
    {
        ArrayList<Song> result = null;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if(songs!=null)
        {
            /* return ArrayList for ease of use */
            result = new ArrayList<>(songs);
        }
        return result;
    }

    /**
     * remove song from category and add the song to
     * removed song list.
     * @param song      song to remove
     * @param emotion   emotion to remove song from
     * @return true if successful, false if it cannot remove
     */
    public boolean removeFromCategory(Song song,String emotion)
    {
        boolean succeed = false;
        /* get songs from emotion */
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if(songs!=null)
        {
            /* adding song to removed song list */
            /* if other songs(s) have been removed from the emotion in the past*/
            if(songsRemoved.containsKey(emotion))
            {
                ArrayList<String> oldSongsList = songsRemoved.get(emotion);
                oldSongsList.add(song.getTitle());
                songsRemoved.put(emotion,oldSongsList);
            }
            /* first song to be removed from the emotion */
            else
            {
                ArrayList<String> newList =new ArrayList<>();
                newList.add(song.getTitle());
                songsRemoved.put(emotion,newList);
            }
            /* set emotion that will be used to compare score */
            SongComparator.setEmotion(emotion);
            /* remove song from emotion category */
            succeed = songsWithEmotions.get(emotion).remove(song);
        }
        return succeed;
    }

    /**
     * check if a song is removed from emotion category already
     * @param emotion   emotion to check
     * @param song      song to check
     * @return true if already removed, otherwise false.
     */
    private boolean isRemovedSong(String emotion,Song song)
    {
        boolean removed = false;
        /* check if the emotion has songs that are removed */
        if(songsRemoved.containsKey(emotion))
        {
            /* find song in removed song list of emotion category */
            for(String songTitle: songsRemoved.get(emotion))
            {
                if(song.getTitle().compareToIgnoreCase(songTitle)==0)
                {
                    removed = true;
                    break;
                }
            }
        }
        return removed;
    }

    /**
     * Store songs related with emotion in sorted order.
     * @param emotion   current emotion
     * @param words     words of current emotion
     * @param songs     ArrayList of song to store(all songs)
     */
    public void sync(String emotion,ArrayList<String> words,ArrayList<Song> songs)
    {
        /* set emotion that will be used to compare score */
        SongComparator.setEmotion(emotion);
        for(Song song:songs)
        {
            /* song has not been removed from emotion category */
            if(!isRemovedSong(emotion,song))
            {
                /* to calculate emotion score */
                song.countScore(emotion,words);
                if(song.getScore(emotion)>0.4)
                {
                    /* emotion already exist */
                    if(songsWithEmotions.containsKey(emotion))
                    {
                        /* gets sorted song set and add current song to the set */
                        TreeSet<Song> songsByScore = songsWithEmotions.get(emotion);
                        songsByScore.add(song);
                        songsWithEmotions.put(emotion,songsByScore);
                    }
                    /* emotion has been recently added */
                    else
                    {
                        /* create a new song set then add the song */
                        TreeSet<Song> newTreeSet = new TreeSet<>(new SongComparator());
                        newTreeSet.add(song);
                        songsWithEmotions.put(emotion,newTreeSet);
                    }
                }
            }
        }
    }

     /**
     * write songs removed from emotion to text file
     * @return true if successful, false if error occurred
     */
    public boolean writeRemovedSongs()
    {
        boolean succeed = false;
        try
        {
            FileWriter writer = new FileWriter("removed.txt");
            ArrayList<String> emotions = new ArrayList<>(songsRemoved.keySet());
            /* write emotion and removed song */
            for (String emotion:emotions)
            {
                /* get removed song by emotion */
                ArrayList<String> songs = songsRemoved.get(emotion);
                /* write emotion */
                writer.write(emotion.toLowerCase()+" : [\n");
                /* write removed songs */
                for(String song : songs)
                {
                    writer.write(song.toLowerCase()+"\n");
                }
                writer.write("]\n");
            }
            writer.close();
            succeed = true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return succeed;

    }
}