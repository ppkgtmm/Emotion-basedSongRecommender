package song;

import data.MockData;
import emotion.Emotion;
import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongComparatorTest {
    ArrayList<Song> songs = MockData.getSongObjects();
    ArrayList<Emotion> emotions = MockData.getEmotionObjects();
    SongComparator songComparator;

    @Test
    @Order(1)
    public void setup() {
        assertNotNull(songs);
        assertNotNull(emotions);
        assertTrue(emotions.size() > 2);
        assertTrue(songs.size() > 1);
        songComparator = new SongComparator();
        assertNotNull(songComparator);
    }

    @Test
    @Order(2)
    public void testSetInvalidEmotion() {
        // count score of null emotion
        Exception exception = assertThrows(
                InvalidParameterException.class,
                () -> SongComparator.setEmotion(null)
        );
        assertEquals("Invalid emotion to compare score", exception.getMessage());
    }

    @Test
    @Order(2)
    public void testCountScoreOfUnsetEmotion() {
        // no emotion set to count score for
        Song song1 = songs.get(0);
        Song song2 = songs.get(1);
        Exception exception = assertThrows(
                InvalidParameterException.class,
                () -> songComparator.compare(song1, song2)
        );
        assertEquals("Cannot compare songs score", exception.getMessage());
    }

    @Test
    @Order(3)
    public void testCompareSongs() {

        Emotion emotion = emotions.get(0);

        // set emotion to count score for
        SongComparator.setEmotion(emotion.getTitle());
        Song song1 = songs.get(0);
        Song song2 = songs.get(1);
        song1.countScore(emotion.getTitle(), emotion.getDetails());
        song2.countScore(emotion.getTitle(), emotion.getDetails());

        // first song in compare argument list has higher score
        assertEquals(-1, songComparator.compare(song1, song2));
    }

    @Test
    @Order(4)
    public void testCompareSongs2() {

        Emotion emotion = emotions.get(1);

        // set emotion to count score for
        SongComparator.setEmotion(emotion.getTitle());
        Song song1 = songs.get(0);
        Song song2 = songs.get(1);
        song2.countScore(emotion.getTitle(), emotion.getDetails());

        // comparison for emotion score between song with score calculated and not calculated
        assertEquals(1, songComparator.compare(song1, song2));
    }

    @Test
    @Order(5)
    public void testCompareSongs3() {

        Emotion emotion = emotions.get(2);

        // set emotion to count score for
        SongComparator.setEmotion(emotion.getTitle());
        Song song1 = songs.get(0);
        Song song2 = songs.get(1);
        song1.countScore(emotion.getTitle(), emotion.getDetails());
        song2.countScore(emotion.getTitle(), emotion.getDetails());

        // comparison for emotion score between song with equal score but different id (song 2 has higher id)
        assertEquals(1, songComparator.compare(song1, song2));
    }

    @Test
    @Order(6)
    public void testCompareSongs4() {
        // one of song is invalid
        SongComparator.setEmotion(emotions.get(0).getTitle());
        Exception exception = assertThrows(
                InvalidParameterException.class,
                () -> songComparator.compare(null, songs.get(0))
        );
        assertEquals("Cannot compare songs score", exception.getMessage());
    }

}