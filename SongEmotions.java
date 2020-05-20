/**
 *  SongEmotion.java
 *
 *  This class represents organization to manage songs with emotions
 *  which has collection of songs with emotions, removed songs from
 *  category. Moreover, they are managed by this.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions
{
    /**
     * songs with emotion which are sorted
     */
    private HashMap<String,TreeSet<Song>> songsWithEmotions;

    /**
     *  constructure of songEmotions
     */
    private static SongEmotions songEmotions = null;

    /**
     * removed song from category sorted
     */
    private HashMap<String, ArrayList<String>> songsRemoved;

    /**
     * current removed song
     */
    private RemovedSongReader removedSongReader = null;

    /**
     * Constructor sets the songs with emotion and removed song from category.
     */
    private SongEmotions()
    {
        songsWithEmotions = new HashMap<>();
        songsRemoved = new HashMap<>();
    }

    /**
     * Getter for songEmotions instance
     * @return  songEmotions
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
     * set songsWithEmotion by reading and initial it from text file
     * @param   fileName  removed text file
     * @return true if successful, false if it cannot initialize
     */
    public boolean initialize(String fileName)
    {
        boolean succeed = false;
        removedSongReader = new RemovedSongReader();        /* init removedSongReader */
        if (!removedSongReader.open(fileName))
        {
            System.out.println("Error opening removed songs file " + fileName);
        }
        else
        {
            RemovedSongs removedSongs;
            /** get removed song from category from text file */
            while ((removedSongs = removedSongReader.readRemovedSong()) != null)
            {
                /** put removed song from category to removed song collection */
                songsRemoved.put(removedSongs.getEmotion(),removedSongs.getSongs());
            }
            succeed = true;
        }
        return succeed;
    }

    /**
     * Getter for songs from emotion
     * @return songs in emotion category
     */
    public ArrayList<Song> getSongsFromEmotion(String emotion)
    {
        ArrayList<Song> result = null;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);   /* get songs from emotion */
        if(songs!=null)
        {
            result = new ArrayList<>(songs);
        }
        return result;
    }

    /**
     * remove song from category by adding the song to
     * removedSongs.
     * @param song      removed song
     * @param emotion   current emotion
     * @return true if successful, false if it cannot remove
     */
    public boolean removeFromCategory(Song song,String emotion)
    {
        boolean succeed = false;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);       /* get songs from emotion */
        if(songs!=null)
        {
            //System.out.println(songs.contains(song));
            /* add removed song from category */
            if(songsRemoved.containsKey(emotion))
            {
                /** add removed song from initialize emotion */
                ArrayList<String> oldSongsList = songsRemoved.get(emotion);
                oldSongsList.add(song.getTitle());
                songsRemoved.put(emotion,oldSongsList);
            }
            else
            {
                /** add removed song from new initialize emotion */
                ArrayList<String> newList =new ArrayList<>();
                newList.add(song.getTitle());
                songsRemoved.put(emotion,newList);
            }
            /** analyze song with emotion sorted by emotion score */
            SongComparator.setEmotion(emotion);
            /** remove song from song with emotions */
            succeed = songsWithEmotions.get(emotion).remove(song);
        }
        return succeed;
    }

    /**
     * check a song is it removed from emotion in songsRemoved collection
     * @param emotion   current emotion
     * @param song      removed song
     * @return true if finding, false if it cannot find removed song
     */
    private boolean isRemovedSong(String emotion,Song song)
    {
        boolean removed = false;
        /** check is it contain the emotion */
        if(songsRemoved.containsKey(emotion))
        {
            /** find removed song from current emotion */
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
     * updating song with emotion by calculating emotion score
     * @param emotion   current emotions
     * @param words     words of current emotions
     * @param songs     arraylist of song to update
     * @return true if finding, false if it cannot find removed song
     */
    public void sync(String emotion,ArrayList<String> words,ArrayList<Song> songs)
    {
        /** analyze song with emotion sorted by emotion score */
        SongComparator.setEmotion(emotion);
//        System.out.println(emotion);
        /** analyze song to calculate score and sort by emotion score */
        for(Song song:songs)
        {
            if(!isRemovedSong(emotion,song))
            {
                song.countScore(emotion,words);
                /** set emotion score of each song and add song to each emotion category*/
                if(song.getScore(emotion)>0)
                {
                    if(songsWithEmotions.containsKey(emotion))
                    {
                        TreeSet<Song> songsByScore = songsWithEmotions.get(emotion);    /* get song by emotion */
                        /** add song to  arraylist*/
                        songsByScore.add(song);
                        /** add song with emotion to songsWithEmotions */
                        songsWithEmotions.put(emotion,songsByScore);
                    }
                    else
                    {
                        /** create new song set to collect */
                        TreeSet<Song> newTreeSet = new TreeSet<>(new SongComparator());
                        newTreeSet.add(song);
                        songsWithEmotions.put(emotion,newTreeSet);
                    }
                }
            }
        }
    }

    /**
     * write removed song from emotion
     * @return true if succeful, false if it cannot writing text file
     */
    public boolean writeRemovedSongs()
    {
        boolean succeed = false;
        try
        {
            FileWriter writer = new FileWriter("removed.txt");
            ArrayList<String> emotions = new ArrayList<>(songsRemoved.keySet());
            /** write emotion and removed song */
            for (String emotion:emotions)
            {
                /** get removed song by emotion */
                ArrayList<String> songs = songsRemoved.get(emotion);
                /** write emotion */
                writer.write(emotion.toLowerCase()+" : [\n");
                /** write removed songs */
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

    public static void main(String[] args) {
        SongEmotions songEmotions = SongEmotions.getInstance();
        boolean bOk = songEmotions.initialize("removed.txt");
        // if(bOk)
        // {
        //     songEmotions.writeRemovedSongs();
        // }
    }
}