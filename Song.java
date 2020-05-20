/**
 *  Song.java
 *
 *  This class represents songs object which collects
 *  all song detail with title, lyrics, id and emotion scorce.
 *  It will calculate emotion scorce from words in lyric and
 *  gets the most top 3 emotion score.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
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

    /** emotion score in order */
    private HashMap<String,Double> emotionScore;

    /**
     * Constructor sets the title and lyrics.
     * We should probably validate to make sure the title is legal and lyrics
     * but it's akward to deal with errors in constructors.
     * @param  title    song title
     * @param  lyrics   song lyrics
     */
    public Song(String title, ArrayList<String> lyrics)
    {
        counter += 1;   /* increases counter by 1 */
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
     * Count a word from related emotion in lyrics
     * find line by line in lyrics
     * @param   word    word to count score
     * @return  count   emotion score
     */
    private Integer analyzeLyrics(String word)
    {
        Integer count = 0;
        Integer fromIndex = 0;
        /** find lines which have the word*/
        for(String line : lyrics)
        {
            /** find the word in a line*/
            while ((fromIndex = line.indexOf(word, fromIndex)) != -1 ){
                count++;
                fromIndex++;
            }
        }
        return count;
    }

    /**
     * Getter for song score
     * @return tile song score
     */
    public Double getScore(String emotion)
    {
        /** check the key emotion */
        if(emotionScore.containsKey(emotion))
        {
            return emotionScore.get(emotion);
        }
        return -1.0;
    }

    /**
     * Count words in each lyrics
     * @return count    amount of word of lyrics
     */
    public Integer countWordsInLyrics()
    {
        Integer count = 0;
        for(String line:lyrics)
        {
            /** count word in each line */
            count += line.split("//s+").length;
        }
        return count;
    }

    /**
     * Calculate emotion score from lyric by count words which related
     * to each emotion and collect the emotion score in order
     * @param emotion   current emotion
     * @param words     words of related current emotion
     */
    public void countScore(String emotion,ArrayList<String> words)
    {
        Double score = 0.0;         /** initial the score */
        /** Count words of emotion */
        for(String word:words)
        {
            score += analyzeLyrics(word);
        }
        Integer wordCount = countWordsInLyrics();
        /** Calculate the emotion score*/
        if(wordCount>0)
        {
            score = score/wordCount;
        }
        /** collect the emotion score in order */
        emotionScore.put(emotion,score);
    }

}
