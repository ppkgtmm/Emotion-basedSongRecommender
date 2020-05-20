/**
 *  SongManager.java
 *
 *  This class represents organization to manage songs
 *  by initialize, collecting, getting, checking and adding.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

public class SongManager
{
    /** song collection object */
    private SongCollection songs;

    /** consucture songManager */
    private static SongManager songManager= null;

    /** Reader object to access the file */
    private SongReader reader;

    /** A constucture set of song collection object */
    private SongManager()
    {
        songs = new SongCollection();
    }

    /**
     * Getter for an amount of song
     * @return an amount song
     */
    public int getAmountSongs()
    {
        return songs.getAmountSongs();
    }

    /**
     * find song id in song collection
     * @param songID    checking song id
     * @return true if finding, false if it cannot find song ID
     */
    public boolean isSongID(Integer songID)
    {
        boolean bOk = false;
        for(int counter = 0;counter< songs.getAmountSongs();counter++)
        {
            /** get a song by songID and compare */
            if(songID == songs.getAllSongs().get(counter).getId())
                bOk = true;
        }
        return bOk;
    }

    /**
     * Getter for songManager instance
     * @return songManager
     */
    public static SongManager getInstance()
    {
        if(SongManager.songManager == null)
        {
            songManager = new SongManager();
        }
        return SongManager.songManager;
    }

    /**
     * Getter for all songs
     * @return arraylist of song
     */
    public ArrayList<Song> getAllSongs()
    {
        return songs.getAllSongs();
    }

    /**
     * Getter for a song by song ID
     * @return a song
     */
    public Song getASong(Integer id)
    {
        return songs.getSongByID(id);
    }

    /**
     * Getter for songs from keyword
     * @return arraylist of song which contains keyword
     */
    public ArrayList<Song> getSongs(String keyword)
    {
        return songs.getSongs(keyword);
    }

    /**
     * read method by opening a song file and calling reader to read
     * and initial song to song collection.
     * @param fileName  a song file name
     * @return true if sucessful, false if it cannot read or get songs
     */
    public boolean readSongs(String fileName)
    {
        boolean result = false;
        reader = new SongReader();
        /** open a song text file */
        if (!reader.open(fileName))
        {
            System.out.println("Error opening song file "+fileName);
            System.exit(1);
        }
        Song nextSong = null;
        /** read,get and create song instance */
        while ((nextSong = reader.readSong()) != null)
        {
            //System.out.println("=====> Successfully added " + nextSong.getTitle());
            for (String lyric:nextSong.getLyrics())
            {
                //System.out.println(lyric);
            }
                songs.addSong(nextSong);
                result = true;
        }
        return  result;
    }

    public static void main(String[] args)
    {
        SongManager manager = SongManager.getInstance();
        boolean result = songManager.readSongs("songs.txt");
        if(result)
        {
            System.out.println(songManager.isSongID(1));
            System.out.println(songManager.isSongID(-8));
            System.out.println(songManager.isSongID(55));
        }
    }

}