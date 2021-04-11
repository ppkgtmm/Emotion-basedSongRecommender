package utils; /**
 * utils.Display.java
 * <p>
 * Help to display data like songs, emotions or
 * even lyrics.
 * <p>
 * Created by
 * Pinky Gautam ID: 60070503401,
 * Thitiporn Sukpartcharoen ID: 60070503419
 * <p>
 * 19 May 2020
 */

import interfaces.Data;
import song.Song;

import java.util.ArrayList;

public class Display {

    public static void printStringList(ArrayList<String> strings) {
        for (String currentString : strings) {
            System.out.println(currentString);
        }
    }

    private static void printListData(ArrayList<Data> data) {
        int i = 0;
        for (Data item : data) {
            i++;
            System.out.println(i + ". " + item.getTitle());
        }
    }

    public static void printData(
            ArrayList<Data> data,
            String name
    ) {
        if (data == null) return;
        if (data.size() == 0) {
            System.out.println("No " + name.toUpperCase() + " available");
        } else {
            System.out.println(">> " + name.toUpperCase() + " List <<");
            printListData(data);
        }
    }

    public static void printLyrics(Song currentSong) {
        if (currentSong != null) {
            ArrayList<String> currentLyrics = currentSong.getDetails();
            if (currentLyrics != null && currentLyrics.size() > 0) {
                System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                printStringList(currentLyrics);
                return;
            }
            System.out.println("Unable to print lyrics");
        }
    }

}
