/**
 * EmotionReader.java
 * Class to read information about emotions from a file
 * and create emotion objects for further use.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.util.ArrayList;


public class EmotionReader extends TextFileReader
{
    /** currentEmotion associated with current words read from file */
    private String currentEmotion = null;
    /** words related with current emotion read from file */
    private ArrayList<String> currentWords = null;


    /**
     * find emotion in text file and set the emotion to currentEmotion
     * @param  line   current line
     * @return true if successful, false if cannot find emotion
     */
    private boolean findAndSetEmotion(String line)
    {
        boolean found = false;
        if(line.charAt(line.length()-1) == '[')
        {
            int colonIndex = line.indexOf(":");
            if(colonIndex!=-1)
            {
                currentEmotion = line.substring(0,colonIndex).trim().toLowerCase();
                found = true;
            }
        }
        return found;
    }

    /**
     * check if it is the last word related to current emotion
     * in text file and add the word to collection
     * @param  line   current line
     * @return true if successful, false if the line is not the
     * last word of current emotion.
     *
     */
    private boolean isLastWordOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        /* check if it is last word */
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            /* getting last word */
            String lastWord = line.substring(0,braceIndex).trim();
            if(!lastWord.isEmpty())
            {
                currentWords.add(lastWord);
            }
            isLast = true;
        }
        return isLast;
    }

    /**
     * read a emotion text file, if possible. It will read and
     * create emotion object from emotion and words related to the
     * emotion.
     * @return emotion object if reading about an emotion complete
     * or null if end of file reached.
     */
    public Emotion readEmotions()
    {
        Emotion newEmotion = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                /* eliminates leading and trailing spaces of a line */
                line = line.trim();
                /* check for empty line */
                if (!isEmptyLine(line))
                {
                    /* find and set current emotion */
                    boolean foundEmotion = findAndSetEmotion(line);
                    if (foundEmotion)
                    {
                        /* instantiate to collect word of emotion separately*/
                        currentWords = new ArrayList<>();
                        /* collect words of emotion */
                        while ((line = getNextLine()) != null)
                        {
                            line = line.trim();
                            if (!isEmptyLine(line))
                            {
                                /* check is it the last word */
                                boolean lastWordMatch = isLastWordOfEmotion(line);
                                if (lastWordMatch)
                                {
                                    newEmotion = new Emotion(currentEmotion,currentWords);
                                    break;
                                }
                                else
                                {
                                    currentWords.add(line);
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Bad line "+line+" ==> skipping");
                    }
                }
            }
        }while( line!=null && newEmotion == null);
        return newEmotion;
    }
}