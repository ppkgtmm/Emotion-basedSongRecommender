import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmotionReader extends TextFileReader
{
    private String currentEmotion = null;
    private ArrayList<String> currentWords = null;

    private boolean findAndSetEmotion(String line)
    {
        boolean found = false;
        if(line.charAt(line.length()-1) == '[')
        {
            int colonIndex = line.indexOf(":");
            if(colonIndex!=-1)
            {
                currentEmotion = line.substring(0,colonIndex).trim();
//                System.out.println("emotion:"+currentEmotion);
                found = true;
            }
        }
        return found;
    }
    private boolean isLastWordOfEmotion(String line)
    {
        boolean isLast = false;
        int braceIndex = line.lastIndexOf("]");
        if(braceIndex!= -1 && braceIndex==line.length()-1)
        {
            String lastWord = line.substring(0,braceIndex).trim();
            if(!lastWord.isEmpty())
            {
                currentWords.add(lastWord);
//                System.out.println("last word:"+lastWord);
            }
            isLast = true;
        }
        return isLast;
    }


    public Emotion readEmotions()
    {
        Emotion newEmotion = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                line = line.trim();
                if (!isEmptyLine(line))
                {
                    boolean foundEmotion = findAndSetEmotion(line);
                    if (foundEmotion)
                    {
                        currentWords = new ArrayList<>();
                        while ((line = getNextLine()) != null)
                        {
                            line = line.trim();
                            if (!isEmptyLine(line))
                            {
                                boolean lastWordMatch = isLastWordOfEmotion(line);
                                if (lastWordMatch)
                                {
                                    newEmotion = new Emotion(currentEmotion,currentWords);
                                    break;
                                }
                                else
                                {
//                                    System.out.println("Middle word: " + line);
                                    currentWords.add(line);
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
        Emotion nextEmotion = null;
        while ((nextEmotion = reader.readEmotions()) != null)
        {
            System.out.println("nextEmotion: " + nextEmotion.getEmotion());
        }
    }
}