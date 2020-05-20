/**
 *  RemovedSongs.java
 *
 *  This class represents RemovedSongs object which collects
 *  emotion which has removed song and removed songs.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

public class RemovedSongs
{
    /* emotions which have removed song */
    private String emotion;

    /* removed songs of current emotion */
    private ArrayList<String> songs = null;

    /**
     * Constructor sets the emotion and removed songs.
     * We should probably validate to make sure the emotion is legal
     * and also songs are legal but it's akward to deal 
     * with errors in constructors.
     * @param  emotion   emotion which has removed song
     * @param  words     removed song
     */
    public RemovedSongs(String emotion,ArrayList<String> songs)
    {
        this.emotion = emotion;
        this.songs = songs;
    }

    /**
     * Getter for emotion
     * @return tile emotion
     */
    public String getEmotion()
    {
        return emotion;
    }

    /**
     * Getter for removed songs
     * @return removed songs
     */
    public ArrayList<String> getSongs() 
    {
        return songs;
    }
}