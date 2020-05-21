/**
 * Class to read info about emotions from a file
 * and create emotions.
 *
 * Each line of the file has the following structure
 *
 *  First field is emotion - lines that have colon, after emotion
 *  Second field is the words - lines which are in the bracket after emotion
 *
*  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

/**
 * This class encapsulates the weird Java file IO to give emotions and related words
 * a way to read text files line by line
 */
public class EmotionReader extends TextFileReader
{
    /** currentEmotion associated with current emotion of words*/
    private String currentEmotion = null;
    /** currentWords arraylist associated with words related emotion*/
    private ArrayList<String> currentWords = null;

    /**
     * find emotion in text file and set emotion to currentEmotion
     * @param  line   current line
     * @return true if successful, false if cannot find emotion
     */
    private boolean findAndSetEmotion(String line)
    {
        boolean found = false;
        /* check is it have emotion */
        if(line.charAt(line.length()-1) == '[')
        {
            int colonIndex = line.indexOf(":");     /* find out the colon (emotion symbol)*/
            if(colonIndex!=-1)
            {
                /* get the emotion */
                currentEmotion = line.substring(0,colonIndex).trim().toLowerCase();
                found = true;
            }
        }
        return found;
    }

    /**
     * check is it the last word of related emotion in text file and
     * @param  line   current line
     * @return true if successful, false if the line is not the last word of emotion
     *
     */
    private boolean isLastWordOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");                     /* find out the bracket of the last line (ending word symbol)*/
        /* check is it the last word of related emotion*/
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            String lastWord = line.substring(0,braceIndex).trim();  /* divide to get the last word */
            /* check is it empty */
            if(!lastWord.isEmpty())
            {
                currentWords.add(lastWord);
            }
            isLast = true;
        }
        return isLast;
    }

    /**
    * read a emotion text file, if possible. It will be read and
    * initit emotions and words of each related emotion.
    * @return Emotion emotion set
    */
    public Emotion readEmotions()
    {
        Emotion newEmotion = null;
        String line = null;
        /* loop until the end of the text file */
        do {
            line = getNextLine();
            if (line!=null)
            {
                /* eliminates leading and trailing spaces of a line */
                line = line.trim();
                /* cheak is it reads completely  */
                if (!isEmptyLine(line))
                {
                    boolean foundEmotion = findAndSetEmotion(line); /* find and set current emotion */
                    if (foundEmotion)
                    {
                        currentWords = new ArrayList<>();           /* arraylist to collect word of emotion */
                        /* collect words of emotion */
                        while ((line = getNextLine()) != null)
                        {
                            line = line.trim();
                            if (!isEmptyLine(line))
                            {
                                boolean lastWordMatch = isLastWordOfEmotion(line);  /* check is it the last words */
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
                }
            }
        }while( line!=null && newEmotion == null);
        return newEmotion;
    }
}