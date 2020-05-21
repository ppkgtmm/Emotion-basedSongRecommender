/**
 *  SongComparator.java
 *
 *  Simple comparator to compare emotion score of
 *  songs
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.util.Comparator;
public class SongComparator implements Comparator<Song>
{
    /** current emotion used to compare the scores */
    private static String currentEmotion = null;

    /**
     * compare songs by their id
     * @param song1 first song in comparision
     * @param song2 second song in comparision
     * @return negative number if song2 id is less than song1,
     * positive number if song2 id is greater that song1,
     * otherwise return 0.
     */
    private int compareById(Song song1,Song song2)
    {
        return song2.getId().compareTo(song1.getId());
    }

    /**
     * Override compare method to compare songs based on
     * emotion score and call other function if the scores
     * are equal.
     * @param song1 first song in comparision
     * @param song2 second song in comparision
     * @return -1 if song2 emotion score is less than song1,
     * 1 if song2 score id is greater that song1,
     * otherwise call function to compare songs by id.
     */
    @Override
    public int compare(Song song1,Song song2) {
        if(song2.getScore(currentEmotion)>song1.getScore(currentEmotion))
            return 1;
        else if(song1.getScore(currentEmotion)>song2.getScore(currentEmotion))
            return -1;
        else
            return compareById(song1,song2);
    }

    /**
     * Sets current emotion
     * @param emotion emotion to set.
     */
    public static void setEmotion(String emotion)
    {
        currentEmotion = emotion;
    }
}
