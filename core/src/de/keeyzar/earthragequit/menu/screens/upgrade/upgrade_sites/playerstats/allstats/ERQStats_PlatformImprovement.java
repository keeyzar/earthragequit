package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_PlatformImprovement extends ERQStats {

    public ERQStats_PlatformImprovement(){
        IDENTIFIER_NAME = STAT_VARS.STAT_PLATFORM_JUMP_HEIGHT;

        displayName = "plat_improvement_title";
        shortDescription = "plat_improvement_desc_short";
        longDescription =  "plat_improvement_desc_long";
        setLocked(true);

        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 1);
        levelToAttributeBoost.put(2, 2);
        levelToAttributeBoost.put(3, 3);
        levelToAttributeBoost.put(4, 4);
        levelToAttributeBoost.put(5, 5);
        levelToAttributeBoost.put(6, 6);
        levelToAttributeBoost.put(7, 7);
        levelToAttributeBoost.put(8, 8);
        levelToAttributeBoost.put(9, 9);
        levelToAttributeBoost.put(10, 12);

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 10);
        levelToCosts.put(2, 20);
        levelToCosts.put(3, 60);
        levelToCosts.put(4, 120);
        levelToCosts.put(5, 200);
        levelToCosts.put(6, 300);
        levelToCosts.put(7, 500);
        levelToCosts.put(8, 1000);
        levelToCosts.put(9, 1500);
        levelToCosts.put(10, 3000);
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
