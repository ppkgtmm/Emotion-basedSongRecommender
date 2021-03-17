package emotion;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import data.*;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmotionTest {

    Emotion emotionObject;
    String emotion;
    ArrayList<String> words;


    @Test
    public void testProperlySetData(){
        String[] emotions = MockData.getEmotions();
        String[] words = MockData.getWords();
        assertEquals(emotions.length, words.length);
        assertTrue(emotions.length > 0);
        this.emotion = emotions[0];
        this.words = new ArrayList<>(Arrays.asList(words[0].split(" ")));
        emotionObject = new Emotion(this.emotion, this.words);
        assertEquals(this.emotion, emotionObject.getTitle());
        assertEquals(this.words, emotionObject.getDetails());
    }

}