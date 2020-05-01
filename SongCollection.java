
import java.util.ArrayList;

public class SongCollection
{
    private ArrayList<Song> songs;

    public SongCollection()
    {
        songs = new ArrayList<>();
    }

    public ArrayList<Song> getAllSongs() {
        return songs;
    }

    public ArrayList<Song> getSongs(String keyword) {
        ArrayList<Song> result = new ArrayList<>();
        for(Song song:songs)
        {
            if(song.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            {
                result.add(song);
            }
        }
        return result;
    }

    public int getAmountSongs()
    {
        return songs.size();
    }

    public void addSong(Song song)
    {
        songs.add(song);
    }

    public Song getSongByID(Integer id)
    {
        for(Song song:songs)
        {
            if(song.getId().equals(id))
            {
                return song;
            }
        }
        return null;
    }
}
