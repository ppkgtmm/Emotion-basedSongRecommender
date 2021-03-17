package emotion; /**
 *  emotion.Emotion.java
 *
 *  Represents an emotion which words related
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import interfaces.Data;

import java.util.ArrayList;

public class Emotion implements Data
{
    /** emotion.Emotion category */
    private final String emotion;
    /** Words associated with emotion category */
    private final ArrayList<String> words;

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
    public String getTitle()
    {
        return emotion;
    }

    /**
     * Getter for words
     * @return emotion related words
     */
    public ArrayList<String> getDetails()
    {
        return words;
    }

}
