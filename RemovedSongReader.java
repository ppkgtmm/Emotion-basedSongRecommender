import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RemovedSongReader extends TextFileReader
{
    private String currentEmotion = null;
    private ArrayList<String> currentSongs = null;

    public boolean findAndSetEmotion(String line)
    {
        boolean found = false;
        Pattern emotionPattern = Pattern.compile("(.*?) : \\[");
        Matcher emotionMatch = emotionPattern.matcher(line);
        if (emotionMatch.find())
        {
            currentEmotion = emotionMatch.group(1);
            found = true;
            //System.out.println("findAndSetEmotion " + currentEmotion);
        }
        return found;
    }

    public SongEmotions readRemovedSong()
    {
        SongEmotions newSongEmotion = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                boolean foundEmotion = findAndSetEmotion(line);
                //System.out.println("line1: " + line);
                if(foundEmotion)
                {
                    currentSongs = new ArrayList<>();
                    //System.out.println("line2: " + line);
                    while((line = getNextLine())!=null)
                    {
                        //System.out.println("line3: " + line);
                        Pattern lastSongPattern = Pattern.compile("(.*?) \\]");
                        Matcher lastSongMatch = lastSongPattern.matcher(line);
                        if(lastSongMatch.find())
                        {
                            //System.out.println("line4: " + lastSongMatch.group(1));
                            currentSongs.add(lastSongMatch.group(1));
                            newSongEmotion = new SongEmotions(currentEmotion,currentSongs);
                            break;
                        }
                        else
                        {
                            //System.out.println("line5: " + line);
                            currentSongs.add(line);
                        }
                    }
                }
            }
        }while( line!=null && newSongEmotion == null);
        return newSongEmotion;
    }

    public static void main(String[] args)
    {
        RemovedSongReader reader;
        String fileName = "removed.txt";
        reader = new RemovedSongReader();
        if (!reader.open(fileName))
        {
            System.out.println("Error opening song file " + fileName);
            System.exit(1);
        }
        SongEmotions nextSongEmotion = null;
        while ((nextSongEmotion = reader.readRemovedSong()) != null)
        {
            System.out.println("nextSongEmotion: " + nextSongEmotion);
        }
    }
}