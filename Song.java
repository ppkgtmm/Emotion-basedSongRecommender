/**
 *  Song.java
 *
 *  This class represents songs object which collects
 *  all song detail with title, lyrics, id and emotion score.
 *  It will calculate emotion scorce from words in lyric and
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Song
{
    /** song ID */
    private Integer id;

    /** counter to increase song id */
    private static Integer counter = 0;

    /** song title  */
    private String title;

    /** song lyrics */
    private ArrayList<String> lyrics;

    /** emotion score based on lyrics */
    private HashMap<String,Double> emotionScore;

    /**
     * Constructor sets the id, title, lyrics and instantiate HashMap
     * of emotion score
     * @param  title    song title
     * @param  lyrics   song lyrics
     */
    public Song(String title, ArrayList<String> lyrics)
    {
        counter += 1;
        id = counter;
        this.title = title;
        this.lyrics = lyrics;
        emotionScore = new HashMap<>();
    }

    /**
     * Getter for song title
     * @return song tile
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Getter for song lyrics
     * @return song lyrics
     */
    public ArrayList<String> getLyrics()
    {
        return lyrics;
    }

    /**
     * Getter for song ID
     * @return tile song ID
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Count occurrence word related to emotion in lyrics
     * @param   word    word to count
     * @return  occurrence of word
     */
    private Integer analyzeLyrics(String word)
    {
        Integer count = 0;
        /* used to keep track of where we are in lyrics */
        Integer fromIndex = 0;
        for(String line : lyrics)
        {
            while ((fromIndex = line.indexOf(word, fromIndex)) != -1 ){
                count++;
                fromIndex++;
            }
        }
        return count;
    }

    /**
     * Getter for song's emotion score
     * @return song's score or -1 if score not available
     */
    public Double getScore(String emotion)
    {
        /* emotion score exist */
        if(emotionScore.containsKey(emotion))
        {
            return emotionScore.get(emotion);
        }
        return -1.0;
    }

    /**
     * Count words in lyrics
     * @return count of amount of words of lyrics
     */
    private Integer countWordsInLyrics()
    {
        Integer count = 0;
        for(String line:lyrics)
        {
            /* find number of words in line using space */
            count += line.split("//s+").length;
        }
        return count;
    }

    /**
     * Calculate emotion score from lyrics by counting number of words
     * which are related with emotion and divide by word count in lyrics.
     * @param emotion   emotion
     * @param words     words related to emotion
     */
    public void countScore(String emotion,ArrayList<String> words)
    {
        Double score = 0.0;
        /* Count emotion score */
        for(String word:words)
        {
            score += analyzeLyrics(word);
        }
        Integer wordCount = countWordsInLyrics();
        if(wordCount>0)
        {
            score = score/wordCount;
        }
        emotionScore.put(emotion,score);
    }

}
