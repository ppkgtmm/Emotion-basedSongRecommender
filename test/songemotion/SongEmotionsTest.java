package songemotion;

import data.MockData;
import emotion.Emotion;
import interfaces.Data;
import org.junit.jupiter.api.*;
import song.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongEmotionsTest {
    ArrayList<Song> songs = MockData.getSongObjects();
    ArrayList<Emotion> emotions = MockData.getEmotionObjects();
    SongEmotions songEmotionManager;

    @Test
    @Order(1)
    public void setup() {
        assertNotNull(songs);
        assertNotNull(emotions);
        assertTrue(songs.size() > 1);
        assertTrue(emotions.size() > 2);
        songEmotionManager = SongEmotions.getInstance();
        assertNotNull(songEmotionManager);
    }

    @Test
    @Order(2)
    public void testSync() {
        for (Emotion emotion : emotions) {
            assertTrue(songEmotionManager.sync(emotion.getTitle(), emotion.getDetails(), new ArrayList<>(songs)));
        }
        // invalid argument for counting songs score passed
        assertFalse(songEmotionManager.sync(null, emotions.get(0).getDetails(), new ArrayList<>(songs)));
        assertFalse(songEmotionManager.sync(" ", emotions.get(0).getDetails(), new ArrayList<>(songs)));
        assertFalse(songEmotionManager.sync(emotions.get(0).getTitle(), null, new ArrayList<>(songs)));
        assertFalse(songEmotionManager.sync(emotions.get(0).getTitle(), new ArrayList<>(), new ArrayList<>(songs)));
        assertFalse(songEmotionManager.sync(emotions.get(0).getTitle(), emotions.get(0).getDetails(), null));
        assertFalse(songEmotionManager.sync(emotions.get(0).getTitle(), emotions.get(0).getDetails(), new ArrayList<>()));
    }

    @Test
    @Order(3)
    public void testGetSongsFromEmotion() {

        // emotion worry related songs
        ArrayList<Data> worrySongs = songEmotionManager.getSongsFromEmotion(emotions.get(0).getTitle());
        assertNotNull(worrySongs);
        assertEquals(2, worrySongs.size());
        // first song in mock data should have the highest score for emotion worry
        assertEquals(songs.get(0), worrySongs.get(0));

        ArrayList<Data> duhSongs = songEmotionManager.getSongsFromEmotion(emotions.get(2).getTitle());
        assertNotNull(duhSongs);
        // should found no songs
        assertEquals(0, duhSongs.size());

        // should found no songs for invalid emotions or unknown emotions
        // invalid emotions
        ArrayList<Data> nullEmotionSongs = songEmotionManager.getSongsFromEmotion(null);
        assertNotNull(nullEmotionSongs);
        assertEquals(0, nullEmotionSongs.size());

        ArrayList<Data> emptyEmotionSongs = songEmotionManager.getSongsFromEmotion("");
        assertNotNull(emptyEmotionSongs);
        assertEquals(0, emptyEmotionSongs.size());

        // unknown emotion
        ArrayList<Data> unknownEmotionSong = songEmotionManager.getSongsFromEmotion("abc");
        assertNotNull(unknownEmotionSong);
        assertEquals(0, unknownEmotionSong.size());

    }

    @Test
    @Order(4)
    public void testRemoveFromCategory() {
        assertTrue(songEmotionManager.removeFromCategory(songs.get(0), emotions.get(0).getTitle()));
        ArrayList<Data> worrySongs = songEmotionManager.getSongsFromEmotion(emotions.get(0).getTitle());
        assertNotNull(worrySongs);
        // song should be removed
        assertEquals(1, worrySongs.size());
        assertFalse(worrySongs.contains(songs.get(0)));

        // second song in mock data should now have the highest score for emotion worry
        assertEquals(songs.get(1), worrySongs.get(0));

        // should not allow removing already deleted song
        assertFalse(songEmotionManager.removeFromCategory(songs.get(0), emotions.get(0).getTitle()));

        // should not allow removing invalid or unknown songs from invalid or unknown emotions
        assertFalse(songEmotionManager.removeFromCategory(null, emotions.get(0).getTitle()));
        assertFalse(songEmotionManager.removeFromCategory(new Song(null, null), null));
        assertFalse(songEmotionManager.removeFromCategory(new Song("title", new ArrayList<>()), ""));
        assertFalse(songEmotionManager.removeFromCategory(songs.get(1), " 090 "));
    }
}