package emotion;

import data.MockData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmotionTest {

    @Test
    public void testProperlySetEmotion() {
        String[] emotions = MockData.getEmotions();
        String[] words = MockData.getWords();
        assertNotNull(emotions);
        assertNotNull(words);
        assertEquals(emotions.length, words.length);
        assertTrue(emotions.length > 0);
        ArrayList<String>  wordList = MockData.parseEmotionWords(words[0]);
        Emotion emotionObject = new Emotion(emotions[0],wordList);
        assertEquals(emotions[0], emotionObject.getTitle());
        assertEquals(wordList, emotionObject.getDetails());
    }

    // test to check if values are correctly set H.W. there will be data validation first by other classes
    // before instantiating emotion in real scenario

    @Test
    public void testProperlySetEmotion2() {
        ArrayList<String> wordList = new ArrayList<>();
        Emotion emotionObject = new Emotion(null, wordList);
        assertNull(emotionObject.getTitle());
        assertEquals(wordList, emotionObject.getDetails());
    }

    @Test
    public void testProperlySetEmotion3() {
        String testTitle = "";
        Emotion emotionObject = new Emotion(testTitle, null);
        assertEquals(testTitle,emotionObject.getTitle());
        assertNull(emotionObject.getDetails());
    }

    @Test
    public void testProperlySetEmotion4() {
        String emptyEmotion = "   ";
        ArrayList<String> wordList = new ArrayList<>();
        Emotion emotionObject = new Emotion(emptyEmotion, wordList);
        assertEquals(emptyEmotion, emotionObject.getTitle());
        assertEquals(wordList, emotionObject.getDetails());
    }

    @Test
    public void testProperlySetEmotion5() {
        String emptyEmotion = "   ";
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(" ", "  "));
        Emotion emotionObject = new Emotion(emptyEmotion, wordList);
        assertEquals(emptyEmotion, emotionObject.getTitle());
        assertEquals(wordList, emotionObject.getDetails());
    }


}