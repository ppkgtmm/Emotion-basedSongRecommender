import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SongEmotions
{
    private HashMap<String,TreeSet<Song>> songsWithEmotions;
    private static SongEmotions songEmotions = null;
    private HashMap<String, ArrayList<String>> songsRemoved;
    private RemovedSongReader removedSongReader = null;

    private SongEmotions()
    {
        songsWithEmotions = new HashMap<>();
        songsRemoved = new HashMap<>();
    }

    public static SongEmotions getInstance()
    {
        if(songEmotions==null)
        {
            songEmotions = new SongEmotions();
        }
        return songEmotions;
    }

    public boolean initialize(String fileName)
    {
        boolean succeed = false;
        removedSongReader = new RemovedSongReader();
        if (!removedSongReader.open(fileName))
        {
            System.out.println("Error opening removed songs file " + fileName);
//            System.exit(1);
        }
        else
        {
            RemovedSongs removedSongs;
            while ((removedSongs = removedSongReader.readRemovedSong()) != null)
            {
                songsRemoved.put(removedSongs.getEmotion(),removedSongs.getSongs());
            }
            succeed = true;
        }
        return succeed;
    }

    public ArrayList<Song> getSongsFromEmotion(String emotion)
    {
        ArrayList<Song> result = null;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if(songs!=null)
        {
            result = new ArrayList<>(songs);
        }
        return result;
    }

    public boolean removeFromCategory(Song song,String emotion)
    {
        boolean succeed = false;
        TreeSet<Song> songs = songsWithEmotions.get(emotion);
        if(songs!=null)
        {
            succeed = songs.remove(song);
            if(songsRemoved.containsKey(emotion))
            {
                ArrayList<String> oldSongsList = songsRemoved.get(emotion);
                oldSongsList.add(song.getTitle());
                songsRemoved.put(emotion,oldSongsList);
            }
            else
            {
                ArrayList<String> newList =new ArrayList<>();
                newList.add(song.getTitle());
                songsRemoved.put(emotion,newList);
            }
        }
        return succeed;
    }

    private boolean isRemovedSong(String emotion,Song song)
    {
        boolean removed = false;
        for(String songTitle: songsRemoved.get(emotion))
        {
            if(song.getTitle().compareToIgnoreCase(songTitle)==0)
            {
                removed = true;
                break;
            }
        }
        return removed;
    }
    public void sync(String emotion,ArrayList<String> words,ArrayList<Song> songs)
    {
        SongComparator.setEmotion(emotion);
        for(Song song:songs)
        {
            if(!isRemovedSong(emotion,song))
            {
                song.countScore(emotion,words);
                if(songsWithEmotions.containsKey(emotion))
                {
                    TreeSet<Song> songsByScore = songsWithEmotions.get(emotion);
                    songsByScore.add(song);
                    songsWithEmotions.put(emotion,songsByScore);
                }
                else
                {
                    TreeSet<Song> newTreeSet = new TreeSet<>(new SongComparator());
                    newTreeSet.add(song);
                    songsWithEmotions.put(emotion,newTreeSet);
                }
            }
        }
    }
}