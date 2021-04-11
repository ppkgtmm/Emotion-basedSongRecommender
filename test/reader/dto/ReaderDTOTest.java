package reader.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

class ReaderDTOTest {

    // test if ReaderDTO receive and set properties correctly (validation is done by class who instantiate the DTO)
    @Test
    public void testDtoInitialization(){
        String title = "abcd";
        ArrayList<String> details = new ArrayList<>(Arrays.asList("asc", "123"));
        ReaderDTO data = new ReaderDTO(title, details);
        assertEquals(title, data.getTitle());
        assertEquals(details, data.getDetails());
    }

    @Test
    public void testDtoInitialization2(){
        String title = "";
        ArrayList<String> details = new ArrayList<>(Arrays.asList(null, "123"));
        ReaderDTO data = new ReaderDTO(title, details);
        assertEquals(title, data.getTitle());
        assertEquals(details, data.getDetails());
    }

    @Test
    public void testDtoInitialization3(){
        ArrayList<String> details = new ArrayList<>();
        ReaderDTO data = new ReaderDTO(null, details);
        assertNull(data.getTitle());
        assertEquals(details, data.getDetails());
    }

    @Test
    public void testDtoInitialization4(){
        ReaderDTO data = new ReaderDTO(null, null);
        assertNull(data.getTitle());
        assertNull(data.getDetails());
    }
}