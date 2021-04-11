package emotion;

import interfaces.Data;

import java.util.ArrayList;

public class Emotion implements Data {
    private static int counter = 0;
    private final int id;
    private final String emotion;
    private final ArrayList<String> words;


    public Emotion(String emotion, ArrayList<String> words) {
        counter++;
        this.id = counter;
        this.emotion = emotion;
        this.words = words;
    }

    @Override
    public String getTitle() {
        return emotion;
    }

    @Override
    public ArrayList<String> getDetails() {
        return words;
    }

}
