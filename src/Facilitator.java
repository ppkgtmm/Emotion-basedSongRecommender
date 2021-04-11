import emotion.Emotion;
import emotion.EmotionManager;
import interfaces.Data;
import song.Song;
import song.SongManager;
import songemotion.SongEmotions;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;

public class Facilitator {

    private SongManager songManager;

    private SongEmotions songEmotions;

    private EmotionManager emotionManager;

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

    private boolean setUpRemovedSongs(String removedSongFile, ArrayList<Data> emotions) {
        if (!Utils.isInvalidString(removedSongFile)) {
            return songEmotions.initialize(removedSongFile, emotions);
        }
        return true;
    }


    public boolean doSetting(String songFileName, String emotionFileName, String removedSongsFile) {
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);

        boolean songOK = songManager.readSongs(songFileName);

        boolean ok = false;
        if (songOK && emotionOK) {
            boolean removedSongsOK = setUpRemovedSongs(removedSongsFile, emotionManager.getEmotions());
            return removedSongsOK && doSync();
        }
        return ok;
    }


    private boolean doSync() {
        ArrayList<Data> allEmotions = emotionManager.getEmotions();
        ArrayList<Data> allSongs = songManager.getAllSongs();
        boolean result = true;
        for (Data emotion : allEmotions) {
            result = result && songEmotions.sync(emotion.getTitle(), emotion.getDetails(), allSongs);
        }
        return result;
    }


    public ArrayList<Data> getAllSongs() {
        return songManager.getAllSongs();
    }


    public ArrayList<Data> getSongs(String keyword) {
        if(Utils.isInvalidString(keyword)) return null;
        return songManager.getSongs(keyword);
    }


    public ArrayList<Data> getAllEmotions() {
        return emotionManager.getEmotions();
    }


    public ArrayList<Data> getSongsFromEmotion(Emotion emotion) {
        if (!Utils.isValidData(emotion)) return new ArrayList<>();
        return songEmotions.getSongsFromEmotion(emotion.getTitle());
    }


    public int removeSongFromCategory(Emotion emotion, Song song) {
        if (!Utils.isValidData(song) || !Utils.isValidData(emotion)) {
            return 0;
        }
        return songEmotions.removeFromCategory(song, emotion.getTitle()) ? 1 : -1;
    }


    public boolean addEmotion(String stringEmotion, ArrayList<String> words) {
        HashSet<String> uniqueWords = words == null ? new HashSet<>() : new HashSet<>(words);
        Emotion emotion = new Emotion(stringEmotion, new ArrayList<>(uniqueWords));
        if (!Utils.isValidData(emotion)) return false;
        boolean bOk = emotionManager.addEmotion(emotion);
        if(!bOk) return false;
        return songEmotions.sync(emotion.getTitle(), emotion.getDetails(), songManager.getAllSongs());

    }


    public boolean terminate() {
        boolean emotionsWritten = emotionManager.writeEmotions();
        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
