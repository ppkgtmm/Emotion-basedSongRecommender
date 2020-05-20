import java.util.ArrayList;
import java.util.Scanner;

public class Facilitator
{
    private SongManager songManager=null;
    private SongEmotions songEmotions = null;
    private EmotionManager emotionManager = null;
    private static Facilitator facilitator = null;
    private Scanner scanner = new Scanner(System.in);

    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }
    public static Facilitator getInstance()
    {
        if (facilitator==null)
        {
            facilitator = new Facilitator();
        }
        return facilitator;
    }

    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        boolean songOK = songManager.readSongs(songFileName);
        boolean removedSongsOK;
        boolean ok = false;
        if(!removedSongsFile.isEmpty())
        {
            removedSongsOK = syncDeletedSongs(removedSongsFile);
            if(songOK && emotionOK && removedSongsOK)
            {
                doSync();
                ok = true;
            }
        }
        else if(songOK && emotionOK)
        {
         doSync();
         ok = true;
        }
        return ok;
    }
    public void doSync()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
        ArrayList<Song> allSongs = songManager.getAllSongs();
        for(String emotion:allEmotions)
        {
            songEmotions.sync(emotion,emotionManager.getEmotionWords(emotion),allSongs);
        }
    }
    private boolean syncDeletedSongs(String fileName)
    {
        return songEmotions.initialize(fileName);
    }

    public void printAllEmotions()
    {
        ArrayList<String> allEmotions = emotionManager.getEmotions();
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
    public void printLyrics(Song currentSong)
    {
            if(currentSong!=null)
            {
                ArrayList<String> currentLyrics = currentSong.getLyrics();
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
                System.out.println("No song found with the id");
            }

    }
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
//        if(songsFound==null || songsFound.size()==0)
//        {
//            System.out.println("No songs found with title that contains "+keyword);
//        }
//        else
//        {
//            System.out.println(">> Song with Keyword List <<");
//            for (int counter = 0; counter < songsFound.size(); counter++)
//             {
//                 Song currentSong = songsFound.get(counter);
//                 System.out.println((counter+1) +" " + currentSong.getTitle());
//             }
//        }
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
//        int i = 0;
//        for (Song song:songs)
//        {
//            System.out.println((i+1)+" "+song.getTitle());
//            i++;
//        }
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
//            for (int i=0;i<foundSongs.size();i++)
//            {
//                System.out.println((i+1)+" "+foundSongs.get(i).getTitle());
//            }
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
        ArrayList<String> emotions = emotionManager.getEmotions();
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
    public void findSongFromEmotion()
    {
        String emotion = getEmotionInput();
        ArrayList<Song> foundSongs = songEmotions.getSongsFromEmotion(emotion);
        if(foundSongs!=null && foundSongs.size()>0)
        {
//            for (Song song:foundSongs )
//            {
//                System.out.println(song.getId()+" "+song.getTitle());
//            }
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
       boolean succeed = emotionManager.addEmotion(emotion.trim().toLowerCase(),words);
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
