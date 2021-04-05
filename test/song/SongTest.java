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
    private final ArrayList<String> words = MockData.getWords();
    private final String[] titles = MockData.getSongs();
    private final ArrayList<String> lyrics = MockData.getLyrics();
    private final double[][] scores = MockData.getScore();
    private final SongComparator comparator = new SongComparator();
    private SongManager songManager;

    @Test
    @Order(1)
    public void setup() {
        assertEquals(emotions.length, words.size());
        assertNotEquals(0, emotions.length);
        assertEquals(titles.length, lyrics.size());
        assertNotEquals(0, titles.length);
        songManager = SongManager.getInstance();
        assertNotNull(songManager);
    }

    @Test
    @Order(2)
    public void testCreateSong() {
        int index = 0;
        assertNotNull(titles[index]);
        assertNotNull(lyrics.get(index));
        ArrayList<String> lyrics = new ArrayList<>(Arrays.asList(this.lyrics.get(index).split("\n")));
        Song testSong = new Song(titles[index], lyrics);
        assertNotNull(testSong);
        assertEquals(titles[index], testSong.getTitle());
        assertEquals(lyrics, testSong.getDetails());
    }

    @Test
    private void testCountScore(Song song, String emotion, String[] word, double expectedScore) {
        song.countScore(emotion, new ArrayList<>(Arrays.asList(word)));
        assertEquals(expectedScore, song.getScore(emotion));
    }

    @Test
    @Order(3)
    public void testSongEmotionScore() {
        ArrayList<Song> songsToTest = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ArrayList<String> songLyrics = new ArrayList<>(Arrays.asList(this.lyrics.get(i).split("\n")));
            songsToTest.add(new Song(titles[i], songLyrics));
        }

        assertTrue(songsToTest.size() > 1);
        assertEquals(songsToTest.size(), scores.length);
        assertEquals(scores[0].length, emotions.length);

        testCountScore(songsToTest.get(0), emotions[0], words.get(0).split(" "), scores[0][0]);
        testCountScore(songsToTest.get(1), emotions[0], words.get(0).split(" "), scores[1][0]);
        testCountScore(songsToTest.get(1), emotions[1], words.get(1).split(" "), scores[1][1]);
        SongComparator.setEmotion(emotions[0]);
        assertTrue(comparator.compare(songsToTest.get(1), songsToTest.get(0)) > 0);
    }

    @Test
    @Order(3)
    public void testSongEmotionBadInput() {
        // no lyrics
        testCountScore(
                new Song("", new ArrayList<>(Arrays.asList("", ""))),
                emotions[0],
                new String[]{"a", "b"},
                0);

        Song randomSong = new Song("abcd", new ArrayList<>(Arrays.asList("abcd", "defg")));
        Song randomSong2 = new Song("abcd", new ArrayList<>(Arrays.asList("defg", "abcd")));
        // no matching words
        testCountScore(randomSong, emotions[0], new String[]{"a", "b"}, 0);
        testCountScore(randomSong2, emotions[0], new String[]{"a", "b"}, 0);
        // no score counted for specified emotion
        assertEquals(-1, randomSong.getScore(emotions[1]));

        SongComparator.setEmotion(emotions[0]);

        // compare songs with equal emotion score (the second song in argument has greater id)
        assertTrue(comparator.compare(randomSong, randomSong2) > 0);
    }

}
