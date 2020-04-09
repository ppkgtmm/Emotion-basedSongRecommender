import java.util.ArrayList;
import java.util.HashMap;

public class Emotions
{
    private HashMap<String,ArrayList<String>> emotions = new HashMap<String,ArrayList<String>>();
    private Integer id;
    private String emotion;
    private ArrayList<String> words;
    private static Integer counter = 0;

    // public Emotions(String emotion,ArrayList<String> words)
    // {
    //     counter += 1;
    //     id = counter;
    //     this.emotion = emotion;
    //     this.words = words;
    // }

    public Emotions(String emotion,ArrayList<String> words)
    {
        counter += 1;
        id = counter;
        emotions.put(emotion,words);
    }

    // public boolean addEmotion(String emotion,ArrayList<String> words)
    // {
    //     ArrayList<String> checkAdd = emotions.put(emotion,words);
    //     if( checkAdd == null )
    //     {
    //         return false;
    //     }
    //     else
    //         return true;
    // }

    public ArrayList<String> getEmotions()
    {
        ArrayList<String> testArraylist= null;
        return testArraylist;
    }

    public ArrayList<String> getEmotionWords(String emotion)
    {
        if( emotions.containsKey(emotion) )
        {
            return emotions.get(emotion);
        }
        else
            return null;
    }
}