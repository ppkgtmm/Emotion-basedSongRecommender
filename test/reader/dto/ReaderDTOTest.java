package reader.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

class ReaderDTOTest {
    @Test
    public void testDtoInitialization(){
        String title = "abcd";
        ArrayList<String> details = new ArrayList<>(Arrays.asList("asc", "123"));
        ReaderDTO data = new ReaderDTO(title, details);
        assertEquals(title, data.getTitle());
        assertEquals(details, data.getDetails());
        assertFalse(data.isIncompleteData());
    }

    @Test
    public void testInvalidData(){
        String title = null;
        ArrayList<String> details = new ArrayList<>();
        ReaderDTO data = new ReaderDTO(title, details);
        assertEquals(title, data.getTitle());
        assertEquals(details, data.getDetails());
        assertTrue(data.isIncompleteData());
    }
}