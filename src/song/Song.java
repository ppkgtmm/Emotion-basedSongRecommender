package song;


import interfaces.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Song implements Data {

    private final Integer id;


    private static Integer counter = 0;


    private final String title;


    private final ArrayList<String> lyrics;


    private HashMap<String, Double> emotionScore;

    private HashMap<String, Integer> lyricsWordCount;


    public Song(String title, ArrayList<String> lyrics) {
        counter += 1;
        id = counter;
        this.title = title;
        this.lyrics = lyrics;
        emotionScore = new HashMap<>();
        lyricsWordCount = new HashMap<>();
    }


    public String getTitle() {
        return title;
    }


    public ArrayList<String> getDetails() {
        return lyrics;
    }


    public Integer getId() {
        return id;
    }


    public Double getScore(String emotion) {

        if (emotionScore.containsKey(emotion)) {
            return emotionScore.get(emotion);
        }
        return -1.0;
    }

    private int updateAndReturnWordCount(String line) {
        String[] splitLine = line.split(" ");
        int totalWordCount = 0;
        for (String word : splitLine) {
            word = word.replaceAll("[^a-zA-Z0-9-']", "");
            if (lyricsWordCount.containsKey(word)) {
                lyricsWordCount.put(word, lyricsWordCount.get(word) + 1);
            } else {
                lyricsWordCount.put(word, 1);
            }
            totalWordCount++;
        }
        return totalWordCount;
    }

    private void setUpLyricsMap() {
        int totalWordCount = 0;
        for (String line : lyrics) {
            totalWordCount += updateAndReturnWordCount(line);

        }
        lyricsWordCount.put("  total  ", totalWordCount);
    }


    public void countScore(String emotion, ArrayList<String> words) {
        Double score = 0.0;
        setUpLyricsMap();
        if (lyricsWordCount.get("  total  ") == 0) {
            emotionScore.put(emotion, score);
            return;
        }
        for (String word : words) {
            if (lyricsWordCount.containsKey(word)) {
                score += lyricsWordCount.get(word);
            }
        }
        emotionScore.put(emotion, score / lyricsWordCount.get("  total  "));
    }

}