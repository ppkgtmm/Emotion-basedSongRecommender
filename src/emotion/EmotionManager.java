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

    private static EmotionManager emotionManager = null;

    private final CustomReader reader = new CustomReader();

    private HashMap<Integer, Emotion> emotionMap;
    private static final String emotionPattern = "Emotion :";
    private static final String wordsPattern = "Words :";


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
            if (Utils.isValidData(emotionData,"Skipping invalid emotion")) {
                addEmotion(new Emotion(emotionData.getTitle(), emotionData.getDetails()));
            }
        }
        reader.close();
        return emotionMap.size() > 0;
    }


    public ArrayList<Data> getEmotions() {
        return new ArrayList<>(emotionMap.values());
    }


    public boolean addEmotion(Emotion emotion) {
        if (!emotionMap.containsKey(emotion.getId())) {
            emotionMap.put(emotion.getId(), emotion);
        }
        return true;
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
            e.printStackTrace();
        }
        return succeed;
    }


}
