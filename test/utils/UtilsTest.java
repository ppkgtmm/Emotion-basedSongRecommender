package utils;


import emotion.Emotion;
import org.junit.jupiter.api.Test;
import reader.dto.ReaderDTO;
import song.Song;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    public void testParseOption(){
        assertEquals(123, Utils.parseOption("123 0"));
        assertEquals(-1, Utils.parseOption("abl"));
        assertEquals(-1, Utils.parseOption("12d4"));

    }

    @Test
    public void testIsNumeric(){
        assertTrue(Utils.isNumeric("-1.09"));
        assertFalse(Utils.isNumeric("123a"));
        assertFalse(Utils.isNumeric("-a1"));
        assertTrue(Utils.isNumeric(" 123    "));

    }

    @Test
    public void testSetSpaces(){
        assertEquals("123 er 45", Utils.setSpaces(" 123       er 45   "));
    }

    @Test
    public void testIsValidChoice(){
        assertFalse(Utils.isValidChoice(-1,0,9));
        assertTrue(Utils.isValidChoice(0,0,9));
        assertTrue(Utils.isValidChoice(6,5,9));
        assertFalse(Utils.isValidChoice(10,1,9));
    }

    @Test
    public void testIsValidData(){
        assertFalse(Utils.isValidData(new ReaderDTO(null, null)));
        assertFalse(Utils.isValidData(new Emotion("", null)));
        assertFalse(Utils.isValidData(new Song("abc", null)));
        assertFalse(Utils.isValidData(new ReaderDTO("abc", new ArrayList<>())));
        assertTrue(Utils.isValidData(new Song("abc", new ArrayList<>(Arrays.asList("123","456")))));
    }
}