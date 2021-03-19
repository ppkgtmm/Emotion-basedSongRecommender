package emotion;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import data.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmotionTest {

    Emotion emotionObject;
    String emotion;
    ArrayList<String> words;

    @Order(1)
    @Test
    public void testSetup(){
        String[] emotions = MockData.getEmotions();
        ArrayList<String> words = MockData.getWords();
        assertEquals(emotions.length, words.size());
        assertTrue(emotions.length > 0);
        assertNotNull(emotions[0]);
        assertNotNull(words.get(0));
        this.emotion = emotions[0];
        this.words = new ArrayList<>(Arrays.asList(words.get(0).split(" ")));
        emotionObject = new Emotion(this.emotion, this.words);
    }


    @Test
    public void setProperlySetData(){
        assertEquals(this.emotion, emotionObject.getTitle());
        assertEquals(this.words, emotionObject.getDetails());
        assertEquals(this.words.size(), emotionObject.getDetails().size());
    }

}