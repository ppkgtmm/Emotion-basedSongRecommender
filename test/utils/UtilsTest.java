package utils;


import emotion.Emotion;
import interfaces.Data;
import org.junit.jupiter.api.Test;
import reader.dto.ReaderDTO;
import song.Song;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    public void testParseOption() {
        assertEquals(123, Utils.parseOption("123 0"));
        // invalid inputs
        assertEquals(-1, Utils.parseOption("abl 0"));
        assertEquals(-1, Utils.parseOption("12d4"));
        assertEquals(-1, Utils.parseOption(null));
        assertEquals(-1, Utils.parseOption(""));
        assertEquals(-1, Utils.parseOption("  "));
    }

    @Test
    public void testIsNumeric() {
        assertTrue(Utils.isNumeric("-1.09"));
        assertTrue(Utils.isNumeric(" 123    "));
        assertFalse(Utils.isNumeric("123a"));
        assertFalse(Utils.isNumeric("-a1"));

    }

    @Test
    public void testSetSpaces() {
        assertEquals("123 er 45", Utils.setSpaces(" 123       er 45   "));
        assertEquals("", Utils.setSpaces("   "));
        // invalid input
        Exception exception = assertThrows(InvalidParameterException.class, ()-> Utils.setSpaces(null));
        assertEquals("Invalid input to reformat", exception.getMessage());
    }

    @Test
    public void testIsValidChoice() {
        assertTrue(Utils.isValidChoice(0, 0, 9));
        assertTrue(Utils.isValidChoice(6, 5, 9));
        assertFalse(Utils.isValidChoice(-1, 0, 9));
        assertFalse(Utils.isValidChoice(10, 1, 9));
    }

    @Test
    public void testGetChosenItem() {
        ArrayList<Data> testList = new ArrayList<>();
        Song testSong = new Song("", new ArrayList<>());
        assertNull(Utils.getChosenItem(1, null));
        assertNull(Utils.getChosenItem(0, testList));
        assertNull(Utils.getChosenItem(1, testList));
        assertNull(Utils.getChosenItem("1", null));
        assertNull(Utils.getChosenItem("0", testList));
        assertNull(Utils.getChosenItem("1", testList));
        // invalid input
        assertNull(Utils.getChosenItem("1a", testList));

        testList.add(testSong);
        assertNotNull(Utils.getChosenItem(1, testList));
        assertNotNull(Utils.getChosenItem("1", testList));
        assertEquals(testSong, Utils.getChosenItem(1, testList));
        assertEquals(testSong, Utils.getChosenItem("1", testList));
    }


    @Test
    public void testIsValidData() {
        // invalid inputs
        assertFalse(Utils.isValidData(new ReaderDTO(null, null)));
        assertFalse(Utils.isValidData(new Emotion(" ", null)), "Invalid Emotion");
        assertFalse(Utils.isValidData(new Song("abc", null)));
        assertFalse(Utils.isValidData(new ReaderDTO("abc", new ArrayList<>())));

        assertTrue(Utils.isValidData(
                new Song("abc", new ArrayList<>(Arrays.asList("123", "456")))),
                "Invalid song"
        );
    }


    @Test
    public void testGotSomeString() {
        String message = "Not enough string have been collected";
        ArrayList<String> stringsGot = new ArrayList<>(Arrays.asList("loop", "cool"));
        String stopSign = "DONE";
        assertFalse(Utils.shouldStopGetStrings("duno", stopSign , new ArrayList<>(), message));
        assertFalse(Utils.shouldStopGetStrings(stopSign, stopSign, new ArrayList<>(), message));
        assertFalse(Utils.shouldStopGetStrings(stopSign, stopSign, new ArrayList<>(), null));
        assertFalse(Utils.shouldStopGetStrings("duno", stopSign ,stringsGot, message));
        assertTrue(Utils.shouldStopGetStrings(stopSign, stopSign, stringsGot, message));

        // invalid inputs
        Exception exceptionNullInput = assertThrows(InvalidParameterException.class,
                () -> Utils.shouldStopGetStrings(null,
                stopSign,stringsGot, message));
        assertEquals("Invalid argument(s) provided", exceptionNullInput.getMessage());

        Exception exceptionNullStopSign = assertThrows(InvalidParameterException.class,
                () -> Utils.shouldStopGetStrings("loop",
                null,stringsGot, message));
        assertEquals("Invalid argument(s) provided", exceptionNullStopSign.getMessage());

        Exception exceptionNullStringsGot = assertThrows(InvalidParameterException.class,
                () -> Utils.shouldStopGetStrings("ui",
                stopSign,null, null));
        assertEquals("Invalid argument(s) provided", exceptionNullStringsGot.getMessage());

    }

    @Test
    public void testGetValidWord() {
        // invalid inputs
        assertNull(Utils.getValidWord(null));
        assertNull(Utils.getValidWord(""));
        assertNull(Utils.getValidWord("  "));
        assertNull(Utils.getValidWord("  1234  "));

        assertEquals("oop 123",Utils.getValidWord("     oop 123 "));
    }

    @Test
    public void  testIsInValidString(){
        // invalid inputs
        assertTrue(Utils.isInvalidString(null));
        assertTrue(Utils.isInvalidString(""));
        assertTrue(Utils.isInvalidString(" "));

        assertFalse(Utils.isInvalidString("   000   "));
    }

    @Test
    public void  testIsInValidList(){
        ArrayList<String> sampleList = new ArrayList<>(Arrays.asList("12345","67890"));
        assertFalse(Utils.isInvalidList(sampleList));

        // invalid inputs
        assertTrue(Utils.isInvalidList(null));
        assertTrue(Utils.isInvalidList(new ArrayList<>()));

    }

}