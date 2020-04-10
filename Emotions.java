import java.util.ArrayList;
import java.util.HashMap;

public class Emotions
{
    private HashMap<String,ArrayList<String>> emotions;

    public Emotions()
    {
        emotions = new HashMap<>();
    }

    public ArrayList<String> getEmotions()
    {
        return new ArrayList<>(emotions.keySet());
    }

     public void addEmotion(String emotion,ArrayList<String> words)
     {
         emotions.put(emotion,words);
     }


    public ArrayList<String> getEmotionWords(String emotion)
    {
        return emotions.getOrDefault(emotion, null);
    }
}