import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmotionManager
{
    private Emotions emotions;
    private static EmotionManager emotionManager= null;
    private EmotionReader reader;

    // public static SongManager getInstance()
    // {
    //     if(SongManager.songManager == null)
    //     {
    //         songManager = new SongManager();
    //     }
    //     return SongManager.songManager;
    // }

    // public boolean readEmotions(String fileName)
    // {
    //     boolean result = false;
    //     reader = new EmotionReader();
    //     if (!reader.open(fileName))
    //     {
    //         System.out.println("Error opening song file " + fileName);
    //         System.exit(1);
    //     }
    //     Emotions nextEmotion = null;
    //     while ((nextEmotion = reader.readEmotions()) != null)
    //     {
    //         System.out.println("Successfully added " + nextEmotion.getEmotions());
    //         for (String word:nextEmotion.getEmotionWords(nextEmotion.getEmotions()))
    //         {
    //             System.out.println(word);
    //         }
    //             emotions.addEmotion(nextEmotion);
    //             result = true;
    //     }
    //     return  result;
    // }

    public static void main(String[] args) {

    }
}
