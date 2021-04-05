package reader.dto;

import interfaces.Data;

import java.util.ArrayList;

public class ReaderDTO implements Data {
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
}
