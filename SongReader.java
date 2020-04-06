package Emotion

import java.util.ArrayList;

public class SongReader extends TextFileReader
{
    private boolean isLyric = false;
    private String currentSong = null;
    private ArrayList<String> currentLyrics = null;

    public boolean findAndSetTitle(String line)
    {
        boolean found = false;
        String titlePattern = "Song : ";
        int startIndex = line.indexOf(titlePattern);
        if(startIndex!=-1)
        {
            String title = line.substring(startIndex + titlePattern.length());
            if (!title.isEmpty())
            {
                currentSong = title;
                found = true;
            }
        }
        return found;
    }

    public Song readSong()
    {
        Song newSong = null;
        String line = null;
        String lyricsPattern = "Lyrics :";
        do {
            line = getNextLine();
            if (line!=null)
            {
                boolean foundTitle = findAndSetTitle(line);
                if(foundTitle)
                {
                    currentLyrics = new ArrayList<>();
                    while((line = getNextLine())!=null)
                    {
                        if(!line.contains(lyricsPattern))
                        {
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