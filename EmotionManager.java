/**
 *  EmotionManager.java
 *
 *  This class represents the pool of unselected tiles in the
 *  game of Scrabble.
 *
 *  All methods are static because this is a singleton class.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmotionManager
{
    /**
     * instance of a EmotionManager for getting, reading, adding and writing emotion
     */
    private static EmotionManager emotionManager= null;

    /** reader that knows how to parse the emotion file  */
    private EmotionReader reader;

    /** emotion hash map which key emotion and words  */
    private HashMap<String,ArrayList<String>> emotions;

    /**
     * Constructor class to create emotion hash map
     */
    private EmotionManager()
    {
        emotions = new HashMap<>();
    }

    /**
     * Get emotion manager instance
     */
    public static EmotionManager getInstance()
    {
        if(emotionManager==null)
        {
            emotionManager = new EmotionManager();
        }
        return emotionManager;
    }

    /**
     * read emotion file class
     * @param  fileName  emotion file name
     * @return true if successful, false if error
     *         Error could involve there are not open file or
     *         add emotion
     */
     public boolean readEmotions(String fileName)
     {
         boolean result = false;
         reader = new EmotionReader();  /* Reader object to access the file */
         /* Check opening file */
         if (!reader.open(fileName))
         {
             System.out.println("Error opening emotion file " + fileName);
         }
         Emotion nextEmotion;
         /* loop get emotion and word */
         while ((nextEmotion = reader.readEmotions()) != null)
         {
             addEmotion(nextEmotion.getEmotion(),nextEmotion.getWords());
             result = true;
         }
         return  result;
     }

     /**
     * Getter for emotion
     * @return tile emotion
     */
    public ArrayList<String> getEmotions()
    {
        return new ArrayList<>(emotions.keySet());
    }

    /**
     * add emotion and words
     * @param  emotion  emotion key
     * @param  words    words related to emotion
     * @return true if successful, false if error
     *         Error could involve there are put or add
     *
     */
    public boolean addEmotion(String emotion,ArrayList<String> words)
    {
        boolean bOk = false;
        /* check emotion is it alraedy has */
        if(emotions.containsKey(emotion))
        {
            ArrayList<String> currentWords = emotions.get(emotion);
            /* add all word to words related to emotion */
            words.addAll(currentWords);
        }
        /*  check is it already put emotion to collection */
        if(emotions.put(emotion,words) == null)
                bOk = true;
        return bOk;
    }

    /**
     * Getter for words of each related emotion
     * @return tile emotion
     */
    public ArrayList<String> getEmotionWords(String emotion)
    {
        return emotions.getOrDefault(emotion, null);
    }

    /**
    * Write a text file, if possible. It will be closed
    * when finishs writing file.
    * @return true if successfully written, false if not writetn.
    */
    public boolean writeEmotions()
    {
        boolean succeed = false;
        try
        {
            FileWriter writer = new FileWriter("emotions.txt");                 /* emotion writter file */
            ArrayList<String> allEmotions = new ArrayList<>(emotions.keySet()); /* emotion key set */
            /* loop for write emotion and related words */
            for (String emotion: allEmotions)
            {
                ArrayList<String> words = emotions.get(emotion);                /* related words */
                writer.write(emotion.toLowerCase()+" : [\n");
                /* loop for write words related emotion */
                for(String word : words)
                {
                    writer.write(word.toLowerCase()+"\n");
                }
                writer.write("]\n");
            }
            writer.close();
            succeed = true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return succeed;
    }


}
