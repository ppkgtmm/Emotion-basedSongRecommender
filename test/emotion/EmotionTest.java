package emotion;

import interfaces.Data;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import data.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmotionTest {

    Emotion emotionObject;
    String emotion;
    ArrayList<String> words;
    EmotionManager emotionManager;


    @Test
    @Order(1)
    public void setup() {
        String[] emotions = MockData.getEmotions();
        ArrayList<String> words = MockData.getWords();
        assertEquals(emotions.length, words.size());
        assertTrue(emotions.length > 0);
        assertNotNull(emotions[0]);
        assertNotNull(words.get(0));
        this.emotion = emotions[0];
        this.words = new ArrayList<>(Arrays.asList(words.get(0).split(" ")));
        emotionObject = new Emotion(this.emotion, this.words);
        emotionManager = EmotionManager.getInstance();
        assertNotNull(emotionManager);
    }

    @Test
    @Order(2)
    public void testProperlySetEmotion() {
        assertEquals(emotion, emotionObject.getTitle());
        assertEquals(words.size(), emotionObject.getDetails().size());
        assertEquals(words, emotionObject.getDetails());
    }

    @Test
    @Order(3)
    public void testAddEmotion() {
        assertTrue(emotionManager.addEmotion(emotionObject));
        ArrayList<Data> emotions = emotionManager.getEmotions();
        int expectedSize = 1;
        assertEquals(expectedSize, emotions.size());
        assertEquals(emotions.get(expectedSize - 1), emotionObject);
        assertFalse(emotionManager.addEmotion(emotionObject));
        ArrayList<Data> emotionsAfterDuplicateAdd = emotionManager.getEmotions();
        assertEquals(expectedSize, emotionsAfterDuplicateAdd.size());
        assertEquals(emotionsAfterDuplicateAdd.get(expectedSize - 1), emotionObject);
    }

    @Test
    @Order(4)
    public void testAddInvalidEmotion() {
        assertFalse(emotionManager.addEmotion(new Emotion(null, null)));
        ArrayList<Data> emotions = emotionManager.getEmotions();
        int expectedSize = 1;
        assertEquals(expectedSize, emotions.size());
        assertEquals(emotions.get(expectedSize - 1), emotionObject);
        assertFalse(emotionManager.addEmotion(emotionObject));
        ArrayList<Data> emotionsAfterDuplicateAdd = emotionManager.getEmotions();
        assertEquals(expectedSize, emotionsAfterDuplicateAdd.size());
        assertEquals(emotionsAfterDuplicateAdd.get(expectedSize - 1), emotionObject);
    }

}