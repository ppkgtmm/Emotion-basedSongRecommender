package song;

import data.MockData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongTest {
    private final String[] emotions = MockData.getEmotions();
    private final String[] words = MockData.getWords();
    private final String[] titles = MockData.getSongs();
    private final String[] lyrics = MockData.getLyrics();
    private final double[][] scores = MockData.getScore();

    @Test
    @Order(1)
    public void setup() {
        assertNotNull(emotions);
        assertNotNull(words);
        assertNotNull(titles);
        assertNotNull(lyrics);
        assertNotNull(scores);
        assertEquals(emotions.length, words.length);
        assertTrue(emotions.length > 1);
        assertEquals(titles.length, lyrics.length);
        assertTrue(titles.length > 1);
    }

    // test if set properties correctly
    @Test
    @Order(2)
    public void testCreateSong() {
        int index = 0;
        assertNotNull(lyrics[index]);
        ArrayList<String> lyrics = MockData.parseLyrics(this.lyrics[index]);
        Song testSong = new Song(titles[index], lyrics);
        assertNotNull(testSong);
        assertEquals(titles[index], testSong.getTitle());
        assertEquals(lyrics, testSong.getDetails());
    }

    // test if count emotion score correctly
    @Test
    @Order(3)
    public void testSongEmotionScore() {
        ArrayList<Song> songsToTest = MockData.getSongObjects();

        assertNotNull(songsToTest);
        assertTrue(songsToTest.size() > 1);
        assertEquals(songsToTest.size(), scores.length);
        assertTrue(scores.length > 1);
        assertEquals(scores[0].length, emotions.length);

        songsToTest.get(0).countScore(emotions[0],  MockData.parseEmotionWords(words[0]));
        assertEquals(scores[0][0], songsToTest.get(0).getScore(emotions[0]));

        songsToTest.get(1).countScore(emotions[0],  MockData.parseEmotionWords(words[0]));
        assertEquals(scores[1][0], songsToTest.get(1).getScore(emotions[0]));

        songsToTest.get(1).countScore(emotions[1],  MockData.parseEmotionWords(words[1]));
        assertEquals(scores[1][1], songsToTest.get(1).getScore(emotions[1]));

    }

    // edge cases for counting song's emotion score
    @Test
    @Order(3)
    public void testSongEmotionBadInput() {
        // no lyrics -- in real scenario songs with no lyrics or any other type of invalidity e.g. no title
        // is not added to the system
        Song noLyrics = new Song("", new ArrayList<>(Arrays.asList("", "")));
        noLyrics.countScore(emotions[0], MockData.parseEmotionWords(words[0]));
        assertEquals(0, noLyrics.getScore(emotions[0]));

        Song randomSong = new Song("abcd", new ArrayList<>(Arrays.asList("abcd", "defg")));
        Song randomSong2 = new Song("abcf", new ArrayList<>(Arrays.asList("defg", "abcd")));
        String randomWords = "a b c d f ";
        // no matching emotion words in the lyrics
        String randomEmotion = "emotion";
        randomSong.countScore(randomEmotion, MockData.parseEmotionWords(randomWords));
        assertEquals(0, randomSong.getScore(randomEmotion));

        randomSong2.countScore(randomEmotion, MockData.parseEmotionWords(randomWords));
        assertEquals(0, randomSong2.getScore(randomEmotion));

        // no score counted for specified emotion
        assertEquals(-1, randomSong.getScore(emotions[0]));
    }

}
