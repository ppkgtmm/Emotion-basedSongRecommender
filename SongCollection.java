/**
 *  SongCollection.java
 *
 *  A SongCollection is a group of songs which provides
 *  functionality for getting songs
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.util.ArrayList;

public class SongCollection
{

    /**
     * Collection of songs
     */
    private ArrayList<Song> songs;

    /**
     * Constructor instantiate array list of songs.
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
     * Getter for songs by from input keyword which finds
     * the word in song title.
     * @param keyword   a keyword to find songs
     * @return songs    ArrayList of song which contains keyword
     */
    public ArrayList<Song> getSongs(String keyword) {
        ArrayList<Song> result = new ArrayList<>();
        for(Song song:songs)
        {
            /* find keyword in title */
            if(song.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Add song to song collection
     * @param song song to add
     */
    public void addSong(Song song)
    {
        songs.add(song);
    }

    /**
     * Getter for song by song id
     * @return song with specified id or null if
     * not found
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
