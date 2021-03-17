package song; /**
 *  song.Song.java
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

import interfaces.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Song implements Data
{
    /** song ID */
    private final Integer id;

    /** counter to increase song id */
    private static Integer counter = 0;

    /** song title  */
    private final String title;

    /** song lyrics */
    private final ArrayList<String> lyrics;

    /** emotion score based on lyrics */
    private HashMap<String,Double> emotionScore;

    private HashMap<String, Integer> lyricsWordCount;

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
        lyricsWordCount = new HashMap<>();
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
    public ArrayList<String> getDetails()
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

    private int updateAndReturnWordCount(String line){
        String[] splitLine = line.split(" ");
        int totalWordCount = 0;
        for(String word: splitLine){
            word = word.replaceAll("[^a-zA-Z0-9-']", "");
            if(lyricsWordCount.containsKey(word)){
                lyricsWordCount.put(word,lyricsWordCount.get(word)+1);
            }else{
                lyricsWordCount.put(word, 1);
            }
            totalWordCount ++;
        }
        return totalWordCount;
    }

    private void setUpLyricsMap()
    {
        int totalWordCount = 0;
        for(String line:lyrics)
        {
            totalWordCount += updateAndReturnWordCount(line);

        }
        lyricsWordCount.put("  total  ", totalWordCount);
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
        setUpLyricsMap();
        if(lyricsWordCount.get("  total  ") == 0){
            emotionScore.put(emotion,score);
            return;
        }
        for (String word: words){
            if(lyricsWordCount.containsKey(word)){
                score += lyricsWordCount.get(word);
            }
        }
        System.out.println(title + " " + emotion + " " + score / lyricsWordCount.get("  total  "));
        emotionScore.put(emotion,score / lyricsWordCount.get("  total  "));
    }

}