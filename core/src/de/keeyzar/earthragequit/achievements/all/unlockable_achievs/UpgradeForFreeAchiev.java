package de.keeyzar.earthragequit.achievements.all.unlockable_achievs;

import de.keeyzar.earthragequit.ERQGame;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class UpgradeForFreeAchiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;

    private boolean finished = false;


    public UpgradeForFreeAchiev(ERQGame game){
        this.game = game;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("upgrade_for_free_text");
    }

    @Override
    public void applyAchievement() {
        game.getGlobalPlayerInformation().addFreeUpgrades();
        game.newAchievement(this);
    }

    @Override
    public int coinsGatheredByAchievement() {
        return 0;
    }

    @Override
    public String getTitle() {
        return LANG.format("upgrade_for_free_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("upgrade_for_free_short_reward_text");
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        applyAchievement();
    }

    @Override
    public int getProgress() {
        return finished ? 100 : 0;
    }

    @Override
    public boolean shouldHide() {
        return true;
    }
}
