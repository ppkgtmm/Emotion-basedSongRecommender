package song;

import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class SongManager {

    private HashMap<Integer,Song> songs;


    private static SongManager songManager = null;


    private CustomReader reader;

    private static final String titlePattern = "Song :";
    private static final String lyricsPattern = "Lyrics :";


    private SongManager() {
        songs = new HashMap<>();
    }


    public static SongManager getInstance() {
        if (SongManager.songManager == null) {
            songManager = new SongManager();
        }
        return SongManager.songManager;
    }


    public ArrayList<Data> getAllSongs() {
        return new ArrayList<>(songs.values());
    }


    public ArrayList<Data> getSongs(String keyword) {
        ArrayList<Data> result = new ArrayList<>();
        if(keyword == null || keyword.trim().isEmpty()) return result;
        for(Song song : songs.values()){
            if(song.getTitle().contains(keyword)){
                result.add(song);
            }
        }
        return result;
    }

    private void addSong(Song song){
        if(song == null || !Utils.isValidData(song)) return;
        if(!songs.containsKey(song.getId())){
            songs.put(song.getId(),song);
        }
    }


    public boolean readSongs(String fileName) {
        reader = new CustomReader();

        if (!reader.open(fileName)) {
            System.out.println("Error opening song file " + fileName);
            return false;
        }
        ReaderDTO songData;

        while ((songData = reader.readData(SongManager.titlePattern, SongManager.lyricsPattern)) != null) {
            if (Utils.isValidData(songData, "Skipping invalid song")) {
                addSong(new Song(songData.getTitle(), songData.getDetails()));
            }
        }
        return songs.size() > 0;
    }


}