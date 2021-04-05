package data;

import java.util.ArrayList;
import java.util.Arrays;

public class MockData {
    private static final String[] emotions = {
            "worry",
            "happy"
    };

    // emotion words separated by space
    private static final String[] words = {
            "worry fine serious truth lie",
            "fine super joyous happy wow yay"
    };

    // song titles
    private static final String[] songs = {
            "Why so serious",
            "I don't lie to you",

    };

    // emotion scores where score[i][j] = score for song i emotion j
    private static final double [][] score = {
            {(double) 3/13, (double) 2/13},
            {(double) 2/16, (double) 0/16}
    };

    // song lyrics which will be parsed using new line character
    private static final String[] lyrics = {
                    "Life is not that serious, everything\ngonna be fine why worry be happy",
                    "When tell me, when did I lie I don't know why\nI only spoken the truth"

    };

    public static ArrayList<String> getLyrics() {
        return new ArrayList<>(Arrays.asList(lyrics));
    }

    public static String[] getSongs() {
        return songs;
    }

    public static String[] getEmotions() {
        return emotions;
    }

    public static ArrayList<String> getWords() {
        return new ArrayList<>(Arrays.asList(words));
    }

    public static double[][] getScore() {
        return score;
    }
}
