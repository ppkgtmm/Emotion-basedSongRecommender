package songemotion; /**
 *  songemotion.SongEmotions.java
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
import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;
import song.Song;
import song.SongComparator;

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

    /** instance of songemotion.SongEmotions for managing songs related to emotions */
    private static SongEmotions songEmotions = null;

    /** store songs removed from emotion category in the past
     * keys : emotion category
     * values : list of song titles that had been removed
     */
    private HashMap<String, ArrayList<String>> songsRemoved;

    /** reader that knows how to read and parse the removed song file */
    private CustomReader reader;

    private static final String emotionPattern = "Emotion :";
    private static final String songPattern = "Songs :";


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
        reader = new CustomReader();
        /* trying to open file */
        if (!reader.open(fileName))
        {
            System.out.println("Error opening removed songs file " + fileName);
            return false;
        }
        ReaderDTO data;
        while ((data = reader.readData(SongEmotions.emotionPattern,SongEmotions.songPattern)) != null)
        {
            String emotion = data.getTitle();
            ArrayList<String> songs = data.getDetails();

            if(data.isIncompleteData()){
                System.out.println("Skipping invalid data");
                continue;
            }
            songsRemoved.put(emotion,songs);

        }
        return true;
    }

    /**
     * Getter for songs related to emotion
     * @return songs in emotion category
     */
    public ArrayList<Data> getSongsFromEmotion(String emotion)
    {
        ArrayList<Data> result = null;
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

    private boolean foundInSongBin(Song song, ArrayList<String> bin){
        for(String songTitle: bin)
        {
            if(song.getTitle().compareToIgnoreCase(songTitle)==0)
            {
                return true;
            }
        }
        return false;
    }
    /**
     * check if a song is removed from emotion category already
     * @param emotion   emotion to check
     * @param song      song to check
     * @return true if already removed, otherwise false.
     */
    private boolean isRemovedSong(String emotion, Song song)
    {
        if(songsRemoved.containsKey(emotion))
        {
            return foundInSongBin(song,songsRemoved.get(emotion));
        }
        return false;
    }

    private void addSong(Song song, String emotion){
        if(song.getScore(emotion)>0.1)
        {
            if(songsWithEmotions.containsKey(emotion))
            {
                TreeSet<Song> songsByScore = songsWithEmotions.get(emotion);
                songsByScore.add(song);
                songsWithEmotions.put(emotion,songsByScore);
            }
            else
            {
                TreeSet<Song> newTreeSet = new TreeSet<>(new SongComparator());
                newTreeSet.add(song);
                songsWithEmotions.put(emotion,newTreeSet);
            }
        }
    }

    /**
     * Store songs related with emotion in sorted order.
     * @param emotion   current emotion
     * @param words     words of current emotion
     * @param songs     ArrayList of song to store(all songs)
     */
    public void sync(String emotion, ArrayList<String> words, ArrayList<Data> songs)
    {
        /* set emotion that will be used to compare score */
        SongComparator.setEmotion(emotion);
        for(Data song:songs)
        {
            /* song has not been removed from emotion category */
            if(!isRemovedSong(emotion, (Song) song))
            {
                Song castedSong  = (Song) song;
                castedSong.countScore(emotion,words);
                addSong(castedSong, emotion);
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
                writer.write("Emotion : "+ emotion.toLowerCase() + "\n");
                writer.write("Songs :"+ "\n");
                /* write removed songs */
                for(String song : songs)
                {
                    writer.write(song.toLowerCase()+"\n");
                }
                writer.write("==END==\n");
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