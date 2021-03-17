package song; /**
 *  song.SongCollection.java
 *
 *  A song.SongCollection is a group of songs which provides
 *  functionality for getting songs
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import interfaces.Data;

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
    public ArrayList<Data> getAllSongs()
    {
        return new ArrayList<>(songs);
    }

    /**
     * Getter for songs by from input keyword which finds
     * the word in song title.
     * @param keyword   a keyword to find songs
     * @return songs    ArrayList of song which contains keyword
     */
    public ArrayList<Data> getSongs(String keyword) {
        ArrayList<Data> result = new ArrayList<>();
        for(Data song:songs)
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

}
