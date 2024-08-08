package de.keeyzar.earthragequit.menu.screens.level_selection;

import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Level3.Level3;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level1.Level1;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level2.Level2;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level4.Level4;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level5.Level5;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level6.Level6;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level7.Level7;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level8.Level8;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level9.Level9;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.saving.Safeable;

/**
 * @author = Keeyzar on 13.03.2016
 */
public class LevelVerwalter implements Safeable {
    private ObjectMap<Integer, Boolean> levelLockMap;
    private ObjectMap<Integer, Boolean> galaxyLockMap;
    private ObjectMap<Integer, LevelDefining> levelMap;
    private LevelSaferLoader levelSaferLoader;

    public LevelVerwalter(){
        levelSaferLoader = new LevelSaferLoader(this);
        levelMap = new ObjectMap<Integer, LevelDefining>();
        levelMap.put(1, new Level1());
        levelMap.put(2, new Level2());
        levelMap.put(3, new Level3());
        levelMap.put(4, new Level4());
        levelMap.put(5, new Level5());
        levelMap.put(6, new Level6());
        levelMap.put(7, new Level7());
        levelMap.put(8, new Level8());
        levelMap.put(9, new Level9());

        ///MAPS THAT NEEDS TO BE SAFED
        levelLockMap = new ObjectMap<Integer, Boolean>();
        for(int i = 1; i <= levelMap.size; i++){
            levelLockMap.put(i, false);
        }

        galaxyLockMap = new ObjectMap<Integer, Boolean>();
        galaxyLockMap.put(1, false);
        galaxyLockMap.put(2, true);

        levelSaferLoader.load();

        //FIXME REMOVE BEFORE RELEASE
//        levelLockMap.put(1, false);
//        levelLockMap.put(2, false);
//        levelLockMap.put(3, false);
//        levelLockMap.put(4, false);
//        levelLockMap.put(5, false);
//        levelLockMap.put(6, false);
//        levelLockMap.put(7, false);
//        levelLockMap.put(8, false);
//        levelLockMap.put(9, false);
//        levelLockMap.put(10, false);

    }

    /**
     * return description of level
     * @return may be null if no level exists with that Description
     */
    public LevelDescription getLevelDescription(int whichLevel){
        final LevelDefining level = getLevel(whichLevel);

        if(level != null) return level.getLevelDescription();
        return null;
    }

    /**
     * return the level
     */
    public LevelDefining getLevel(int whichLevel){
        return levelMap.get(whichLevel);
    }

    public BossLevelDefining getBossLevel(int whichlevel) {
        return getLevelDescription(whichlevel).getBossLevel();
    }

    /**
     * returns the lock map
     */
    public ObjectMap<Integer, Boolean> getLevelLockMap() {
        return levelLockMap;
    }

    public void unlockLevel(int level){
        levelLockMap.put(level, false);
        save();
    }

    @Override
    public void save() {
        levelSaferLoader.safe();
    }

    @Override
    public void reset() {
        levelSaferLoader.reset();
    }

    public ObjectMap<Integer, Boolean> getGalaxyLockMap() {
        return galaxyLockMap;
    }

    public void instantiate(ERQGame game, Transitionable transitionable, int level, boolean bossLevel){
        if(level < 1) throw new RuntimeException("Level must greater than 0!");

        //if bigger than any level, instantiate main menu
        if(level > levelMap.size){
            game.setScreen(new TransitionScreen(game, transitionable, new MainMenuScreen(game)));
            return;
        }

        if(!bossLevel) {
            game.setScreen(new TransitionScreen(game, transitionable, new GameScreen(game, getLevel(level))));
        } else {
            game.setScreen(new TransitionScreen(game, transitionable, new BossScreen(game, getBossLevel(level))));
        }
    }
}
