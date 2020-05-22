import java.util.ArrayList;

public class Display
{

    /**
     * Print songs in the song list provided
     * @param songs list of songs to print
     */
    public static void printSongs(ArrayList<Song> songs)
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

    public static void printAllEmotions(ArrayList<Emotion> allEmotions)
    {
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
    public static void printLyrics(Song currentSong)
    {
            if(currentSong!=null)
            {
                ArrayList<String> currentLyrics = currentSong.getLyrics();
                /* song has lyrics */
                if(currentLyrics!=null && currentLyrics.size()>0)
                {
                    System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                    for (String currentPart : currentLyrics) {
                        System.out.println(currentPart);
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
}
