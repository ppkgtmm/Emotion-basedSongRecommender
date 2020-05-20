/**
 * Class to read info about removes songs from a file
 * and create removedSong.
 *
 * Each line of the file has the following structure
 *
 *  First field is emotion - lines that have colon after emotion
 *  Second field is the songs - lines which are in the bracket after emotion
 *
*  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

public class RemovedSongReader extends TextFileReader
{
    /* emotions which have removed song */
    private String currentEmotion = null;

    /* removed songs of current emotion */
    private ArrayList<String> currentSongs = null;

    /**
     * check is it the emotion line by checking is it has colon
     * @return true if successful, false if the line is not emotion
     */
    public boolean findAndSetEmotion(String line)
    {
        boolean found = false;
            /* check is it have emotion */
            if(line.charAt(line.length()-1) == '[')     /* find out the colon (emotion symbol)*/
            {
                int colonIndex = line.indexOf(":");
                /* get the emotion */
                if(colonIndex!=-1)
                {
                    currentEmotion = line.substring(0,colonIndex).trim();
                    //System.out.println("emotion:"+currentEmotion);
                    found = true;
                }
            }
    return found;
    }

    /**
     * find emotion in text file and set emotion to currentEmotion
     * @param  line   current line
     * @return true if successful, false if cannot find emotion
     */
    public boolean isLastSongOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        /* check is it have emotion */
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            String lastSong = line.substring(0,braceIndex).trim();
            if(!lastSong.isEmpty())
            {
                currentSongs.add(lastSong);
                //System.out.println("last song:"+lastSong);
            }
            isLast = true;
        }
        return isLast;
    }

    /**
     * This method reads a line (if necessary)
     * then creates removedSong. It automatically
     * handles the field indicating multiple emotion and removed.
     * @param  line   current line
     * @return removedSong
     */
    public RemovedSongs readRemovedSong()
    {
        RemovedSongs removedSongs = null;
        String line = null;
        /* */
        do {
            line = getNextLine();
            if (line!=null)
            {
                line = line.trim();
                if(!isEmptyLine(line))
                {
                    boolean foundEmotion = findAndSetEmotion(line);
                    if(foundEmotion)
                    {
                        currentSongs = new ArrayList<>();
                        do {
                            line = getNextLine();
                            if(line!=null)
                            {
                                line = line.trim();
                                if(!isEmptyLine(line))
                                {
                                    boolean lastSong = isLastSongOfEmotion(line);
                                    /* now create and return RemoveSong from current emotion and songs */
                                    if(lastSong)
                                    {
                                        removedSongs = new RemovedSongs(currentEmotion,currentSongs);
                                        break;
                                    }
                                    else
                                    {
                                        //System.out.println("middle song: "+ line);
                                        currentSongs.add(line);
                                    }
                                }
                            }
                        }while(line!=null);
                    }
                    else
                    {
                        //System.out.println("Bad line "+line+" ==> skipping");
                    }
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