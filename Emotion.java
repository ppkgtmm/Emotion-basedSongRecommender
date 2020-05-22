/**
 *  Emotion.java
 *
 *  Represents an emotion which words related
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;

public class Emotion
{
    /** Emotion category */
    private String emotion = null;
    /** Words associated with emotion category */
    private ArrayList<String> words = null;

    /**
     * Constructor sets the emotion and words.
     * We should probably validate to make sure the emotion is legal
     * but it's akward to deal with errors in constructors.
     * @param  emotion   emotion category
     * @param  words     emotion related words
     */
    public Emotion(String emotion,ArrayList<String> words)
    {
        this.emotion = emotion;
        this.words = words;
    }

    /**
     * Getter for emotion
     * @return emotion category
     */
    public String getEmotion()
    {
        return emotion;
    }

    /**
     * Getter for words
     * @return emotion related words
     */
    public ArrayList<String> getWords()
    {
        return words;
    }

    /**
     * Setter for emotion
     * @param emotion emotion to set
     */
    public void setEmotion(String emotion)
    {
        this.emotion = emotion;
    }

    /**
     * Setter for words related to emotion
     * @param words words to set
     */
    public void setWords(ArrayList<String> words)
    {
        this.words = words;
    }
}
