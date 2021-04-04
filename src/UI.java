import emotion.Emotion;
import interfaces.Data;
import song.Song;
import utils.Display;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;

import static utils.Utils.getInputString;

public class UI {

    private static Facilitator facilitator = Facilitator.getInstance();

    private static Scanner inputLine = new Scanner(System.in);


    private static int getOption() {
        System.out.println("Options:");
        System.out.println("0. Go to main menu");
        System.out.println("1. See all songs");
        System.out.println("2. See all emotions");
        System.out.println("3. See lyrics");
        System.out.println("4. Find song by title");
        System.out.println("5. Find song based on emotion");
        System.out.println("6. Add emotion");
        System.out.println("7. Remove song from emotion category");
        System.out.println("8. Exit");
        System.out.println("Please select an option: ");
        String input = inputLine.nextLine();
        return Utils.parseOption(input);
    }


    private static int getHowFindSong() {
        System.out.println("How would you like to find a song? ");
        System.out.println("0. Return to main menu");
        System.out.println("1. Find from all songs list");
        System.out.println("2. Find from keyword in title");
        System.out.println("Please enter an option");
        String input = inputLine.nextLine();
        return Utils.parseOption(input);
    }


    private static void getSongAndPrintLyrics(ArrayList<Data> songs) {
        Display.printData(songs, "song", false);
        if (songs != null && songs.size() > 0) {
            int selectedChoice = getSongToPrintLyrics(songs.size());
            if (selectedChoice > 0) {
                Display.printLyrics((Song) songs.get(selectedChoice));
            }
        }
    }

    private static int getSongToPrintLyrics(int numberOfSongs) {
        String input = getInputString("Please enter song number or 0 to return", inputLine);
        int selectedChoice = Utils.parseOption(input);
        if (Utils.isValidChoice(selectedChoice, numberOfSongs)) {
            if (selectedChoice == 0) {
                return 0;
            }
            return selectedChoice - 1;
        }
        System.out.println("You have entered invalid song number");
        return -1;
    }


    private static void seeLyrics() {
        int selectedChoice = getHowFindSong();
        String input;
        ArrayList<Data> songs = null;
        switch (selectedChoice) {
            case 0:
                break;
            case 1:
                songs = facilitator.getAllSongs();
                getSongAndPrintLyrics(songs);
                break;
            case 2:
                String keyword = getInputString("Please enter keyword or enter to return", inputLine);
                songs = facilitator.getSongs(keyword);
                getSongAndPrintLyrics(songs);
                break;
            default:
                System.out.println("Please enter a valid option");
                break;
        }
    }

    private static String getValidWord(String word) {
        if (word.isEmpty()) {
            System.out.println("Word should not be empty");
            return null;
        }
        if (Utils.isNumeric(word)) {
            System.out.println("Word should not be only number");
            return null;
        }
        return Utils.setSpaces(word);
    }

    private static ArrayList<String> getEmotionWords() {
        ArrayList<String> words = new ArrayList<>();
        String stopSign = "_done_";
        String noWordsError = "No words have been added to word collection";
        while (true) {
            String word = getInputString("Enter words for emotion or _done_ : ", inputLine);
            boolean shouldBreak = Utils.gotSomeStrings(word, stopSign, words, noWordsError);
            if (shouldBreak) {
                break;
            }
            String cleanedWord = getValidWord(word);
            if (cleanedWord != null && !words.contains(word)) {
                words.add(word);
            }
        }
        return words;
    }


    private static void addEmotion() {

        String emotion = getInputString("Please enter emotion to add", inputLine).trim();

        while (!emotion.matches("[a-zA-Z.!\\- ']+") || emotion.isEmpty()) {
            System.out.println("emotion.Emotion is not a valid word");
            emotion = getInputString("Please enter emotion to add again", inputLine).trim();
        }

        ArrayList<String> words = getEmotionWords();
        Emotion newEmotion = new Emotion(emotion.trim().toLowerCase(), words);
        boolean bOk = facilitator.addEmotion(newEmotion);
        if (bOk) {
            System.out.println("emotion.Emotion added successfully");
        } else {
            System.out.println("Cannot to add emotion");
        }
    }


    private static void findSongByTitle() {
        String keyword = getInputString("Enter keyword in song title: ", inputLine);
        Display.printData(facilitator.getSongs(keyword), "song", true);

    }

