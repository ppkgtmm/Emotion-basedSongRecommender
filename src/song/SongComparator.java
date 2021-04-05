package song;

import java.util.Comparator;

public class SongComparator implements Comparator<Song> {

    private static String currentEmotion = null;


    private int compareById(Song song1, Song song2) {
        return song2.getId().compareTo(song1.getId());
    }


    @Override
    public int compare(Song song1, Song song2) {
        int comparisonResult = song2.getScore(currentEmotion).compareTo(song1.getScore(currentEmotion));
        if (comparisonResult == 0) {
            return compareById(song1, song2);
        }
        return comparisonResult;
    }


    public static void setEmotion(String emotion) {
        currentEmotion = emotion;
    }
}
