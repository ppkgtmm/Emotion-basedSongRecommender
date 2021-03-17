package utils;

public class MockData {

    String[] songs = {
            "I don't lie to you",
            "Got ya crayon",
            "Why so serious"
    };

    String[] lyrics = {
                    "When tell me, when did I lie I don't know why I only spoken the truth" +
                    "but you never believed me though It wasn't a lie and I'll try to" +
                    "give an explanation",

                    "Im ma got ya a crayon crayon crayon man, got ya crayon crayon crayon man give ma" +
                    "give ma common man got ya got ya crayon wow!, don't ya dare throw it dude" +
                    "I don't wanna create a dispute crayon crayon crayon man crayon wow !",

                    "Life is not that serious, everything gonna be fine He said so why worry"+
                    "Worry worry worry less Don't ya worry oh oh its joyous super joyous" +
                    "Worry worry worry less Don't ya worry oh oh be happy"
    };

    String[] emotions = {
            "happy",
            "worry",
            "disappointed",
            "jealous"
    };

    String[] words = {
            "fine super joyous happy wow",
            "worry fine dispute",
            "explanation lie don't why what? dispute",
            "no oh jealous envy everything"
    };

    public String[] getLyrics() {
        return lyrics;
    }

    public String[] getSongs() {
        return songs;
    }

    public String[] getEmotions() {
        return emotions;
    }

    public String[] getWords() {
        return words;
    }
}
