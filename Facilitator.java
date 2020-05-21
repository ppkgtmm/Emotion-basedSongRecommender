/**
 *  Facilitator.java
 *
 *  This class represents a front-facing interface which
 *  assemble all manager class and organize to interface.
 *
 *  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Facilitator
{
    private static Facilitator facilitator = null;

    /* song manager */
    private SongManager songManager=null;

    /* song emotion manager */
    private SongEmotions songEmotions = null;

    /* emotion manager */
    private EmotionManager emotionManager = null;

    /* getting user input line */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Contructor class of facilitator
     * to get the instance of song, songEmotion and emotion
     */
    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }

    /**
     * get facilitator instance class to get
     * an static instactance
     * @return facilitator current facilitator
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
     * doSetting method to set song, emotion and
     * removed song files.
     * @return true if successful, false if cannot read the files
     */
    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        boolean songOK = songManager.readSongs(songFileName);
        boolean removedSongsOK;
        boolean ok = false;
        /* check is it has removed song */
        if(!removedSongsFile.isEmpty())
        {
            removedSongsOK = syncDeletedSongs(removedSongsFile);
            if(songOK && emotionOK && removedSongsOK)
            {
                doSync();   /* set song and emotion from read files */
                ok = true;
            }
        }
        else if(songOK && emotionOK)
        {
         doSync();  /* set song and emotion from read files */
         ok = true;
        }
        return ok;
    }

    /**
     * doSync method to set and update emotion and song to instace.
     */
    public void doSync()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        /* set emotion */
        for(String emotion:allEmotions)
        {
            songEmotions.sync(emotion,emotionManager.getEmotionWords(emotion),allSongs);
        }
    }

    /**
     * syncDeletedSongs method to set removed song file
     * @param fileName removed text file
     */
    private boolean syncDeletedSongs(String fileName)
    {
        return songEmotions.initialize(fileName);
    }

    /**
     * printAllEmotion method to display all emotions
     */
    public void printAllEmotions()
    {
        /* get emotion */
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        /* check is it has emotion */
        if(allEmotions==null || allEmotions.size()==0)
        {
            System.out.println("There are no emotions stored in the system");
        }
        else
        {
            System.out.println(">> Emotion List <<");
            for (int counter = 0; counter < allEmotions.size(); counter++)
            {
                System.out.println((counter+1)+ " " + allEmotions.get(counter));
            }
        }
    }

    /**
     * printLyrics method to display lyrics of current song
     * @param currentSong song to display lyrics
     */
    public void printLyrics(Song currentSong)
    {
            /* check is it has current song */
            if(currentSong!=null)
            {
                ArrayList<String> currentLyrics = currentSong.getLyrics();  /* get lyrics of current song */
                /* check is it has lyrics */
                if(currentLyrics!=null && currentLyrics.size()>0)
                {
                    System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                    /* display lyrics */
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
                System.out.println("No song found with the id");
            }

    }

    /**
     * printAllSongs method to display all songs
     */
    public void printAllSongs(){
        ArrayList<Song> allSongs = songManager.getAllSongs();
        printSongs(allSongs);
    }

    /**
     * printSongs method to display all specific songs
     * @param songs selected song to display
     */
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

    /**
     * printSongs method to display all song by specific keyword
     * @param keyword   input keyword
     */
    public void printSongs(String keyword)
    {
        ArrayList<Song> songsFound = songManager.getSongs(keyword);     /* get songs which have specific keyword */
        printSongs(songsFound);
    }

    /**
     * printSongs method to get song to lyrics and display
     */
    public void seeLyricsFromList()
    {
        Song song = getSongFromList();
        if(song!=null)
        {
            printLyrics(song);
        }
    }

    /**
     * getSongFromList method to choose a song number 
     * and get id of selected song
     * @return result an selected song
     */
    private Song getSongFromList()
    {
        Song result = null;
        ArrayList<Song> songs = songManager.getAllSongs();
        printSongs(songs);
        if(songs.size()>0)
        {
            System.out.println("Enter song number ");
            String inputLine = scanner.nextLine();
            int id = Utils.parseOption(inputLine);  /* get an input song number */
            /* check is it has this song number */
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

    /**
     * getSongByKeyword method to inputed keyword
     * and get id of song which has the keyword
     * @param keyword inputed keyword to find songs
     * @return result songs by selected keyword
     */
    private Song getSongByKeyword(String keyword)
    {
        ArrayList<Song> foundSongs = songManager.getSongs(keyword);     /* get found songs from keyword */
        Song song = null;
        printSongs(foundSongs);
        /* select a found song */
        if(foundSongs!=null && foundSongs.size()>0)
        {
            System.out.println("Enter song number ");
            String inputLine = scanner.nextLine();
            int id = Utils.parseOption(inputLine);                      /* get song id */
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

    /**
     * seeLyricsFromKeyword method to display a song
     * from inputed keyword.
     * @param keyword inputed keyword to find songs
     */
    public void seeLyricsFromKeyWord(String keyword)
    {
        Song song = getSongByKeyword(keyword);
        if(song!=null)
        {
            printLyrics(song);
        }
    }

    /**
     * getEmotionInput method to get inputting emotion from user
     * @return emotion selected emotion
     */
    private String getEmotionInput()
    {
        String emotion = null;
        ArrayList<String> emotions = emotionManager.getEmotions();
        if(emotions!=null && emotions.size()>0)
        {
<<<<<<< HEAD
            for (int i = 0; i < emotions.size(); i++) {
                System.out.println((i + 1) +" " + emotions.get(i));
=======
            for (int i = 0; i < emotions.size(); i++) 
            {
                System.out.println((i + 1)  + emotions.get(i));
>>>>>>> 716e99eff17052c566e5dae8b6add41cc3bd5804
            }
            /* get inputting emotion */
            System.out.println("Enter emotion number ");
            String inputLine = scanner.nextLine();
            int number = Utils.parseOption(inputLine);
            if(number>0 && number<=emotions.size())
            {
                number--;
                emotion = emotions.get(number);
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

    /**
     * findSongFromEmotion method to display songs from selected emotion
     */
    public void findSongFromEmotion()
    {
        String emotion = getEmotionInput();
        ArrayList<Song> foundSongs = songEmotions.getSongsFromEmotion(emotion);     /* get songs from selected emotion */
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

    /**
     * remove song from category by inputting emotion and 
     * select song to remove from each emotion
     * @return true if successful, false if cannot remove song
     */
    public boolean removeFromCategory()
    {
        boolean bOk = false;
        String emotion = getEmotionInput();     /* get emotion to remove song */
        if(emotion!=null)
        {
           ArrayList<Song> songs = songEmotions.getSongsFromEmotion(emotion);   /* get song from selected emotion */
           printSongs(songs);
           /* get a song to remove from selected emotion */
            if(songs!=null && songs.size()>0)
            {
                System.out.println("Enter song number ");
                String inputLine = scanner.nextLine();
                int number = Utils.parseOption(inputLine);
                /* remove song */
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

    /**
     * add new emotion and word of the emotion in collection
     * @param   emotion current emotion
     * @param   words   arraylist words to add
     * @return true if successful, false if cannot add new emotion
     */
    public boolean addEmotion(String emotion,ArrayList<String> words)
    {
<<<<<<< HEAD
       boolean succeed = emotionManager.addEmotion(emotion.trim().toLowerCase(),words);
       songEmotions.sync(emotion,words,songManager.getAllSongs());
       return  succeed;
=======
        boolean bOk = false;
        /* set emotion to legal and words with having to legal*/
        if(emotionManager.addEmotion(emotion.trim().toLowerCase(),words))
        {
            bOk = true;
            doSync();
        }
        return bOk;
>>>>>>> 716e99eff17052c566e5dae8b6add41cc3bd5804
    }

    /**
     * write new emotion and removed song from emotion to text file
     * @return true if successful, false if cannot write both file
     */
    public boolean terminate()
    {
        scanner.close();
        boolean emotionsWritten = emotionManager.writeEmotions();
        boolean songsRemovedOk = songEmotions.writeRemovedSongs();
        return emotionsWritten && songsRemovedOk;
    }
}
