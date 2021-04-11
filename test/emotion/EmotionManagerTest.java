package emotion;

import data.MockData;
import interfaces.Data;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmotionManagerTest {
    EmotionManager emotionManager;
    ArrayList<Emotion> emotions = MockData.getEmotionObjects();

    @Test
    @Order(1)
    public void setUp() {
        assertNotNull(emotions);
        assertTrue(emotions.size() > 1);
        emotionManager = EmotionManager.getInstance();
        assertNotNull(emotionManager);
    }

    @Test
    @Order(2)
    public void testAddEmotion() {
        // add emotions seen for the first time
        assertTrue(emotionManager.addEmotion(emotions.get(0)));
        assertTrue(emotionManager.addEmotion(emotions.get(1)));

        // invalid emotion should not be added
        assertFalse(emotionManager.addEmotion(null));
        assertFalse(emotionManager.addEmotion(new Emotion(null, null)));
        assertFalse(emotionManager.addEmotion(new Emotion("", null)));
        assertFalse(emotionManager.addEmotion(new Emotion(" ", new ArrayList<>())));
        assertFalse(emotionManager.addEmotion(new Emotion("abc", new ArrayList<>())));
    }


    @Test
    @Order(3)
    public void testGetEmotions1() {
        // check for emotions -- only valid ones added should be found
        ArrayList<Emotion> expectedEmotions = new ArrayList<>(emotions.subList(0, 2));
        ArrayList<Data> actualEmotions = emotionManager.getEmotions();
        assertTrue(expectedEmotions.equals(actualEmotions));
    }

    @Test
    @Order(4)
    public void testAddDuplicateEmotion() {
        // adding duplicate emotion is not allowed
        assertFalse(emotionManager.addEmotion(emotions.get(0)));
        assertFalse(emotionManager.addEmotion(emotions.get(1)));
        // duplicate emotion (string) is not allowed though the emotion has different word list associated
        assertFalse(emotionManager.addEmotion(new Emotion(emotions.get(0).getTitle(),
                new ArrayList<>(Arrays.asList("qwq", "ppp")))));
    }


    @Test
    @Order(5)
    public void testGetEmotions2() {
        // emotion list remained unchanged after invalid addition
        ArrayList<Emotion> expectedEmotions = new ArrayList<>(emotions.subList(0, 2));
        ArrayList<Data> actualEmotions = emotionManager.getEmotions();
        assertTrue(expectedEmotions.equals(actualEmotions));
    }

}