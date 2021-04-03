package songemotion;


import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;
import song.Song;
import song.SongComparator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions {


    private HashMap<String, TreeSet<Song>> songsWithEmotions;


    private static SongEmotions songEmotions = null;


    private HashMap<String, ArrayList<String>> songsRemoved;


    private CustomReader reader;

    private static final String emotionPattern = "Emotion :";
    private static final String songPattern = "Songs :";


    private SongEmotions() {
        songsWithEmotions = new HashMap<>();
        songsRemoved = new HashMap<>();
    }


    public static SongEmotions getInstance() {
        if (songEmotions == null) {
            songEmotions = new SongEmotions();
        }
        return songEmotions;
    }


    public boolean initialize(String fileName) {
        reader = new CustomReader();

        if (!reader.open(fileName)) {
            System.out.println("Error opening removed songs file " + fileName);
            return false;
        }
        ReaderDTO data;
        while ((data = reader.readData(
                SongEmotions.emotionPattern,
                SongEmotions.songPattern
        )) != null) {
            String emotion = data.getTitle();
            ArrayList<String> songs = data.getDetails();

            if (data.isIncompleteData()) {
                System.out.println("Skipping invalid data");
                continue;
            }
            songsRemoved.put(emotion, songs);

        }
        return true;
    }


    public ArrayList<Data> getSongsFromEmotion(String emotion) {
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if (songs != null) {
            return new ArrayList<>(songs);
        }
        return new ArrayList<>();
    }

    private boolean isNotDeletedSong(String emotion, Song song){
        if(songsRemoved.containsKey(emotion) && songsRemoved.get(emotion).contains(song.getTitle())){
            return false;
        }
        return true;
    }

    public boolean removeFromCategory(Song song, String emotion) {
        boolean succeed = false;

        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if (songs != null) {
            if(!isNotDeletedSong(emotion, song)){
                return false;
            }
            if (songsRemoved.containsKey(emotion)) {
                ArrayList<String> oldSongsList = songsRemoved.get(emotion);
                oldSongsList.add(song.getTitle());
                songsRemoved.put(emotion, oldSongsList);
            } else {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(song.getTitle());
                songsRemoved.put(emotion, newList);
            }

            SongComparator.setEmotion(emotion);

            succeed = songsWithEmotions.get(emotion).remove(song);
        }
        return succeed;
    }


    private void addSong(Song song, String emotion) {
        if (song.getScore(emotion) > 0.1) {
            if (songsWithEmotions.containsKey(emotion)) {
                TreeSet<Song> sortedSongs = songsWithEmotions.get(emotion);
                sortedSongs.add(song);
                songsWithEmotions.put(emotion, sortedSongs);
            } else {
                TreeSet<Song> newTreeSet = new TreeSet<>(new SongComparator());
                newTreeSet.add(song);
                songsWithEmotions.put(emotion, newTreeSet);
            }
        }
    }


    public void sync(
            String emotion,
            ArrayList<String> words,
            ArrayList<Data> songs
    ) {

        SongComparator.setEmotion(emotion);
        for (Data song : songs) {
            Song castedSong = (Song) song;
            if (isNotDeletedSong(emotion, castedSong)) {
                castedSong.countScore(emotion, words);
                addSong(castedSong, emotion);
            }
        }
    }


    public boolean writeRemovedSongs() {
        boolean succeed = false;
        try {
            FileWriter writer = new FileWriter("removed.txt");
            ArrayList<String> emotions = new ArrayList<>(songsRemoved.keySet());

            for (String emotion : emotions) {

                ArrayList<String> songs = songsRemoved.get(emotion);

                writer.write("Emotion : " + emotion.toLowerCase() + "\n");
                writer.write("Songs :" + "\n");

                for (String song : songs) {
                    writer.write(song.toLowerCase() + "\n");
                }
                writer.write("==END==\n");
            }
            writer.close();
            succeed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return succeed;

    }
}