    private static Song getSongToRemoveFromEmotion(Emotion emotion) {
        ArrayList<Data> songs = facilitator.getSongsFromEmotion(emotion);
        Display.printData(songs, "song", true);
        if (songs != null && songs.size() > 0) {
            String input = getInputString("Enter song number", inputLine);
            int option = Utils.parseOption(input);

            if (Utils.isValidChoice(option, songs.size())) {
                option--;
                return (Song) songs.get(option);
            } else {
                System.out.println("Invalid song number entered");
            }
        }
        return null;
    }


    private static void removeFromCategory() {
        Data emotion = getEmotionInput();
        if (emotion != null) {
            Song targetSong = getSongToRemoveFromEmotion((Emotion) emotion);
            if (targetSong != null) {
                boolean succeed = facilitator.removeSongFromCategory((Emotion) emotion, targetSong);
                if (succeed) {
                    System.out.println("song.Song removed from emotion category successfully");
                } else {
                    System.out.println("Cannot remove song from emotion category");
                }
            }
        }
    }


    private static void endProgram() {
        inputLine.close();
        boolean succeed = facilitator.terminate();
        System.out.println("Exiting from the system\n");
        System.out.println("--------------------------------------------------------\n");
        if (succeed) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }


    private static Data getEmotionInput() {
        ArrayList<Data> allEmotion = facilitator.getAllEmotions();
        Display.printData(allEmotion, "emotion", true);
        if (allEmotion != null && allEmotion.size() > 0) {
            String input = getInputString("Please enter emotion number", inputLine);
            int selectedChoice = Utils.parseOption(input);
            if (Utils.isValidChoice(selectedChoice, allEmotion.size())) {
                selectedChoice--;
                return allEmotion.get(selectedChoice);
            } else {
                System.out.println("Invalid emotion number entered");
            }
        }
        return null;
    }


    private static ArrayList<Data> findSongFromEmotion() {
        Emotion emotion = (Emotion) getEmotionInput();
        if (emotion != null) {
            return facilitator.getSongsFromEmotion(emotion);
        }
        return null;
    }
    private static boolean handleDelete(int option){
        if(!MenuOption.isDeleteRequest(option)) return false;
        if(option == MenuOption.REMOVE_FROM_EMOTION.value) removeFromCategory();
        return true;
    }
    private static boolean handleCreate(int option){
        if(!MenuOption.isCreateRequest(option)) return false;
        if(option == MenuOption.ADD_EMOTION.value) addEmotion();
        return true;
    }

    private static boolean handleInteractiveGet(int option){
        if(!MenuOption.isInteractiveGet(option)) return false;
        if (option == MenuOption.GET_LYRICS.value)
            seeLyrics();
        else if(option == MenuOption.FIND_SONG_BY_TITLE.value)
            findSongByTitle();
        else if(option == MenuOption.FIND_SONG_BY_EMOTION.value)
            Display.printData(findSongFromEmotion(), "song", true);
        return true;

    }

    private static boolean handleGet(int option) {
        if(!MenuOption.isGetRequest(option)) return false;
        if (option == MenuOption.GET_ALL_SONGS.value){
            Display.printData(facilitator.getAllSongs(), "song", true);
            return true;
        }
        else if (option == MenuOption.GET_ALL_EMOTIONS.value){
            Display.printData(facilitator.getAllEmotions(), "emotion", true);
            return true;
        }
        return handleInteractiveGet(option);
    }

    private static void proceedOption(int option) {
        if (!MenuOption.isValidMenuOption(option))
            System.out.println("Please enter a valid option");
        if (MenuOption.shouldExit(option))
            endProgram();
        if (!handleGet(option) && !handleCreate(option)) {
            handleDelete(option);
        }
    }


    public static void run(String songsFileName, String emotionsFileName, String removedFileName) {
        if (facilitator.doSetting(songsFileName, emotionsFileName, removedFileName)) {
            while (true) {
                int option = getOption();
                proceedOption(option);
            }
        } else {
            System.out.println("Failed to do system set up --- exiting");
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("You should provide at least SongFile and EmotionFile");
            System.exit(1);
        } else if (args.length > 2) {
            UI.run(args[0], args[1], args[2]);
        } else {
            UI.run(args[0], args[1], "");
        }

    }
}
