import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
/** Count multiple songs of the same type */

public class SongReader extends TextFileReader
{
    private boolean isEnd = false;
    private boolean isLyric = false;
    private String[] currentSong = null;
    private ArrayList<String> currentLyrics = new ArrayList<String>();

    public Song readerEmotion()
    {
        Song newSong = null;
        if (!isEnd)
        {
            String line = null;
            do
            {
                line = getNextLine();
                String arrayLine[] = line.split(" ");
                if (arrayLine[0].equalsIgnoreCase("Song"))
                {
                    Pattern pSong = Pattern.compile("Song :");
                    currentSong = pSong.split(line);
                    System.out.println("SONG -"+ currentSong[1]);
                    isLyric=false;
                }
                else if(arrayLine[0].equalsIgnoreCase("Lyrics"))
                {
                    System.out.println("Find lyric");
                    isLyric = true;
                }
                else if(line.equalsIgnoreCase("=ENDSONG="))
                {
                    // System.out.println("===== "+currentSong[1]);
                    // System.out.println("----- "+ currentLyrics);
                    newSong = new Song(currentSong[1],currentLyrics);
                    return newSong;
                }
                else if(line.equalsIgnoreCase("=END="))
                {
                    isEnd = true;
                    isLyric = false;
                    System.out.println("End");
                    return null;
                }
                else if(isLyric)
                {
                    currentLyrics.add(line);
                    //System.out.println("ADDed " + line);
                }
                else
                {
                    System.out.println("ERROR - Unknow pattern in songsFile");
                    return null;
                }
            }while (!isEnd);
        }
        return null;
    }

    // private boolean parseSong(String line)
    // {
    //     Pattern patternSong = Pattern.compile("Song :");
    //     return true;
    // }

    public static void main(String[] args)
    {
        SongReader reader = new SongReader();
        if (!reader.open("songsShort.txt"))
	    {
            System.out.println("Error opening tile file");
            System.exit(0);
        }
        Song nextSong = null;
        while ((nextSong = reader.readerEmotion()) != null)
	    {
            System.out.println("Successfuly added " + nextSong.getTitle());
	    }
    }
}