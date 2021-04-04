import static utils.Utils.isValidChoice;

public enum MenuOption {
    RETURN(0),
    GET_ALL_SONGS(1),
    GET_ALL_EMOTIONS(2),
    GET_LYRICS(3),
    FIND_SONG_BY_TITLE(4),
    FIND_SONG_BY_EMOTION(5),
    ADD_EMOTION(6),
    REMOVE_FROM_EMOTION(7),
    EXIT(8),

    // for song selection to display lyrics
    SONG_BY_KW(1),
    ALL_SONGS(2); // make all songs option last for ease of validation

    public final int value;

    MenuOption(int i) {
        this.value = i;
    }

    static boolean isGetRequest(int option){
        return option >= GET_ALL_SONGS.value && option <= FIND_SONG_BY_EMOTION.value;
    }

    static boolean isInteractiveGet(int option){
        return !(option >= GET_ALL_SONGS.value && option <= GET_ALL_EMOTIONS.value);
    }
    static boolean isCreateRequest(int option){
        return option == ADD_EMOTION.value;
    }

    static boolean isDeleteRequest(int option){
        return option == REMOVE_FROM_EMOTION.value;
    }

    static boolean shouldExit(int option){
        return option == EXIT.value;
    }

    static  boolean isValidMenuOption(int option){
        return isValidChoice(option, RETURN.value, EXIT.value);
    }
}