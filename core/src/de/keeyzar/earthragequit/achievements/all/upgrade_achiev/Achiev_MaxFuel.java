package de.keeyzar.earthragequit.achievements.all.upgrade_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.ERQStats;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.07.2016.
 */
public class Achiev_MaxFuel implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private int coinBelohnung;
    private boolean finished = false;

    public Achiev_MaxFuel(ERQGame game){
        this.game = game;
        coinBelohnung = 100;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("max_fuel_text");
    }

    @Override
    public void applyAchievement() {
        if(!finished) {
            game.getGlobalPlayerInformation().addCoins(coinBelohnung);
            game.newAchievement(this);
        }
    }

    @Override
    public int coinsGatheredByAchievement() {
        return coinBelohnung;
    }

    @Override
    public String getTitle() {
        return LANG.format("max_fuel_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("max_fuel_short_reward_text", coinBelohnung);
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        final ERQStats statByName = game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL);
        if(!finished && statByName.getCurrentLevel() >= statByName.getMaxLevel()){
            applyAchievement();
            setFinished(true);
        }
    }

    @Override
    public int getProgress() {
        ERQStats statByName = game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL);
        return (int) Math.min(100f, ((float)statByName.getCurrentLevel() / (float)statByName.getMaxLevel()) * 100f);
    }

    @Override
    public boolean shouldHide() {
        return false;
    }
}
