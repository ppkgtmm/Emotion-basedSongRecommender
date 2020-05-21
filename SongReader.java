/**
 * Class to read info about songs from a file
 * and create songs.
 *
 * Each line of the file has the following structure
 *
 *  First field is song - lines that have "Song : " before a song
 *  Second field is the lyrics - lines which have "Lyrics :" before Lyrics
 *  and end up with "=ENDSONG="
 *
*  Created by Pinky Gautam , Thitiporn Sukpartcharoen, 19 May 2020
 */
import java.util.ArrayList;

public class SongReader extends TextFileReader
{
    /** a current song */
    private String currentSong = null;

    /** collection of current song lyrics */
    private ArrayList<String> currentLyrics = null;

    /** find song title and set current song title to collect lyrics
     * by comparing to song title pattern
     * @param   line    inputting line
     * @return true if found, false if it did not found song title
     */
    public boolean findAndSetTitle(String line)
    {
        boolean found = false;
        String titlePattern = "Song : ";
        int startIndex = line.indexOf(titlePattern);    /* find is the song pattern */
        if(startIndex!=-1)
        {
            String title = line.substring(startIndex + titlePattern.length());  /* get a song title */
            /* set a current song */
            if (!title.isEmpty())
            {
                currentSong = title;
                found = true;
            }
        }
        return found;
    }

    /**
     * This method reads a line (if necessary)
     * then creates Song. It automatically
     * handles the field indicating multiple song.
     * @return  Song    new song from reading a song text file
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
                line = line.trim();
                if(!isEmptyLine(line))
                {
                    boolean foundTitle = findAndSetTitle(line);
                    if(foundTitle)
                    {
                        currentLyrics = new ArrayList<>();
                        /** read, get and add song lyrics */
                        while((line = getNextLine())!=null)
                        {
                            line = line.trim();
                            if(!isEmptyLine(line))
                            {
                                /** find song lyrics pattern */
                                if(!line.contains(lyricsPattern))
                                {
                                    if(line.compareToIgnoreCase("=ENDSONG=")==0)
                                    {
                                        /** create new son */
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
                        //System.out.println("Bad line "+line+" ==> skipping");
                    }
                }
            }

        }while(line!=null && newSong == null);
        return newSong;
    }



    public static void main(String[] args)
    {
        SongReader reader = new SongReader();
        if (!reader.open("songsShort.txt"))
	    {
            System.out.println("Error opening song file");
            System.exit(1);
        }
        Song nextSong = null;
        while ((nextSong = reader.readSong()) != null)
	    {
            System.out.println("Successfully added " + nextSong.getTitle());
            for (String lyric:nextSong.getLyrics())
            {
                System.out.println(lyric);
            }
	    }
    }
}