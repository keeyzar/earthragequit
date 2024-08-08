package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.ERQStats;

/**
 * Created by Keeyzar on 12.02.2016.
 */
public class StatsSaferLoader{
    private StatsVerwalter statsVerwalter;

    private final String PREFERENCES = "STATS_PREF";

    StatsSaferLoader(StatsVerwalter statsVerwalter){
        this.statsVerwalter = statsVerwalter;
        loadStats();
    }

    private void loadStats() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        Array<ERQStats> statsArray = statsVerwalter.getStatsArray();
        for(ERQStats stats : statsArray){
            int lev = prefs.getInteger(stats.getIdentifierName(), 0);
            stats.setCurrentLevel(lev);
            boolean lock = prefs.getBoolean(stats.getIdentifierName() + "_lock", true);
            stats.setLocked(lock);
        }


    }

    public void safe() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        Array<ERQStats> statsArray = statsVerwalter.getStatsArray();
        for(ERQStats stats : statsArray){
            prefs.putInteger(stats.getIdentifierName(), stats.getCurrentLevel());
            prefs.putBoolean(stats.getIdentifierName() + "_lock", stats.isLocked());
        }
        prefs.flush();
    }

    public void reset() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        prefs.clear();
        prefs.flush();
    }

    public void load() {
        loadStats();
    }
}
