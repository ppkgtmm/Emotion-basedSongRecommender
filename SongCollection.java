/**
 *  SongCollection.java
 *
 *  A SongCollection is a group of songs.
 *  SongCollection encapsulates access to groups of songs.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

public class SongCollection
{
    /**
     * Collection of songs
     */
    private ArrayList<Song> songs;

    /**
     * Constructor sets song arraylist.
     */
    public SongCollection()
    {
        songs = new ArrayList<>();
    }

    /**
     * Getter for all songs from collection
     * @return all songs
     */
    public ArrayList<Song> getAllSongs() 
    {
        return songs;
    }

    /**
     * Getter for songs by inputted keyword which finds
     * the word in song lyrics.
     * @param keyword   a keyword to find songs
     * @return songs    arraylist of song which contains keyword
     */
    public ArrayList<Song> getSongs(String keyword) 
    {
        ArrayList<Song> result = new ArrayList<>();
        for(Song song:songs)
        {
            /** find songs which contains the keyword */
            if(song.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Getter for amount of song
     * @return amount of song
     */
    public int getAmountSongs()
    {
        return songs.size();
    }

    /**
     * Add song to song collection
     * @param song
     */
    public void addSong(Song song)
    {
        songs.add(song);
    }

    /**
     * Getter for amount of song by song id
     * @return a song
     */
    public Song getSongByID(Integer id)
    {
        for(Song song:songs)
        {
            if(song.getId().equals(id))
            {
                return song;
            }
        }
        return null;
    }
}
