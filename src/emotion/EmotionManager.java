package emotion; /**
 *  emotion.EmotionManager.java
 *
 *  Manages emotions and provide information related
 *  to emotions.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmotionManager
{
    /** instance of  emotion.EmotionManager for managing emotion */
    private static EmotionManager emotionManager= null;
    /** reader that knows how to read and parse the emotion file  */
    private CustomReader reader;
    /** collection of all emotions */
    private ArrayList<Emotion> allEmotions;
    private HashMap<String, Emotion> emotionMap;
    private static final String emotionPattern = "Emotion :";
    private static final String wordsPattern = "Words :";
    /**
     * Constructor class which creates ArrayList to store emotion information
     */
    private EmotionManager()
    {
        allEmotions = new ArrayList<>();
        emotionMap = new HashMap<>();
    }

    /**
     * Getter for emotion manager instance which is created
     * only once
     * @return emotion manager instance
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
     * read emotion file
     * @param  fileName  emotion file name
     * @return true if successful, false if error.
     *         Error could involve failure while opening file
     */
     public boolean readEmotions(String fileName)
     {
         reader = new CustomReader();
         /* trying to open input file */
         if (!reader.open(fileName))
         {
             System.out.println("Error opening emotion file " + fileName);
             return false;
         }
         ReaderDTO data;
         while ((data = reader.readData(EmotionManager.emotionPattern,EmotionManager.wordsPattern)) != null)
         {
             String emotion = data.getTitle();
             ArrayList<String> words = data.getDetails();
             if(data.isIncompleteData()){
                 System.out.println("Skipping invalid emotion");
                 continue;
             }
             addEmotion(new Emotion(emotion, words));
         }
         return  this.allEmotions.size() > 0;
     }

    /**
     * Getter for emotions stored
     * @return all emotions stored
     */
    public ArrayList<Data> getEmotions()
    {
        return new ArrayList<>(allEmotions);
    }

    /**
     * add emotion if it does not exist in the system yet
     * @param  emotion  emotion to add
     * @return true if successful, false if emotion already
     * exist in the system.
     */
    public boolean addEmotion(Emotion emotion)
    {
        if(!emotionMap.containsKey(emotion.getTitle())){
            allEmotions.add(emotion);
            emotionMap.put(emotion.getTitle(), emotion);
        }
        return true;
    }

    /**
     * Write a text file using file writer
     * @return true if successfully written, false if failed to write.
     */
    public boolean writeEmotions()
    {
        boolean succeed = false;
        try
        {
            FileWriter writer = new FileWriter("emotions.txt");
            for (Emotion emotion: allEmotions)
            {
                ArrayList<String> words = emotion.getDetails();
                writer.write("Emotion : " + emotion.getTitle().toLowerCase() + "\n");
                writer.write("Words :\n");
                for(String word : words)
                {
                    writer.write(word.toLowerCase()+"\n");
                }
                writer.write("==END==\n");
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
