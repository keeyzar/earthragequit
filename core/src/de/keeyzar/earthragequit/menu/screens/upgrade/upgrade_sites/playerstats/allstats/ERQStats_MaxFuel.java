package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_MaxFuel extends ERQStats {

    public ERQStats_MaxFuel(){
        IDENTIFIER_NAME = STAT_VARS.STAT_FUEL;
        displayName = "stats_fuel_title";
        shortDescription =  "stats_fuel_desc_short";
        longDescription =  "stats_fuel_desc_long";

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 5);
        levelToCosts.put(2, 7);
        levelToCosts.put(3, 9);
        levelToCosts.put(4, 15);
        levelToCosts.put(5, 20);
        levelToCosts.put(6, 25);
        levelToCosts.put(7, 30);
        levelToCosts.put(8, 50);
        levelToCosts.put(9, 70);
        levelToCosts.put(10, 90);
        levelToCosts.put(11, 110);
        levelToCosts.put(12, 150);
        levelToCosts.put(13, 200);
        levelToCosts.put(14, 250);
        levelToCosts.put(15, 350);

        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 10f);
        levelToAttributeBoost.put(2, 15f);
        levelToAttributeBoost.put(3, 20f);
        levelToAttributeBoost.put(4, 25f);
        levelToAttributeBoost.put(5, 30f);
        levelToAttributeBoost.put(6, 35f);
        levelToAttributeBoost.put(7, 40f);
        levelToAttributeBoost.put(8, 45f);
        levelToAttributeBoost.put(9, 50f);
        levelToAttributeBoost.put(10, 55f);
        levelToAttributeBoost.put(11, 60f);
        levelToAttributeBoost.put(12, 65f);
        levelToAttributeBoost.put(13, 70f);
        levelToAttributeBoost.put(14, 80f);
        levelToAttributeBoost.put(15, 90f);
    }
}
