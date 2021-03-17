package song;
/**
 *  song.SongManager.java
 *
 *  This class represents organization to manage songs.
 *  It provides functionality to get songs in many ways.
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

import java.util.ArrayList;

public class SongManager
{
    /** collection of song managed by manager */
    private SongCollection songs;

    /** instance of song.SongManager for managing songs */
    private static SongManager songManager= null;

    /**  reader that knows how to read and parse the song file*/
    private CustomReader reader;

    private static final String titlePattern = "Song :";
    private static final String lyricsPattern = "Lyrics :";

    /**
     * Constructor which instantiates song collection
     */
    private SongManager()
    {
        songs = new SongCollection();
    }

    /**
     * Getter for songManager instance which is created
     * only once.
     * @return  songManager instance
     */
    public static SongManager getInstance()
    {
        if(SongManager.songManager == null)
        {
            songManager = new SongManager();
        }
        return SongManager.songManager;
    }

    /**
     * Getter for all songs
     * @return all songs managed by the manager
     */
    public ArrayList<Data> getAllSongs()
    {
        return songs.getAllSongs();
    }

    /**
     * Getter for songs from keyword
     * @param keyword keyword to find for
     * @return song(s) which title contains keyword
     */
    public ArrayList<Data> getSongs(String keyword)
    {
        return songs.getSongs(keyword);
    }

    /**
     * Open a song file and calling reader to read
     * and add songs read to song collection.
     * @param fileName  a song file name
     * @return true if successful, false if it cannot read songs.
     */
    public boolean readSongs(String fileName)
    {
        reader = new CustomReader();
        /* trying to open song file */
        if (!reader.open(fileName))
        {
            System.out.println("Error opening song file "+fileName);
            return false;
        }
        ReaderDTO data = null;
        /* reading songs from song file */
        while ((data = reader.readData(SongManager.titlePattern, SongManager.lyricsPattern)) != null)
        {
            String title = data.getTitle();
            ArrayList<String> lyrics = data.getDetails();
            if(data.isIncompleteData()){
                System.out.println("Skipping invalid song");
                continue;
            }
            songs.addSong(new Song(title, lyrics));
        }
        return songs.getAllSongs().size() > 0;
    }


}