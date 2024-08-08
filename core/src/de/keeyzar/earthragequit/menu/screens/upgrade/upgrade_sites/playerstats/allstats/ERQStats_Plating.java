package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_Plating extends ERQStats {

    public ERQStats_Plating(){
        IDENTIFIER_NAME = STAT_VARS.STAT_PLATING;

        setLocked(true);
        displayName = "plating_title";
        shortDescription =  "plating_desc_short";
        longDescription = "plating_desc_long";

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 5);
        levelToCosts.put(2, 40);
        levelToCosts.put(3, 100);
        levelToCosts.put(4, 200);
        levelToCosts.put(5, 400);
        levelToCosts.put(6, 600);
        levelToCosts.put(7, 1000);


        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 1f);
        levelToAttributeBoost.put(2, 2f);
        levelToAttributeBoost.put(3, 3f);
        levelToAttributeBoost.put(4, 4f);
        levelToAttributeBoost.put(5, 6f);
        levelToAttributeBoost.put(6, 8f);
        levelToAttributeBoost.put(7, 10f);
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
