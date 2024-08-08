package de.keeyzar.earthragequit.gameplay.ingame.level.levels;

import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;

/**
 * @author = Keeyzar on 26.01.2017.
 */
public class LevelDescription {
    public int width;
    public int height;
    public int level;
    public String levelName;
    public int coinValue;
    public String levelTmxPath;
    public boolean hasBossLevel = false;
    //returns null, if no boss level is there, if so, return here a new Boss Instance
    public BossLevelDefining getBossLevel(){
        return null;
    }

}
