package songemotion; /**
 *  songemotion.RemovedSongs.java
 *
 *  This class represents songemotion.RemovedSongs object which encapsulates
 *  emotion and song titles removed from the emotion.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;

public class RemovedSongs
{
    /* emotions which have removed song */
    private String emotion;
    /* songs removed from emotion */
    private ArrayList<String> songs = null;

    /**
     * Constructor sets the emotion and removed songs.
     * We should probably validate to make sure the emotion is legal
     * and also songs are legal but it's awkward to deal
     * with errors in constructors.
     * @param  emotion   emotion which has removed songs
     * @param  songs     removed song titles of from emotion
     */
    public RemovedSongs(String emotion,ArrayList<String> songs)
    {
        this.emotion = emotion;
        this.songs = songs;
    }

    /**
     * Getter for emotion
     * @return emotion
     */
    public String getEmotion() 
    {
        return emotion;
    }

    /**
     * Getter for removed songs
     * @return removed songs' title
     */
    public ArrayList<String> getSongs() 
    {
        return songs;
    }
}