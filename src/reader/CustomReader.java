package reader;

import reader.dto.ReaderDTO;

import java.util.ArrayList;

public class CustomReader extends TextFileReader {

    private static final String endPattern = "==END==";
    private String currentTitle = null;
    private ArrayList<String> currentDetails = null;
    private String titlePattern = null;
    private String detailPattern = null;

    public void setSearchPatterns(String titlePattern, String detailPattern) {
        this.titlePattern = titlePattern.trim();
        this.detailPattern = detailPattern.trim();
    }

    private void findAndSetTitle(String line) {

        int startIndex = line.indexOf(titlePattern);
        if (startIndex != -1) {
            String title = line.substring(startIndex + titlePattern.length()).trim();
            currentTitle = title.toLowerCase();
        }
    }

    private boolean findAndSetDetail(String line) {
        boolean detailStarted = line.contains(detailPattern);
        boolean isEnd = line.compareTo(CustomReader.endPattern) == 0;
        if (detailStarted) {
            currentDetails = new ArrayList<>();
            return false;
        }
        if (!isEnd) {
            currentDetails.add(line.toLowerCase());
        }
        return isEnd;

    }

    private void resetVariables() {
        currentTitle = null;
        currentDetails = null;
    }

    private ReaderDTO getPackedData() {
        if (currentTitle != null) {
            return new ReaderDTO(currentTitle, currentDetails);
        }
        return null;
    }

    private boolean parseLine(String line) {
        String trimmedLine = line.trim();
        if (line.isEmpty()) return false;
        if (currentTitle == null) {
            findAndSetTitle(trimmedLine);
            return false;
        } else {
            return findAndSetDetail(trimmedLine);
        }
    }

    public ReaderDTO readData(String titlePattern, String detailPattern) {
        ReaderDTO data = null;
        String line;

        setSearchPatterns(titlePattern, detailPattern);
        resetVariables();

        while (data == null && (line = getNextLine()) != null) {
            boolean dataRead = parseLine(line);
            if (dataRead) {
                data = getPackedData();
            }

        }
        return data != null ? data : getPackedData();
    }

}
