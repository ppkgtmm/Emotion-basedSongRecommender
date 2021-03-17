/**
 * Display.java
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
import utils.Utils;

import java.util.ArrayList;

public class Display {

    private static void printListData(ArrayList<Data> data){
        int i = 0;
        for (Data item : data) {
            i++;
            System.out.println(i + ". " + item.getTitle());
        }
    }

    public static void printData(ArrayList<Data> data, String name, boolean verbose){
        if(data == null || data.size()==0){
            if(!verbose){
                return;
            }
            System.out.println("No " + name.toUpperCase() + " available");
        }else {
            System.out.println(">> "+ name.toUpperCase() + " List <<");
            printListData(data);
        }
    }

//    public static void printSongs(ArrayList<Song> songs, boolean verbose) {
//        if (songs == null || songs.size() == 0) {
//            if(!verbose){
//                return;
//            }
//        }
//        else {
//            System.out.println(">> Song List <<");
//            int i = 0;
//            for (Song song : songs) {
//                i++;
//                System.out.println(i + ". " + song.getTitle());
//            }
//        }
//    }

//    /**
//     * Print emotions in emotion list provided
//     * @param allEmotions list of emotions to print
//     */
//    public static void printAllEmotions(ArrayList<Emotion> allEmotions) {
//        if (allEmotions == null || allEmotions.size() == 0) {
//            System.out.println("No emotions available");
//        }
//        /* there exist some emotion(s) in the system */
//        else {
//            System.out.println(">> Emotion List <<");
//            int i = 0;
//            for (Emotion emotion : allEmotions) {
//                i++;
//                System.out.println(i + ". " + emotion.getTitle());
//            }
//        }
//    }

    /**
     * Print lyrics of song provided
     * @param currentSong song to print lyrics
     *
     */
    public static void printLyrics(Song currentSong) {
        if (currentSong != null) {
            ArrayList<String> currentLyrics = currentSong.getDetails();
            if (currentLyrics != null && currentLyrics.size() > 0) {
                System.out.println(">> Lyrics of " + currentSong.getTitle() + " <<");
                Utils.printStringList(currentLyrics);
                return;
            }
        }
        System.out.println("Unable to print song lyrics");
    }
}
