package data;

import emotion.Emotion;
import song.Song;

import java.util.ArrayList;
import java.util.Arrays;

public class MockData {
    private static final String[] emotions = {
            "worry",
            "so happy",
            "duh"
    };

    // emotion words separated by space
    private static final String[] words = {
            "worry fine serious truth lie",
            "fine super joyous happy wow yay",
            "duh uh yah prr"
    };

    // song titles
    private static final String[] songs = {
            "Why so serious",
            "I don't lie to you",

    };

    // emotion scores where score[i][j] = score for song i emotion j
    private static final double[][] score = {
            {(double) 3 / 13, (double) 2 / 13, (double) 0 / 13},
            {(double) 2 / 16, (double) 0 / 16, (double) 0 / 16}
    };

    // song lyrics which will be parsed using new line character
    private static final String[] lyrics = {
            "Life is not that serious, everything\ngonna be fine why worry be happy",
            "When tell me, when did I lie I don't know why\nI only spoken the truth"

    };

    private static ArrayList<Song> songObjects;
    private static ArrayList<Emotion> emotionObjects;


    public static String[] getLyrics() {
        return lyrics;
    }

    public static String[] getSongs() {
        return songs;
    }

    public static String[] getEmotions() {
        return emotions;
    }

    public static String[] getWords() {
        return words;
    }

    public static double[][] getScore() {
        return score;
    }

    private static ArrayList<Song> createSongObjects() throws Exception {
        if (songs.length != lyrics.length) throw new Exception("Invalid song data");
        ArrayList<Song> result = new ArrayList<>();
        for (int i = 0; i < songs.length; i++) {
            ArrayList<String> songLyrics = parseLyrics(lyrics[i]);
            result.add(new Song(songs[i], songLyrics));
        }
        return result;
    }

    private static ArrayList<Emotion> createEmotionObjects() throws Exception {
        if (emotions.length != words.length) throw new Exception("Invalid emotion data");
        ArrayList<Emotion> result = new ArrayList<>();
        for (int i = 0; i < emotions.length; i++) {
            ArrayList<String> wordList = parseEmotionWords(words[i]);
            result.add(new Emotion(emotions[i], wordList));
        }
        return result;
    }

    public static ArrayList<Emotion> getEmotionObjects() {
        if (emotionObjects == null) {
            try {
                emotionObjects = createEmotionObjects();
            } catch (Exception e) {
                e.printStackTrace();
                emotionObjects = null;
            }
        }
        return emotionObjects;
    }

    public static ArrayList<Song> getSongObjects() {
        if (songObjects == null) {
            try {
                songObjects = createSongObjects();
            } catch (Exception e) {
                e.printStackTrace();
                songObjects = null;
            }
        }
        return songObjects;
    }

    public static ArrayList<String> parseLyrics(String stringLyrics) {
        return new ArrayList<>(Arrays.asList(stringLyrics.split("\n")));
    }

    public static ArrayList<String> parseEmotionWords(String words) {
        return new ArrayList<>(Arrays.asList(words.split(" ")));
    }
}
