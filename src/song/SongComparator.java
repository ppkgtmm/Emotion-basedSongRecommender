package song;

import java.security.InvalidParameterException;
import java.util.Comparator;

public class SongComparator implements Comparator<Song> {

    private static String currentEmotion = null;


    private int compareById(Song song1, Song song2) {
        return song2.getId().compareTo(song1.getId());
    }


    @Override
    public int compare(Song song1, Song song2) {
        if (currentEmotion == null || song1 == null || song2 == null)
            throw new InvalidParameterException("Cannot compare songs score");
        int comparisonResult = song2.getScore(currentEmotion).compareTo(song1.getScore(currentEmotion));
        if (comparisonResult == 0) {
            return compareById(song1, song2);
        }
        return comparisonResult;
    }


    public static void setEmotion(String emotion) {
        if(emotion == null) throw new InvalidParameterException("Invalid emotion to compare score");
        currentEmotion = emotion;
    }
}
