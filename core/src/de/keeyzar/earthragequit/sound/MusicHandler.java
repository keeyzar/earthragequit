package de.keeyzar.earthragequit.sound;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.saving.Safeable;

import static de.keeyzar.earthragequit.variables.assets.AssetVariables.*;

/**
 * @author = Keeyzar on 08.02.2016.
 */
public class MusicHandler implements Safeable {
    private Music music;
    private MusicSaferLoader musicSaferLoader;

    private static float musicVolume = 1f;
    private boolean musicAllowed = true;

    private static boolean soundAllowed;

    private IntMap<IntMap<String>> musicMap;
    public static final int MENU_MUSIC = 1;
    public static final int GAME_MUSIC = 2;
    public static final int BOSS_MUSIC = 3;
    public static final int INTRO_MUSIC = 4;
    public static final int LEVEL_WON_MUSIC = 5;
    public static final int LEVEL_LOST_MUSIC = 6;
    public static final int STORY_MUSIC = 8;
    public static final int TO_BE_CONTINUED_MUSIC = 9;
    public static final int CREDITS_MUSIC = 10;
    private int lastMusicType = 0;

    private MusicHelperClass musicFader; // for fading Music Actions
    private boolean musicTypeChanged = false;

    public MusicHandler(){
        musicSaferLoader = new MusicSaferLoader(this);
        initMusicMap();

        musicFader = new MusicHelperClass();
        musicFader.actualize(musicVolume);
    }

    private void initMusicMap() {
        musicMap = new IntMap<IntMap<String>>();
        put(MENU_MUSIC, MUSIC_MENU_1);
        put(STORY_MUSIC, MUSIC_STORY);
        put(GAME_MUSIC, MUSIC_GAME_1, MUSIC_GAME_2);
        put(BOSS_MUSIC, MUSIC_BOSS_1, MUSIC_BOSS_2);
        put(INTRO_MUSIC, MUSIC_INTRO);
        put(LEVEL_WON_MUSIC, MUSIC_LEVEL_WON);
        put(LEVEL_LOST_MUSIC, MUSIC_LEVEL_LOST);
        put(TO_BE_CONTINUED_MUSIC, MUSIC_TO_BE_CONTINUED);
        put(CREDITS_MUSIC, MUSIC_CREDITS);
    }

    private void put(int category, String... titles){
        if(titles.length == 0) throw new RuntimeException("Can't create category without titles!");
        IntMap<String> categoryMap = new IntMap<String>();
        int i = 1;
        for(String string : titles){
            categoryMap.put(i++, string);
        }
        musicMap.put(category, categoryMap);
    }

    /**
     * Play music by type (GAME MUSIC, BOSS MUSIC; INTRO_MUSIC etc..)
     */
    public void playMusic(int whichOne){
        if(lastMusicType != whichOne){
            musicTypeChanged = true;
        }
        lastMusicType = whichOne;

        if(musicAllowed){
            final IntMap<String> entries = musicMap.get(whichOne);
            if(musicTypeChanged || music == null) {
                final int random = MathUtils.random(1, entries.size);
                playMusicByName(entries.get(random));
                musicTypeChanged = false;
            }
        }
    }

    public void actualiseMusicState(){
        if(music == null){
            playMusic(lastMusicType);
            fadeInMusic();
            return;
        }
        if(musicAllowed && (!music.isPlaying() || musicFader.hasActions())){
            if(musicTypeChanged){
                  playMusic(lastMusicType);
                  musicTypeChanged = false;
            } else {
                music.play();
            }
            fadeInMusic();
        } else if(!musicAllowed && music.isPlaying()){
            fadeOutMusic(null);
        }
    }

    private void playMusicByName(final String name){
        if(music != null && music.isPlaying()){
            fadeOutMusic(name);
            return;
        }
        playMusic(name);
        fadeInMusic();
    }

    private void playMusic(String name){
        music = ERQAssets.MANAGER.get(name, Music.class);
        music.setLooping(true);
        music.setVolume(0);
        music.play();
    }

    public void pauseMusic(){
        if(music != null && music.isPlaying() && musicAllowed){
            music.pause();
        }
    }

    public void resumeMusic(){
        if(music != null && !music.isPlaying() && musicAllowed){
            music.setVolume(musicVolume);
            music.play();
        }
    }


    public void actOnMusic(float deltaTime) {
        if(musicFader.hasActions() && music != null){
            musicFader.act(deltaTime);
            music.setVolume(musicFader.a());
        }
    }

    private void fadeInMusic(){
        musicFader.fadeIn(musicVolume);
    }

    /**
     * fades out music
     */
    private void fadeOutMusic(final String name){
        musicFader.fadeOutNewMusic(new Runnable() {
            @Override
            public void run() {
                music.stop();
                if(name != null){
                    playMusic(name);
                    fadeInMusic();
                }
            }
        }, musicVolume);
    }

    public boolean isMusicAllowed() {
        return musicAllowed;
    }

    public void setMusicAllowed(boolean musicAllowed) {
        this.musicAllowed = musicAllowed;
    }

    public static float getSoundVolume() {
        return soundAllowed ? 0.5f : 0;
    }

    public boolean isSoundAllowed() {
        return soundAllowed;
    }

    public void setSoundAllowed(boolean soundAllowed) {
        MusicHandler.soundAllowed = soundAllowed;
    }




    @Override
    public void save() {
        musicSaferLoader.safe();
    }

    @Override
    public void reset() {
        //do not reset music. A player does not want to redo all stuff from the music.
    }

    private class MusicHelperClass extends Actor{

        private boolean fadingIn = false;

        void fadeIn(float to){
            if(!hasActions()) getColor().a = 0;
            clearActions();
            fadingIn = true;
            addAction(Actions.sequence(Actions.alpha(to, 2f, Interpolation.exp5), Actions.run(new Runnable() {
                @Override
                public void run() {
                    fadingIn = false;
                }
            })));
        }

        boolean isFadingIn(){
            return fadingIn;
        }

        float a(){
            return getColor().a;
        }
        void actualize(float a){
            getColor().a = a;
        }

        void fadeOutNewMusic(Runnable runnable, float from){
            if(!hasActions()) getColor().a = from;
            clearActions();
            fadingIn = false;
            addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(runnable)));
        }

        void stopFadingIn() {
            fadingIn = false;
            clearActions();
        }
    }

}
