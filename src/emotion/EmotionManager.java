package emotion;

import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;
import utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmotionManager {

    private static final String emotionPattern = "Emotion :";
    private static final String wordsPattern = "Words :";
    private static EmotionManager emotionManager = null;
    private final CustomReader reader = new CustomReader();
    private HashMap<String, Emotion> emotionMap;


    private EmotionManager() {
        emotionMap = new HashMap<>();
    }


    public static EmotionManager getInstance() {
        if (emotionManager == null) {
            emotionManager = new EmotionManager();
        }
        return emotionManager;
    }


    public boolean readEmotions(String fileName) {
        if (!reader.open(fileName)) {
            System.out.println("Error opening emotion file " + fileName);
            return false;
        }
        ReaderDTO emotionData;
        while ((emotionData = reader.readData(EmotionManager.emotionPattern, EmotionManager.wordsPattern)) != null) {
            addEmotion(new Emotion(emotionData.getTitle(), emotionData.getDetails()));
        }
        reader.close();
        return emotionMap.size() > 0;
    }


    public ArrayList<Data> getEmotions() {
        return new ArrayList<>(emotionMap.values());
    }


    private boolean isNewEmotion(Emotion emotion) {
        if (!emotionMap.containsKey(emotion.getTitle())) return true;
        System.out.println("Duplicate emotion encountered");
        return false;
    }

    public boolean addEmotion(Emotion emotion) {
        if (Utils.isValidData(emotion, "Skipping invalid emotion") && isNewEmotion(emotion)) {
            emotionMap.put(emotion.getTitle(), emotion);
            return true;
        }
        return false;
    }


    public boolean writeEmotions() {
        boolean succeed = false;
        try {
            FileWriter writer = new FileWriter("emotions.txt");
            for (Emotion emotion : emotionMap.values()) {
                ArrayList<String> words = emotion.getDetails();
                writer.write("Emotion : " + emotion.getTitle().toLowerCase() + "\n");
                writer.write("Words :\n");
                for (String word : words) {
                    writer.write(word.toLowerCase() + "\n");
                }
                writer.write("==END==\n");
            }
            writer.close();
            succeed = true;
        } catch (IOException e) {
            System.out.println("Unable to write emotions to text file");
        }
        return succeed;
    }


}
