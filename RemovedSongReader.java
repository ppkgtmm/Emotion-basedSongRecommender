/**
 * Class to read info about removed songs from a file
 * and create RemovedSong object.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;

public class RemovedSongReader extends TextFileReader
{
    /* current emotion which have songs removed read from file */
    private String currentEmotion = null;
    /* songs removed from current emotion */
    private ArrayList<String> currentSongs = null;

    /**
     * check if the line contains emotion and set current emotion if found.
     * @param line current line
     * @return true if successful, false if the line does not have emotion
     */
    private boolean findAndSetEmotion(String line)
    {
        boolean found = false;
        /* trying to get emotion from file to set */
            if(line.charAt(line.length()-1) == '[')
            {
                int colonIndex = line.indexOf(":");
                if(colonIndex!=-1)
                {
                    /* line contains emotion */
                    currentEmotion = line.substring(0,colonIndex).trim(); /* get the emotion and set*/
                    found = true;
                }
            }
    return found;
    }

    /**
     * check if it is the last song of current emotion
     * in text file and add the song title to collection
     * @param  line   current line
     * @return true if successful, false if the line is not the
     * last song of current emotion.
     *
     */
    private boolean isLastSongOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        /* check if it is last song */
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            /* getting last song */
            String lastSong = line.substring(0,braceIndex).trim();
            if(!lastSong.isEmpty())
            {
                currentSongs.add(lastSong);
            }
            isLast = true;
        }
        return isLast;
    }

    /**
     * read a removed song text file, if possible. It will read and
     * create removed song object from emotion and songs removed from
     * the emotion.
     * @return removedSong object if reading about an emotion and
     * song removed from the emotion are completed or null if end of
     * file reached.
     */
    public RemovedSongs readRemovedSong()
    {
        RemovedSongs removedSongs = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                /* eliminates leading and trailing spaces of a line */
                line = line.trim();
                if(!line.isEmpty())
               {
                   /* find and set current emotion */
                   boolean foundEmotion = findAndSetEmotion(line);
                   if(foundEmotion)
                   {
                       /* instantiate to collect songs removed from emotions separately*/
                       currentSongs = new ArrayList<>();
                       /* collect songs removed from emotion */
                       do {
                           line = getNextLine();
                           if(line!=null)
                           {
                               /* eliminates leading and trailing spaces of a line */
                               line = line.trim();
                               if(!line.isEmpty())
                               {
                                   /* check is it the last song */
                                   boolean lastSong = isLastSongOfEmotion(line);
                                   if(lastSong)
                                   {
                                       removedSongs = new RemovedSongs(currentEmotion,currentSongs);
                                       break;
                                   }
                                   else
                                   {
                                       currentSongs.add(line);
                                   }
                               }
                           }
                       }while(line!=null);
                   }
                   else
                   {
                       System.out.println("Bad line "+line+" ==> skipping");
                   }
               }
            }
        }while( line!=null && removedSongs == null);
        return removedSongs;
    }

}