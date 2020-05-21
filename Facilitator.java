/**
 *
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Facilitator
{
    /** SongManager instance to help dealing with songs. */
    private SongManager songManager = null;

    /**
     *  SongEmotions instance to help dealing with songs
     *  related to each emotion.
     */
    private SongEmotions songEmotions = null;

    /** EmotionManager instance to help dealing with emotions. */
    private EmotionManager emotionManager = null;

    /** Facilitator instance which let other classes use its methods*/
    private static Facilitator facilitator = null;

    /** Scanner object used to get input from user */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor which initialize songManager, songEmotions
     * and emotionManager instance
     */
    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }

    /**
     * Getter for instance of facilitator which is created only
     * once.
     * @return facilitator instance
     */
    public static Facilitator getInstance()
    {
        if (facilitator==null)
        {
            facilitator = new Facilitator();
        }
        return facilitator;
    }

    /**
     * set up data inside the program like songs, emotions and
     * removed songs which will be used in the program
     * @param songFileName name of song text file
     * @param emotionFileName name of emotion text file
     * @param removedSongsFile name of removed song text file
     * @return true if succeed , false if error occurred
     */
    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        /* to read and store emotions */
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        /* to read and store songs */
        boolean songOK = songManager.readSongs(songFileName);
        /* to store result from doing set up for songs removed from emotion category */
        boolean removedSongsOK;
        boolean ok = false;
        /* user has provided removed songs file name */
        if(!removedSongsFile.isEmpty())
        {
            /* to read and store removed songs */
            removedSongsOK = songEmotions.initialize(removedSongsFile);
            /* check if no errors found */
            if(songOK && emotionOK && removedSongsOK)
            {
                /* to count emotions scores of song and store song sorted
                in emotion category */
                doSync();
                ok = true; /* no errors */
            }
        }
        /* user does not provide removed song file name */
        else if(songOK && emotionOK)
        {
            /* to count emotions scores of song and store song sorted
                in emotion category */
             doSync();
             ok = true; /* no errors */
        }
        return ok;
    }

    /**
     * call function to count emotions scores of songs and store songs
     * in sorted order in each emotion category.
     */
    private void doSync()
    {
        ArrayList<Emotion> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        /* to count score of songs for each emotion */
        for(Emotion emotion:allEmotions)
        {
            songEmotions.sync(emotion.getEmotion(),emotion.getWords(),allSongs);
        }
    }

    /**
     * Displays all emotions in the system
     */
    public void printAllEmotions()
    {
        /* getting all available emotion(s)*/
        ArrayList<Emotion> allEmotions = emotionManager.getEmotions();
        if(allEmotions==null || allEmotions.size()==0)
        {
            System.out.println("There are no emotions stored in the system");
        }
        /* there exist some emotion(s) in the system */
        else
        {
            System.out.println(">> Emotion List <<");
            for (int counter = 0; counter < allEmotions.size(); counter++)
            {
                System.out.println((counter+1)+ " " + allEmotions.get(counter).getEmotion());
            }
        }
    }

    /**
     * Print lyrics of song provided
     * @param currentSong song to print lyrics
     *
     */
    public void printLyrics(Song currentSong)
    {
            if(currentSong!=null)
            {
                ArrayList<String> currentLyrics = currentSong.getLyrics();
                /* song has lyrics */
                if(currentLyrics!=null && currentLyrics.size()>0)
                {
                    System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                    for (int counter = 0; counter < currentLyrics.size(); counter++)
                    {
                        System.out.println(currentLyrics.get(counter));
                    }
                }
                else
                {
                    System.out.println("There are no lyrics for " + currentSong.getTitle());
                }
            }
            else
            {
                System.out.println("Unable to print song lyrics");
            }

    }

    /***/
    public void printAllSongs(){
        ArrayList<Song> allSongs = songManager.getAllSongs();
        printSongs(allSongs);
    }
    private void printSongs(ArrayList<Song> songs)
    {
        if(songs == null || songs.size()==0)
        {
            System.out.println("No songs available");
        }
        else
        {
            System.out.println(">> Songs List <<");
            int i = 0;
            for (Song song:songs)
            {
                System.out.println((i+1)+" "+song.getTitle());
                i++;
            }
        }
    }
    public void printSongs(String keyword)
    {
        ArrayList<Song> songsFound = songManager.getSongs(keyword);
        printSongs(songsFound);
    }

    public void seeLyricsFromList()
    {
        Song song = getSongFromList();
        if(song!=null)
        {
            printLyrics(song);
        }
    }
    private Song getSongFromList()
    {
        Song result = null;
        ArrayList<Song> songs = songManager.getAllSongs();
        printSongs(songs);
        if(songs.size()>0)
        {
            System.out.println("Enter song number ");
            String inputLine = scanner.nextLine();
            int id = Utils.parseOption(inputLine);
            if(id<1 || id>songs.size())
            {
                System.out.println("Please enter a valid song number");
            }
            else
            {
               result = songManager.getASong(id);
            }
        }
        return result;
    }
    private Song getSongByKeyword(String keyword)
    {
        ArrayList<Song> foundSongs = songManager.getSongs(keyword);
        Song song = null;
        printSongs(foundSongs);
        if(foundSongs!=null && foundSongs.size()>0)
        {
            System.out.println("Enter song number ");
            String inputLine = scanner.nextLine();
            int id = Utils.parseOption(inputLine);
            if(id>0 && id<=foundSongs.size())
            {
                id--;
                song = foundSongs.get(id);
            }
            else
            {
                System.out.println("Please enter a valid song number");
            }
        }
        else
        {
            System.out.println("No songs found with title that contains "+keyword);
        }
        return song;
    }
    public void seeLyricsFromKeyWord(String keyword)
    {
        Song song = getSongByKeyword(keyword);
        if(song!=null)
        {
            printLyrics(song);
        }
    }
    private String getEmotionInput()
    {
        String emotion = null;
        ArrayList<Emotion> emotions = emotionManager.getEmotions();
        if(emotions!=null && emotions.size()>0)
        {
            for (int i = 0; i < emotions.size(); i++) {
                System.out.println((i + 1) +" " + emotions.get(i));
            }
            System.out.println("Enter emotion number ");
            String inputLine = scanner.nextLine();
            int number = Utils.parseOption(inputLine);
            if(number>0 && number<=emotions.size())
            {
                number--;
                emotion = emotions.get(number).getEmotion();
            }
            else
            {
                System.out.println("Please enter a valid emotion number");
            }
        }
        else
        {
            System.out.println("No emotions stored in the system");
        }
        return emotion;
    }
    public void findSongFromEmotion()
    {
        String emotion = getEmotionInput();
        ArrayList<Song> foundSongs = songEmotions.getSongsFromEmotion(emotion);
        if(foundSongs!=null && foundSongs.size()>0)
        {
            printSongs(foundSongs);
        }
        else
            {
                if(emotion!=null)
                {
                    System.out.println("No songs found for emotion "+emotion);
                }
            }

    }
    public boolean removeFromCategory()
    {
        boolean bOk = false;
        String emotion = getEmotionInput();
        if(emotion!=null)
        {
           ArrayList<Song> songs = songEmotions.getSongsFromEmotion(emotion);
           printSongs(songs);
            if(songs!=null && songs.size()>0)
            {
                System.out.println("Enter song number ");
                String inputLine = scanner.nextLine();
                int number = Utils.parseOption(inputLine);
                if(number>0 && number<=songs.size())
                {
                    number--;
                    System.out.println(emotion);
                    bOk = songEmotions.removeFromCategory(songs.get(number),emotion);
                }
                else
                {
                    System.out.println("Please enter a valid song number");
                }
            }
        }
        return bOk;
    }

    public boolean addEmotion(String emotion,ArrayList<String> words)
    {
        Emotion newEmotion = new Emotion(emotion.trim().toLowerCase(),words);
       boolean succeed = emotionManager.addEmotion(newEmotion);
       songEmotions.sync(emotion,words,songManager.getAllSongs());
       return  succeed;
    }

    public boolean terminate()
    {
        scanner.close();
        boolean emotionsWritten = emotionManager.writeEmotions();
        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
