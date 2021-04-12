package songemotion;


import interfaces.Data;
import reader.CustomReader;
import reader.dto.ReaderDTO;
import song.Song;
import song.SongComparator;
import utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions {


    private static final String emotionPattern = "Emotion :";
    private static final String songPattern = "Songs :";
    private static SongEmotions songEmotions = null;
    private HashMap<String, TreeSet<Song>> songsWithEmotions;
    private HashMap<String, ArrayList<String>> songsRemoved;
    private CustomReader reader;


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

    private boolean initRemovedSongMap(ArrayList<Data> emotions) {
        if (Utils.isInvalidList(emotions)) return false;
        if (songsRemoved.size() > 0) return false;
        for (Data emotion : emotions) {
            if (Utils.isValidData(emotion)) {
                songsRemoved.put(emotion.getTitle(), new ArrayList<>());
            }
        }
        return true;
    }

    public boolean initialize(String fileName, ArrayList<Data> emotions) {
        reader = new CustomReader();

        if (!reader.open(fileName)) {
            System.out.println("Error opening removed songs file " + fileName);
            return false;
        }
        if (!initRemovedSongMap(emotions)) return false;
        ReaderDTO data;
        while ((data = reader.readData(
                SongEmotions.emotionPattern,
                SongEmotions.songPattern
        )) != null) {
            if (!Utils.isValidData(data, "Skipping invalid data")) {
                continue;
            }
            String emotion = data.getTitle();
            ArrayList<String> songs = data.getDetails();
            if (songsRemoved.containsKey(emotion) && Utils.isInvalidList(songsRemoved.get(emotion))) {
                songsRemoved.put(emotion, songs);
            }
        }
        reader.close();
        return songsRemoved.size() > 0;
    }


    public ArrayList<Data> getSongsFromEmotion(String emotion) {
        if (Utils.isInvalidString(emotion)) {
            System.out.println("Invalid emotion to get songs from");
            return new ArrayList<>();
        }
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if (songs == null) {
            System.out.println("Unknown emotion provided");
        }
        return songs != null ? new ArrayList<>(songs) : new ArrayList<>();
    }

    private boolean isDeletedSong(String emotion, Song song, boolean verbose) {
        if (songsRemoved.containsKey(emotion) && songsRemoved.get(emotion).contains(song.getTitle())) {
            if (verbose) System.out.println("Song is already removed from emotion");
            return true;
        }
        return false;
    }

    private void addToRemovedSongMap(Song song, String emotion) {
        if (songsRemoved.containsKey(emotion)) {
            ArrayList<String> oldSongsList = songsRemoved.get(emotion);
            oldSongsList.add(song.getTitle());
            songsRemoved.put(emotion, oldSongsList);
        } else {
            ArrayList<String> newList = new ArrayList<>();
            newList.add(song.getTitle());
            songsRemoved.put(emotion, newList);
        }
    }

    public boolean removeFromCategory(Data song, String emotion) {
        if (!Utils.isValidData(song) || Utils.isInvalidString(emotion)) {
            System.out.println("Invalid argument(s) to remove song from emotion");
            return false;
        }
        boolean succeed = false;
        Song castedSong = (Song) song;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if (songs != null) {
            if (isDeletedSong(emotion, castedSong, true)) {
                return false;
            }
            addToRemovedSongMap(castedSong, emotion);
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


    public boolean sync(
            String emotion,
            ArrayList<String> words,
            ArrayList<Data> songs
    ) {

        if (Utils.isInvalidString(emotion) || Utils.isInvalidList(words) || Utils.isInvalidList(songs)) {
            System.out.println("Invalid argument(s) provided to count songs emotion score");
            return false;
        }
        SongComparator.setEmotion(emotion);
        for (Data song : songs) {
            Song castedSong = (Song) song;
            if (!isDeletedSong(emotion, castedSong, false)) {
                castedSong.countScore(emotion, words);
                addSong(castedSong, emotion);
            }
        }
        return true;
    }


    public boolean writeRemovedSongs() {
        boolean succeed = false;
        try {
            FileWriter writer = new FileWriter("removed.txt");
            ArrayList<String> emotions = new ArrayList<>(songsRemoved.keySet());

            for (String emotion : emotions) {

                ArrayList<String> songs = songsRemoved.get(emotion);
                if (Utils.isInvalidList(songs))
                    continue;

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
            System.out.println("Unable to write songs removed from emotion to text file");
        }
        return succeed;

    }
}