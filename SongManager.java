import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SongManager
{
    private SongCollection songs;
    private static SongManager songManager= null;
    private SongReader reader;

    private SongManager()
    {
        songs = new SongCollection();
    }
    public static SongManager getInstance()
    {
        if(SongManager.songManager == null)
        {
            songManager = new SongManager();
        }
        return SongManager.songManager;
    }

    public ArrayList<Song> getAllSongs()
    {
        return songs.getAllSongs();
    }

    public Song getASong(Integer id)
    {
        return songs.getSongByID(id);
    }

    public ArrayList<Song> getSongs(String keyword)
    {
        return songs.getSongs(keyword);
    }

    public boolean readSongs(String fileName)
    {
        boolean result = false;
        reader = new SongReader();
        if (!reader.open(fileName))
        {
            System.out.println("Error opening song file "+fileName);
            System.exit(1);
        }
        Song nextSong = null;
        while ((nextSong = reader.readSong()) != null)
        {
            System.out.println("=====> Successfully added " + nextSong.getTitle());
            for (String lyric:nextSong.getLyrics())
            {
                System.out.println(lyric);
            }
                songs.addSong(nextSong);
                result = true;
        }
        return  result;
    }

    public static void main(String[] args) {
        SongManager manager = SongManager.getInstance();
        boolean result = songManager.readSongs("songsShort.txt");
        if(result)
            System.out.println("OKKKKKK");

    }

}
