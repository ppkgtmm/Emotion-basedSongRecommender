import java.util.Comparator;
public class SongComparator implements Comparator<Song>
{
    private static String currentEmotion = null;

    private int compareById(Song song1,Song song2)
    {
        return song1.getId().compareTo(song2.getId());
    }
    @Override
    public int compare(Song song1,Song song2) {
        if(song2.getScore(currentEmotion)>song1.getScore(currentEmotion))
            return 1;
        else if(song1.getScore(currentEmotion)>song2.getScore(currentEmotion))
            return -1;
        else
            return compareById(song1,song2);
    }

    public static void setEmotion(String emotion)
    {
        currentEmotion = emotion;
    }
}
