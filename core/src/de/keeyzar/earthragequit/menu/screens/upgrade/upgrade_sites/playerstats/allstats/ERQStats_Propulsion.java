package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class ERQStats_Propulsion extends ERQStats {

    public ERQStats_Propulsion(){
        IDENTIFIER_NAME = STAT_VARS.STAT_ROCKET_PROPULSION;
        setLocked(false);
        shortDescription = "Decrease time til fullspeed";
        longDescription = "Propulsion DECREASES the TIME to REACH MAXIMUM speed. If you get pushed downwards, you'll recover faster. Additionally it'll improve your steering";

        levelToCosts = new IntMap<Integer>();
        levelToCosts.put(1, 10);
        levelToCosts.put(2, 15);
        levelToCosts.put(3, 20);
        levelToCosts.put(4, 50);
        levelToCosts.put(5, 100);
        levelToCosts.put(6, 300);
        levelToCosts.put(7, 10000);
        levelToCosts.put(8, 20000);
        levelToCosts.put(9, 50000);
        levelToCosts.put(10, 150000);


        levelToAttributeBoost = new IntFloatMap();
        levelToAttributeBoost.put(1, 2f);
        levelToAttributeBoost.put(2, 3f);
        levelToAttributeBoost.put(3, 5f);
        levelToAttributeBoost.put(4, 8f);
        levelToAttributeBoost.put(5, 10f);
        levelToAttributeBoost.put(6, 15f);
        levelToAttributeBoost.put(7, 3.3f);
        levelToAttributeBoost.put(8, 4f);
        levelToAttributeBoost.put(9, 20f);
        levelToAttributeBoost.put(10, 50f);
    }


    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
