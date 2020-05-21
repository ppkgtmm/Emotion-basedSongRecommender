/**
 *  Facilitator.java
 *
 *  A Emotion represents a emotion which collects words
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 *
 */
import java.util.ArrayList;
public class Emotion
{
    /** Emotion associated with emotion category */
    private String emotion = null;
    /** Words associated with related words to each category */
    private ArrayList<String> words = null;

    /**
     * Constructor sets the emotion and words.
     * We should probably validate to make sure the emotion is legal
     * but it's akward to deal with errors in constructors.
     * @param  emotion   emotion category
     * @param  words     emotion words
     */
    public Emotion(String emotion,ArrayList<String> words)
    {
        this.emotion = emotion;
        this.words = words;
    }

    /**
     * Getter for emotion
     * @return tile emotion
     */
    public String getEmotion() {
        return emotion;
    }

    /**
     * Getter for words
     * @return tile words
     */
    public ArrayList<String> getWords() {
        return words;
    }
}
