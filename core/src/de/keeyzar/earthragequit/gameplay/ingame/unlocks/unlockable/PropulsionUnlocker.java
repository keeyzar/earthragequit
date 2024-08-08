package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class PropulsionUnlocker extends Unlock{
    private ERQGame game;

    public PropulsionUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "Propulsion UNLOCKED!";
        unlockerText = "You unlocked a new stat. You can boost your propulsion by now!";
    }

    @Override
    public void unlock() {
        game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_ROCKET_PROPULSION).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !        game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_ROCKET_PROPULSION).isLocked();
    }


    @Override
    public boolean shouldBeDisplayed() {
        return true;
    }

    @Override
    public int getType() {
        return UpgradeMainDialog.SITE_STATS;
    }
}
