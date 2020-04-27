import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class RemovedSongs
{
    private String emotion;
    private ArrayList<String> songs = null;

    public RemovedSongs(String emotion,ArrayList<String> songs)
    {
        this.emotion = emotion;
        this.songs = songs;
    }

    public String getEmotion() 
    {
        return emotion;
    }

    public ArrayList<String> getSongs() 
    {
        return songs;
    }
}