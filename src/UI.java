import emotion.Emotion;
import interfaces.Data;
import song.Song;
import utils.Display;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;


public class UI {

    private static final String getEmotionStopSign = "_done_";
    private static final String noWordsErrorMessage = "No words have been added to word collection";
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
        System.out.println("1. Find from keyword in title");
        System.out.println("2. Find from all songs list");
        System.out.println("Please enter an option");
        String input = inputLine.nextLine();
        return Utils.parseOption(input);
    }


    private static void getSongAndPrintLyrics(ArrayList<Data> songs) {
        Display.printData(songs, "song");
        if (songs != null && songs.size() > 0) {
            String input = Utils.getInputString("Please enter song number or 0 to return", inputLine);
            if (Utils.parseOption(input) == 0) return;
            Song selectedSong = (Song) Utils.getChosenItem(input, songs);
            Display.printLyrics(selectedSong);
        }
    }


    private static void seeLyrics() {
        int selectedChoice = getHowFindSong();
        ArrayList<Data> songs;
        if (!Utils.isValidChoice(selectedChoice, 0, MenuOption.ALL_SONGS.value))
            System.out.println("Please enter a valid option");
        else {
            if (selectedChoice == MenuOption.ALL_SONGS.value) {
                songs = facilitator.getAllSongs();
                getSongAndPrintLyrics(songs);
            } else if (selectedChoice == MenuOption.SONG_BY_KW.value) {
                String keyword = Utils.getInputString("Please enter keyword or enter to return", inputLine);
                songs = facilitator.getSongs(keyword);
                getSongAndPrintLyrics(songs);
            }
        }
    }

    private static ArrayList<String> getEmotionWords() {
        ArrayList<String> words = new ArrayList<>();
        boolean shouldBreak = false;
        while (!shouldBreak) {
            String word = Utils.getInputString("Enter words for emotion or _done_ : ", inputLine);
            shouldBreak = Utils.shouldStopGetStrings(word, getEmotionStopSign, words, noWordsErrorMessage);
            if (!shouldBreak) {
                String cleanedWord = Utils.getValidWord(word);
                if (cleanedWord != null) {
                    words.add(word);
                }
            }

        }
        return words;
    }

    private static String getEmotionToAdd() {
        String emotion = Utils.getInputString("Please enter emotion to add or enter to return", inputLine).trim();
        if (emotion.isEmpty()) return null;
        while (!emotion.matches("[a-zA-Z.!\\- ']+") || emotion.isEmpty()) {
            System.out.println("emotion entered is not a valid word");
            emotion = Utils.getInputString("Please enter emotion to add again", inputLine).trim();
        }
        return emotion;
    }


    private static void addEmotion() {

        String emotion = getEmotionToAdd();
        if (emotion == null) return;
        ArrayList<String> words = getEmotionWords();
        boolean bOk = facilitator.addEmotion(emotion, words);
        if (bOk) {
            System.out.println("Emotion added successfully");
        } else {
            System.out.println("Cannot to add emotion");
        }
    }


    private static void findSongByTitle() {
        String keyword = Utils.getInputString("Enter keyword in song title or enter to return: ", inputLine);
        Display.printData(facilitator.getSongs(keyword), "song");

    }

    private static Data getSongToRemoveFromEmotion(Emotion emotion) {
        ArrayList<Data> songs = facilitator.getSongsFromEmotion(emotion);
        Display.printData(songs, "song");
        if (songs != null && songs.size() > 0) {
            String input = Utils.getInputString("Enter song number or 0 to return", inputLine);
            return Utils.getChosenItem(input, songs);
        }
        return null;
    }


    private static void removeFromCategory() {
        Emotion emotion = (Emotion) getEmotionInput();
        if(emotion == null) return;
        Song targetSong = (Song) getSongToRemoveFromEmotion(emotion);
        if (targetSong == null) return;
        int result = facilitator.removeSongFromCategory(emotion, targetSong);
        if (result > 0) {
            System.out.println("Song removed from emotion category successfully");
        } else if (result < 0) {
            System.out.println("Cannot remove song from emotion category");
        }
    }


    private static void endProgram() {
        inputLine.close();
        boolean succeed = facilitator.terminate();
        System.out.println("Exiting from the system\n");
        System.out.println("--------------------------------------------------------\n");
        if (succeed) {
            System.exit(0);
        }
        System.exit(1);
    }


    private static Data getEmotionInput() {
        ArrayList<Data> allEmotion = facilitator.getAllEmotions();
        Display.printData(allEmotion, "emotion");
        String input = Utils.getInputString("Please enter emotion number or 0 to return", inputLine);
        int selectedChoice = Utils.parseOption(input);
        return Utils.getChosenItem(selectedChoice, allEmotion);
    }


    private static ArrayList<Data> findSongFromEmotion() {
        Emotion emotion = (Emotion) getEmotionInput();
        if (emotion == null) return null;
        return facilitator.getSongsFromEmotion(emotion);
    }

    private static boolean handleDelete(int option) {
        if (!MenuOption.isDeleteRequest(option)) return false;
        if (option == MenuOption.REMOVE_FROM_EMOTION.value) removeFromCategory();
        return true;
    }

    private static boolean handleCreate(int option) {
        if (!MenuOption.isCreateRequest(option)) return false;
        if (option == MenuOption.ADD_EMOTION.value) addEmotion();
        return true;
    }

    private static boolean handleInteractiveGet(int option) {
        if (!MenuOption.isInteractiveGet(option)) return false;
        if (option == MenuOption.GET_LYRICS.value)
            seeLyrics();
        else if (option == MenuOption.FIND_SONG_BY_TITLE.value)
            findSongByTitle();
        else if (option == MenuOption.FIND_SONG_BY_EMOTION.value)
            Display.printData(findSongFromEmotion(), "song");
        return true;

    }

    private static boolean handleGet(int option) {
        if (!MenuOption.isGetRequest(option)) return false;
        if (option == MenuOption.GET_ALL_SONGS.value) {
            Display.printData(facilitator.getAllSongs(), "song");
            return true;
        } else if (option == MenuOption.GET_ALL_EMOTIONS.value) {
            Display.printData(facilitator.getAllEmotions(), "emotion");
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
