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

    /**
     * Get all songs from song manager and call function to
     * print the songs.
     */
    public void printAllSongs(){
        ArrayList<Song> allSongs = songManager.getAllSongs();
        printSongs(allSongs);
    }

    /**
     * Print songs in the song list provided
     * @param songs list of songs to print
     */
    private void printSongs(ArrayList<Song> songs)
    {
        /* list does not have any songs */
        if(songs == null || songs.size()==0)
        {
            System.out.println("No songs available");
        }
        /* list contains song(s) */
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

    public void seeLyricsFromList(int choice)
    {
        Song song = getSongFromChoice(choice);
        if(song!=null)
        {
            printLyrics(song);
        }
    }
    private Song getSongFromChoice(int choice)
    {
        Song result = null;
        ArrayList<Song> songs = songManager.getAllSongs();
        if(songs.size()>0)
        {
            if(choice<1 || choice>songs.size())
            {
                System.out.println("Please enter a valid song number");
            }
            else
            {
               result = songManager.getASong(choice);
            }
        }
        return result;
    }

    public void seeLyricsFromKeyWord(int selectedChoice,String keyword)
    {
        ArrayList<Song> foundSongs = songManager.getSongs(keyword);
        Song song = null;
        if(selectedChoice>0 && selectedChoice<=foundSongs.size())
            {
                selectedChoice--;
                song = foundSongs.get(selectedChoice);
                printLyrics(song);
            }
            else
            {
                System.out.println("Please enter a valid song number");
            }

    }
    private String getEmotion(int selectedChoice)
    {
        String emotion = null;
        ArrayList<Emotion> emotions = emotionManager.getEmotions();
        if(emotions!=null && emotions.size()>0)
        {
            selectedChoice--;
            if(selectedChoice>=0 && selectedChoice<emotions.size())
            {
                    emotion = emotions.get(selectedChoice).getEmotion();
            }
            else
            {
                System.out.println("Emotion number entered is wrong");
            }

        }
        return emotion;
    }
    public boolean findSongFromEmotion(int selectedChoice)
    {
        String emotion = getEmotion(selectedChoice);
        ArrayList<Song> foundSongs = songEmotions.getSongsFromEmotion(emotion);
        if(foundSongs!=null && foundSongs.size()>0)
        {
            printSongs(foundSongs);
            return true;
        }
        else
            {
                if(emotion!=null)
                {
                    System.out.println("No songs found for emotion "+emotion);
                }
                return false;
            }

    }
    public boolean removeFromCategory(int selectedChoice)
    {
        boolean bOk = false;
        String emotion = getEmotion(selectedChoice);
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

    public boolean addEmotion(Emotion newEmotion)
    {
       boolean succeed = emotionManager.addEmotion(newEmotion);
       songEmotions.sync(newEmotion.getEmotion(),newEmotion.getWords(),songManager.getAllSongs());
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
