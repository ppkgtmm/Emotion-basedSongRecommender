package song;

import data.MockData;
import interfaces.Data;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongManagerTest {
    private SongManager songManager;
    private final ArrayList<Song> songs = MockData.getSongObjects();

    @Test
    @Order(1)
    void setUp(){
        assertNotNull(songs);
        assertTrue(songs.size() > 1);
        songManager = SongManager.getInstance();
        assertNotNull(songManager);
    }


    @Test
    @Order(2)
    void testAddSong() {
        for(Song song: songs){
            songManager.addSong(song);
        }
        // should not add invalid songs
        songManager.addSong(null);
        songManager.addSong(new Song(null, null));
        songManager.addSong(new Song(" ", null));
        songManager.addSong(new Song("abc", new ArrayList<>()));

        // already added song should not be added
        songManager.addSong(songs.get(0));

        // only added songs (valid ones) should be found
        assertTrue(songs.equals(songManager.getAllSongs()));
    }

    @Test
    @Order(3)
    void testGetSongs() {
        // song 2 -- "I don't lie to you" should only be found
        ArrayList<Data> songsFound = songManager.getSongs("you");
        assertNotNull(songsFound);
        assertEquals(1, songsFound.size());
        assertEquals(songs.get(1),songsFound.get(0));
    }

}