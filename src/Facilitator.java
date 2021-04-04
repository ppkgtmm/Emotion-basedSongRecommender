import emotion.Emotion;
import emotion.EmotionManager;
import interfaces.Data;
import song.Song;
import song.SongManager;
import songemotion.SongEmotions;

import java.util.ArrayList;
import java.util.HashSet;

public class Facilitator {

    private SongManager songManager = null;

    private SongEmotions songEmotions = null;

    private EmotionManager emotionManager = null;

    private static Facilitator facilitator = null;

    private Facilitator() {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }


    public static Facilitator getInstance() {
        if (facilitator == null) {
            facilitator = new Facilitator();
        }
        return facilitator;
    }

    private boolean setUpRemovedSongs(String removedSongFile) {
        if (!removedSongFile.isEmpty()) {
            return songEmotions.initialize(removedSongFile);
        }
        return true;
    }


    public boolean doSetting(String songFileName, String emotionFileName, String removedSongsFile) {
        boolean removedSongsOK = setUpRemovedSongs(removedSongsFile);

        boolean emotionOK = emotionManager.readEmotions(emotionFileName);

        boolean songOK = songManager.readSongs(songFileName);

        boolean ok = false;
        if (songOK && emotionOK && removedSongsOK) {
            doSync();
            ok = true;
        }
        return ok;
    }


    private void doSync() {
        ArrayList<Data> allEmotions = emotionManager.getEmotions();
        ArrayList<Data> allSongs = songManager.getAllSongs();

        for (Data emotion : allEmotions) {
            songEmotions.sync(emotion.getTitle(), emotion.getDetails(), allSongs);
        }
    }


    public ArrayList<Data> getAllSongs() {
        return songManager.getAllSongs();
    }


    public ArrayList<Data> getSongs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return songManager.getSongs(keyword);
    }


    public ArrayList<Data> getAllEmotions() {
        return emotionManager.getEmotions();
    }


    public ArrayList<Data> getSongsFromEmotion(Emotion emotion) {
        if (emotion == null) return new ArrayList<>();
        return songEmotions.getSongsFromEmotion(emotion.getTitle());
    }


    public boolean removeSongFromCategory(Emotion emotion, Song song) {
        return songEmotions.removeFromCategory(song, emotion.getTitle());
    }


    public boolean addEmotion(String stringEmotion, ArrayList<String> words) {
        HashSet<String> uniqueWords = new HashSet<>(words);
        Emotion emotion = new Emotion(stringEmotion, new ArrayList<>(uniqueWords));
        boolean bOk = emotionManager.addEmotion(emotion);

        songEmotions.sync(emotion.getTitle(), emotion.getDetails(), songManager.getAllSongs());
        return bOk;

    }


    public boolean terminate() {

        boolean emotionsWritten = emotionManager.writeEmotions();

        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
