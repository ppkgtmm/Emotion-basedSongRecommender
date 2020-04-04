import java.util.ArrayList;
import java.util.HashMap;

public class Song
{
    private Integer id;
    private static Integer counter = 0;
    private String title;
    private ArrayList<String> lyrics;
    private HashMap<String,Double> emotionScore;
    private static Double cutOffScore = 0.3;

    public Song(String title, ArrayList<String> lyrics)
    {
        counter += 1;
        id = counter;
        this.title = title;
        this.lyrics = lyrics;

    }

    public String getTitle()
    {
        return title;
    }

    public ArrayList<String> getLyrics()
    {
        return lyrics;
    }

    public Integer getId()
    {
        return id;
    }

    private Integer analyzeLyrics(String word)
    {
        Integer count = 0;
        Integer fromIndex = 0;
        for(String line : lyrics)
        {
            while ((fromIndex = line.indexOf(word, fromIndex)) != -1 ){
                count++;
                fromIndex++;
            }
        }
        return count;
    }

    public Double getScore(String emotion)
    {
        if(emotionScore.containsKey(emotion))
        {
            return emotionScore.get(emotion);
        }
        return -1.0;
    }
    public Integer countWordsInLyrics(){
        Integer count = 0;
        for(String line:lyrics)
        {
            count += line.split("//s+").length;
        }
        return count;
    }
    public Double countScore(ArrayList<String> words)
    {
        Double score = 0.0;
        for(String word:words)
        {
            score += analyzeLyrics(word);
        }
        return score/countWordsInLyrics();

    }
    public static void main(String[] args) {

    }
}
