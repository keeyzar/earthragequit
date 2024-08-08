package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats;

import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.*;
import de.keeyzar.earthragequit.saving.Safeable;

/**
 * @author = Keeyzar on 12.02.2016.
 */
public class StatsVerwalter implements Safeable {
    Array<ERQStats> statsArray;
    StatsSaferLoader statsSaferLoader;

    public StatsVerwalter(){
        statsArray = new Array<ERQStats>();
        statsArray.add(new ERQStats_MaxFuel());
        statsArray.add(new ERQStats_Plating());
        statsArray.add(new ERQStats_MaximumSpeed());
        statsArray.add(new ERQStats_PlatformImprovement());
        statsArray.add(new ERQStats_BagsMagnetic());
        statsArray.add(new ERQStats_Propulsion());
        statsSaferLoader = new StatsSaferLoader(this);

        //FIXME REMOVE BEFORE RELEASE
//        getStatByName(ERQStats_Vars.STAT_FUEL).setCurrentLevel(1);
//
//        getStatByName(ERQStats_Vars.STAT_ROCKET_MAXIMUM_SPEED).setLocked(false);
//        getStatByName(ERQStats_Vars.STAT_ROCKET_MAXIMUM_SPEED).setCurrentLevel(10);
//        getStatByName(ERQStats_Vars.STAT_FUEL).setLocked(false);
//        getStatByName(ERQStats_Vars.STAT_FUEL).setCurrentLevel(7);
//        getStatByName(ERQStats_Vars.STAT_PLATING).setLocked(false);
//        getStatByName(ERQStats_Vars.STAT_PLATING).setCurrentLevel(2);
    }

    public Array<ERQStats> getStatsArray() {
        return statsArray;
    }

    public ERQStats getStatByName(String name){
        for(ERQStats stats : statsArray){
            if(stats.getIdentifierName().equals(name)){
                return stats;
            }
        }
        throw new RuntimeException("You wanted to find a stat, which is not registered.");
    }

    @Override
    public void save() {
        statsSaferLoader.safe();
    }

    @Override
    public void reset() {
        statsSaferLoader.reset();
        statsSaferLoader.load();
    }
}
