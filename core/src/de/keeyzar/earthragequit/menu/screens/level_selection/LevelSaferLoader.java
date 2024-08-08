package de.keeyzar.earthragequit.menu.screens.level_selection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author = Keeyzar on 19.05.2016
 */
public class LevelSaferLoader {
    private LevelVerwalter levelVerwalter;
    private static final String PREFS = "LEVELPREFS";

    private final String LEVEL_LOCK = "lock_";
    private final String GALAXY_LOCK = "galaxy_lock";

    public LevelSaferLoader(LevelVerwalter levelVerwalter) {
        this.levelVerwalter = levelVerwalter;

    }

    protected void load(){
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        ObjectMap<Integer, Boolean> levelLockMap = levelVerwalter.getLevelLockMap();
        for(int i = 2; i<= levelLockMap.size; i++){
            boolean aBoolean = preferences.getBoolean(LEVEL_LOCK + i, true);
            levelLockMap.put(i, aBoolean);
        }

        ObjectMap<Integer, Boolean> galaxyLockMap = levelVerwalter.getGalaxyLockMap();
        for(int i = 2; i <= galaxyLockMap.size; i++){
            boolean aBoolean = preferences.getBoolean(GALAXY_LOCK + i, true);
            galaxyLockMap.put(i, aBoolean);
        }
    }

    protected void safe(){
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        ObjectMap<Integer, Boolean> levelLockMap = levelVerwalter.getLevelLockMap();
        for(int i = 1; i<= levelLockMap.size; i++){
            preferences.putBoolean(LEVEL_LOCK + i, levelLockMap.get(i));
        }

        final ObjectMap<Integer, Boolean> galaxyLockMap = levelVerwalter.getGalaxyLockMap();
        for(int i = 2; i <= galaxyLockMap.size; i++){
            preferences.putBoolean(GALAXY_LOCK + i, galaxyLockMap.get(i));
        }
        preferences.flush();
    }

    protected void reset(){
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        preferences.clear();
        preferences.flush();
        load();
    }
}
