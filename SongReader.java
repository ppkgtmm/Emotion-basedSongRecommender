/**
 * Class to read info about songs from a file
 * and create Song object.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import java.util.ArrayList;

public class SongReader extends TextFileReader
{
    /** current song title which have been read from file */
    private String currentSong = null;
    /** lyrics of current song that have been read */
    private ArrayList<String> currentLyrics = null;

    /**
     * check if the line contains title and set current song title if found.
     * @param line current line
     * @return true if successful, false if the line does not have song title
     */
    private boolean findAndSetTitle(String line)
    {
        boolean found = false;
        String titlePattern = "Song : ";
        int startIndex = line.indexOf(titlePattern);
        /* line contains title */
        if(startIndex!=-1)
        {
            /* getting title from line */
            String title = line.substring(startIndex + titlePattern.length());
            if (!title.isEmpty())
            {
                currentSong = title;
                found = true;
            }
        }
        return found;
    }

    /*
     * read song from song file and create song object if
     * completed reading song.
     * @return  song object created or null if end of file
     * is reached
     */
    public Song readSong()
    {
        Song newSong = null;
        String line = null;
        String lyricsPattern = "Lyrics :";
        do {
            line = getNextLine();
            if (line!=null)
            {
                /* eliminates leading and trailing spaces of a line */
                line = line.trim();
                if(!line.isEmpty())
                {
                    /* find and set song title */
                    boolean foundTitle = findAndSetTitle(line);
                    if(foundTitle)
                    {
                        /* instantiate ArrayList when new song is read */
                        currentLyrics = new ArrayList<>();
                        while((line = getNextLine())!=null)
                        {
                            /* eliminates leading and trailing spaces of a line */
                            line = line.trim();
                            if(!line.isEmpty())
                            {
                                /* not line between title and lyrics */
                                if(!line.contains(lyricsPattern))
                                {
                                    /* end of song */
                                    if(line.compareToIgnoreCase("=ENDSONG=")==0)
                                    {
                                        newSong = new Song(currentSong,currentLyrics);
                                        break;
                                    }
                                    currentLyrics.add(line);
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Bad line "+line+" ==> skipping");
                    }
                }
            }

        }while(line!=null && newSong == null);
        return newSong;
    }

}