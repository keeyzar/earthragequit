package de.keeyzar.earthragequit.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Keeyzar on 07.04.2016.
 */
public class MusicSaferLoader {

    private static final String PREFS = "MUSIC";
    private MusicHandler musicHandler;

    public MusicSaferLoader(MusicHandler musicHandler) {
        this.musicHandler = musicHandler;
        load();
    }

    public void safe(){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putBoolean("music_allowed", musicHandler.isMusicAllowed());
        prefs.putBoolean("sound_allowed", musicHandler.isSoundAllowed());
        prefs.flush();
    }

    public void load(){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        musicHandler.setMusicAllowed(prefs.getBoolean("music_allowed", true));
        musicHandler.setSoundAllowed(prefs.getBoolean("sound_allowed", true));


    }
}
