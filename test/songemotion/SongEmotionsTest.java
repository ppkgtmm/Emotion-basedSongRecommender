package songemotion;

import data.MockData;
import emotion.Emotion;
import interfaces.Data;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import song.Song;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongEmotionsTest {
    ArrayList<Data> songsToTest = new ArrayList<>();
    ArrayList<Emotion> emotionsToTest = new ArrayList<>();
    SongEmotions songEmotionManager;

    @Test
    @Order(1)
    public void setup() {
        String[] emotions = MockData.getEmotions();
        ArrayList<String> words = MockData.getWords();
        String[] titles = MockData.getSongs();
        ArrayList<String> lyrics = MockData.getLyrics();
        for (int i = 0; i < titles.length; i++) {
            ArrayList<String> songLyrics = new ArrayList<>(Arrays.asList(lyrics.get(i).split("\n")));
            songsToTest.add(new Song(titles[i], songLyrics));
        }
        for (int i = 0; i < emotions.length; i++) {
            ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words.get(i).split(" ")));
            emotionsToTest.add(new Emotion(emotions[i], wordList));
        }
        assertEquals(titles.length, songsToTest.size());
        assertEquals(emotions.length, emotionsToTest.size());
        songEmotionManager = SongEmotions.getInstance();
        assertNotNull(songEmotionManager);
    }

    @Test
    @Order(2)
    public void getSongsFromEmotion() {
        for (Emotion emotion : emotionsToTest) {
            songEmotionManager.sync(emotion.getTitle(), emotion.getDetails(), songsToTest);
        }
        // emotion worry related songs
        ArrayList<Data> worrySongs = songEmotionManager.getSongsFromEmotion(emotionsToTest.get(0).getTitle());
        assertEquals(2, worrySongs.size());

        // first song in mock data should have the highest score for emotion worry
        assertEquals(songsToTest.get(0), worrySongs.get(0));
    }

    @Test
    @Order(3)
    public void removeFromCategory() {
        assertTrue(songEmotionManager.removeFromCategory(songsToTest.get(0),emotionsToTest.get(0).getTitle()) == 1);
    }
}