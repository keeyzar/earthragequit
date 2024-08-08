package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_BagsMagnetic extends ERQStats {

    public ERQStats_BagsMagnetic(){
        IDENTIFIER_NAME = STAT_VARS.STAT_MAGNETIC_MONEYBAG;
        displayName = "stats_magnetic_bags_title";

        setLocked(true);
        shortDescription =  "stats_magnetic_bags_desc_short";
        longDescription =  "stats_magnetic_bags_desc_long";

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 500);

        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 1f);
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
