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
            if(line.charAt(line.length()-1) == '[')
            {
                int colonIndex = line.indexOf(":");
                if(colonIndex!=-1)
                {
                    currentEmotion = line.substring(0,colonIndex).trim();
                    System.out.println("emotion:"+currentEmotion);
                    found = true;
                }
            }
    return found;
    }

    public boolean isLastSongOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            String lastSong = line.substring(0,braceIndex).trim();
            if(!lastSong.isEmpty())
            {
                currentSongs.add(lastSong);
                System.out.println("last song:"+lastSong);
            }
            isLast = true;
        }
        return isLast;
    }

    public RemovedSongs readRemovedSong()
    {
        RemovedSongs removedSongs = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                line = line.trim();
                boolean foundEmotion = findAndSetEmotion(line);
                if(foundEmotion)
                {
                    currentSongs = new ArrayList<>();
                    do {
                        line = getNextLine();
                        if(line!=null)
                        {
                            line = line.trim();
                            boolean lastSong = isLastSongOfEmotion(line);
                            if(lastSong)
                            {
                                removedSongs = new RemovedSongs(currentEmotion,currentSongs);
                                break;
                            }
                            else
                            {
                                System.out.println("middle song: "+line);
                                currentSongs.add(line);
                            }
                        }
                    }while(line!=null);
                }
                else
                {
                    System.out.println("Bad line "+line+" ==> skipping");
                }
            }
        }while( line!=null && removedSongs == null);
        return removedSongs;
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
        RemovedSongs removedSongs;
        while ((removedSongs = reader.readRemovedSong()) != null)
        {
            System.out.println("obj created "+removedSongs.getEmotion());
        }
    }
}