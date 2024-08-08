package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_MaximumSpeed extends ERQStats {

    public ERQStats_MaximumSpeed(){
        IDENTIFIER_NAME = STAT_VARS.STAT_ROCKET_MAXIMUM_SPEED;

        setLocked(true);
        displayName = "stats_max_speed_title";
        shortDescription = "stats_max_speed_desc_short";
        longDescription =  "stats_max_speed_desc_long";

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 10);
        levelToCosts.put(2, 20);
        levelToCosts.put(3, 40);
        levelToCosts.put(4, 80);
        levelToCosts.put(5, 150);
        levelToCosts.put(6, 200);
        levelToCosts.put(7, 300);
        levelToCosts.put(8, 500);

        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 2f);
        levelToAttributeBoost.put(2, 3f);
        levelToAttributeBoost.put(3, 4f);
        levelToAttributeBoost.put(4, 5f);
        levelToAttributeBoost.put(5, 6f);
        levelToAttributeBoost.put(6, 8f);
        levelToAttributeBoost.put(7, 10f);
        levelToAttributeBoost.put(8, 12f);
    }


    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
