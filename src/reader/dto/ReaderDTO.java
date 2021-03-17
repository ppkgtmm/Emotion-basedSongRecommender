package reader.dto;

import java.util.ArrayList;

public class ReaderDTO {
    private String title = null;
    private ArrayList<String> details = null;

    public ReaderDTO(String title, ArrayList<String> details){
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public boolean isIncompleteData(){
        return title == null || details == null || details.size() == 0;
    }
}
