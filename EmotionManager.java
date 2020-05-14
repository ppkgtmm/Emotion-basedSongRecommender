import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmotionManager
{
    private static EmotionManager emotionManager= null;
    private EmotionReader reader;
    private HashMap<String,ArrayList<String>> emotions;

    private EmotionManager()
    {
        emotions = new HashMap<>();
    }

    public static EmotionManager getInstance(){
        if(emotionManager==null)
        {
            emotionManager = new EmotionManager();
        }
        return emotionManager;
    }

     public boolean readEmotions(String fileName)
     {
         boolean result = false;
         reader = new EmotionReader();
         if (!reader.open(fileName))
         {
             System.out.println("Error opening emotion file " + fileName);
         }
         Emotion nextEmotion;
         while ((nextEmotion = reader.readEmotions()) != null)
         {
            // System.out.println("Successfully read " + nextEmotion.getEmotion());
//             for (String word:nextEmotion.getWords())
//             {
//                //System.out.println(word);
//             }
             addEmotion(nextEmotion.getEmotion(),nextEmotion.getWords());
             result = true;
         }
         return  result;
     }

    public ArrayList<String> getEmotions()
    {
        return new ArrayList<>(emotions.keySet());
    }

    public boolean addEmotion(String emotion,ArrayList<String> words)
    {
        boolean bOk = false;
        if(emotions.containsKey(emotion))
        {
            ArrayList<String> currentWords = emotions.get(emotion);
            words.addAll(currentWords);
        }
        if(emotions.put(emotion,words) == null)
                bOk = true;
        return bOk;
    }

    public ArrayList<String> getEmotionWords(String emotion)
    {
        return emotions.getOrDefault(emotion, null);
    }

    public boolean writeEmotions()
    {
        boolean succeed = false;
        try
        {
            FileWriter writer = new FileWriter("emotions.txt");
            ArrayList<String> allEmotions = new ArrayList<>(emotions.keySet());
            for (String emotion: allEmotions)
            {
                ArrayList<String> words = emotions.get(emotion);
                writer.write(emotion.toLowerCase()+" : [\n");
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
