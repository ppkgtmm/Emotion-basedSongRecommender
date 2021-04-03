package reader;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TextFileReader {

    private BufferedReader reader = null;


    public boolean open(String filename) {
        boolean bOk = true;
        try {
            if (reader != null)
                reader.close();
        } catch (IOException io) {
            reader = null;
        }
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException fnf) {
            bOk = false;
            reader = null;
        }
        return bOk;
    }


    public String getNextLine() {
        String lineRead = null;
        try {
            if (reader != null) {
                lineRead = reader.readLine();
                if (lineRead == null) {
                    reader.close();
                }
            }
        } catch (IOException ioe) {
            lineRead = null;
        }
        return lineRead;
    }


    public void close() {
        try {
            reader.close();
        } catch (IOException ioe) {
        }
    }
}

