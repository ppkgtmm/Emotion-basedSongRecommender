package utils;


import org.junit.jupiter.api.Test;

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
        assertFalse(Utils.isValidChoice(-1,9));
        assertTrue(Utils.isValidChoice(0,9));
        assertTrue(Utils.isValidChoice(6,9));
        assertFalse(Utils.isValidChoice(10,9));
    }
}