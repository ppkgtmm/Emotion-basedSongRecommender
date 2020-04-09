import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions
{
    private HashMap<String,TreeSet<Song>> songsWithEmotions;
    private HashMap<String,ArrayList<String>> songsRemoved = new HashMap<String,ArrayList<String>>();

    public SongEmotions(String emotion,ArrayList<String> songs)
    {
        songsRemoved.put(emotion,songs);
    }
}