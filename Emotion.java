import java.util.ArrayList;

public class Emotion
{
    private String emotion = null;
    private ArrayList<String> words = null;

    public Emotion(String emotion,ArrayList<String> words)
    {
        this.emotion = emotion;
        this.words = words;
    }

    public String getEmotion() {
        return emotion;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}
