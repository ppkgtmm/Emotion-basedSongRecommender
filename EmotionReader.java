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

    public Emotions readEmotions()
    {
        Emotions newEmotion = null;
        String line = null;
        do {
            line = getNextLine();
            if (line!=null)
            {
                boolean foundEmotion = findAndSetEmotion(line);
                //System.out.println("line1: " + line);
                if(foundEmotion)
                {
                    currentWords = new ArrayList<>();
                    //System.out.println("line2: " + line);
                    while((line = getNextLine())!=null)
                    {
                        //System.out.println("line3: " + line);
                        Pattern lastWordPattern = Pattern.compile("(.*?) \\]");
                        Matcher lastWordMatch = lastWordPattern.matcher(line);
                        if(lastWordMatch.find())
                        {
                            //System.out.println("line4: " + lastWordMatch.group(1));
                            currentWords.add(lastWordMatch.group(1));
                            newEmotion = new Emotions(currentEmotion,currentWords);
                            break;
                        }
                        else
                        {
                            //System.out.println("line5: " + line);
                            currentWords.add(line);
                        }
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
        Emotions nextEmotion = null;
        while ((nextEmotion = reader.readEmotions()) != null)
        {
            System.out.println("nextEmotion: " + nextEmotion);
        }
    }
}