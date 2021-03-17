package data;

public class MockData {

    private static final String[] songs = {
            "I don't lie to you",
            "Got ya crayon",
            "Why so serious"
    };

    private static final String[] lyrics = {
                    "When tell me, when did I lie I don't know why I only spoken the truth \n" +
                    "but you never believed me though It wasn't a lie and I'll try to \n" +
                    "give an explanation \n",

                    "Im ma got ya a crayon crayon crayon man, got ya crayon crayon crayon man give ma \n" +
                    "give ma common man got ya got ya crayon wow!, don't ya dare throw it dude \n" +
                    "I don't wanna create a dispute crayon crayon crayon man crayon wow ! \n",

                    "Life is not that serious, everything gonna be fine He said so why worry \n"+
                    "Worry worry worry less Don't ya worry oh oh its joyous super joyous \n" +
                    "Worry worry worry less Don't ya worry oh oh be happy \n"
    };

    private static final String[] emotions = {
            "happy",
            "worry",
            "disappointed",
            "jealous"
    };

    private static final String[] words = {
            "fine super joyous happy wow",
            "worry fine dispute",
            "explanation lie don't why what? dispute",
            "no oh jealous envy everything"
    };

    public static String[] getLyrics() {
        return lyrics;
    }

    public static String[] getSongs() {
        return songs;
    }

    public static String[] getEmotions() {
        return emotions;
    }

    public static String[] getWords() {
        return words;
    }
}
