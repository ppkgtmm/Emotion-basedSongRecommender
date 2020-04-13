public class Facilitator
{
    private SongManager songManager=null;
    private SongEmotions songEmotions = null;
    private EmotionManager emotionManager = null;
    private static Facilitator facilitator = null;

    private Facilitator()
    {
        songManager = SongManager.getInstance();
        songEmotions = SongEmotions.getInstance();
        emotionManager = EmotionManager.getInstance();
    }
    public static Facilitator getInstance()
    {
        if (facilitator==null)
        {
            facilitator = new Facilitator();
        }
        return facilitator;
    }

    public boolean doSetting(String songFileName,String emotionFileName,String removedSongsFile)
    {
        boolean songOK = songManager.readSongs(songFileName);
        boolean emotionOK = emotionManager.readEmotions(emotionFileName);
        boolean removedSongsOK = false;
        boolean ok = false;
        if(!removedSongsFile.isEmpty())
        {
            removedSongsOK = syncDeletedSongs(removedSongsFile);
            if(songOK && emotionOK && removedSongsOK)
            {
                for(String emotion:emotionManager.getEmotions())
                {
                    songEmotions.sync(emotion,emotionManager.getEmotionWords(emotion),songManager.getAllSongs());
                }
            }
        }
        return ok;
    }

    public boolean syncDeletedSongs(String fileName)
    {
        return songEmotions.initialize(fileName);
    }



}
