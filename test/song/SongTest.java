package song;

import data.MockData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongTest {
    private final String[] emotions = MockData.getEmotions();
    private final ArrayList<String> words = MockData.getWords();
    private final String[] titles = MockData.getSongs();
    private final ArrayList<String> lyrics = MockData.getLyrics();
    private final double[][] scores = MockData.getScore();

    @Test
    @Order(1)
    public void testSetup() {
        assertEquals(emotions.length, words.size());
        assertNotEquals(0, emotions.length);
        assertEquals(titles.length, lyrics.size());
        assertNotEquals(0, titles.length);
    }


    @Test
    @Order(2)
    public void testCreateSongs(){
        int index = 0;
        assertNotNull(titles[index]);
        assertNotNull(lyrics.get(index));
        ArrayList<String> lyrics = new ArrayList<>(Arrays.asList(this.lyrics.get(index).split("\n")));
        Song testSong = new Song(titles[index], lyrics );
        assertNotNull(testSong);
        assertEquals(index+1, testSong.getId());
        assertEquals(titles[index], testSong.getTitle());
        assertEquals(lyrics, testSong.getDetails());
    }

    private void testCountScore(Song song, String emotion, String[] word, double expectedScore){
        song.countScore(emotion, new ArrayList<>(Arrays.asList(word)));
        assertEquals(expectedScore,song.getScore(emotion));
    }

    @Test
    @Order(3)
    public void testSongEmotionScore(){
        ArrayList<Song> songsToTest = new ArrayList<>();
        for(int i = 0; i < titles.length; i++){
            ArrayList<String> songLyrics = new ArrayList<>(Arrays.asList(this.lyrics.get(i).split("\n")));
            songsToTest.add(new Song(titles[i], songLyrics ));
        }

        assertTrue(songsToTest.size()>1);
        assertEquals(songsToTest.size(), scores.length);
        assertEquals(scores[0].length, emotions.length);

        testCountScore(
                songsToTest.get(0),
                emotions[0], words.get(0).split(" "),
                scores[0][0]
        );

        testCountScore(
                songsToTest.get(1),
                emotions[1], words.get(1).split(" "),
                scores[1][1]
        );

    }

    @Test
    @Order(3)
    public void testSongEmotionBadInput(){
        // no lyrics
        testCountScore(
                new Song("", new ArrayList<>(Arrays.asList("", ""))),
                emotions[0],
                new String[]{"a", "b"},
                0
        );

        Song randomSong = new Song(
                "abcd",
                new ArrayList<>(Arrays.asList("abcd", "defg"))
        );

        // no matching words
        testCountScore(
                randomSong,
                emotions[0],
                new String[]{"a", "b"},
                0
        );

        // no score counted for specified emotion
        assertEquals(-1, randomSong.getScore(emotions[1]));
    }

}