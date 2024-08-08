package de.keeyzar.earthragequit.achievements.all.upgrade_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.07.2016.
 */
public class FirstUpgradeAchiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private int coinBelohnung;
    private boolean finished = false;

    public FirstUpgradeAchiev(ERQGame game){
        this.game = game;
        coinBelohnung = 10;
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
        return LANG.format("first_upgrade_text");
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
        return LANG.format("first_upgrade_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("first_upgrade_short_reward_text", coinBelohnung);
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        if(!finished && game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_FUEL).getCurrentLevel() >= 0){
            applyAchievement();
            setFinished(true);
        }
    }

    @Override
    public int getProgress() {
        return isFinished() ? 100 : 0;
    }

    @Override
    public boolean shouldHide() {
        return false;
    }
}
