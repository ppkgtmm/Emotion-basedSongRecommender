package reader.dto;

import org.junit.jupiter.api.Test;
import utils.Utils;

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
        assertEquals(details.size(), data.getDetails().size());
    }
}