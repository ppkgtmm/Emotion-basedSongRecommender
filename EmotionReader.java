import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmotionReader extends TextFileReader
{
    private String currentEmotion = null;
    private ArrayList<String> currentWords = null;

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
    public boolean isLastWordOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            String lastWord = line.substring(0,braceIndex).trim();
            if(!lastWord.isEmpty())
            {
                currentWords.add(lastWord);
                System.out.println("last word:"+lastWord);
            }
            isLast = true;
        }
        return isLast;
    }

    public Emotions readEmotions()
    {
        Emotions newEmotion = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                line = line.trim();
                boolean foundEmotion = findAndSetEmotion(line);
                if(foundEmotion)
                {
                    currentWords = new ArrayList<>();
                    while((line = getNextLine())!=null)
                    {
                        line = line.trim();
                        boolean lastWordMatch = isLastWordOfEmotion(line);
                        if(lastWordMatch)
                        {
                            // call emotion manager
                            break;
                        }
                        else if(line.length()== 0)
                        {
                            System.out.println("Middle word NULL ");
                        }
                        else
                        {
                                System.out.println("Middle word: "+line);
                                currentWords.add(line);
                        }
                    }
                }
                else
                {
                    System.out.println("Bad line "+line+" ==> skipping");
                }
            }
            else
                System.out.println("Nulll " + line);
        //}while( line!=null && newEmotion == null);
        }while( line!=null && newEmotion == null);
        return newEmotion;
    }

    public static void main(String[] args)
    {
        EmotionReader reader;
        String fileName = "emotions.txt";
        reader = new EmotionReader();
        if (!reader.open(fileName))
        {
            System.out.println("Error opening song file " + fileName);
            System.exit(1);
        }
        Emotions nextEmotion = null;
        while ((nextEmotion = reader.readEmotions()) != null)
        {
            System.out.println("nextEmotion: " + nextEmotion);
        }
    }
}