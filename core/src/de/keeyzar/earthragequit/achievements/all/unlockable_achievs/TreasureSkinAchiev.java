package de.keeyzar.earthragequit.achievements.all.unlockable_achievs;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins.SkinVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class TreasureSkinAchiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;

    private boolean finished = false;


    public TreasureSkinAchiev(ERQGame game){
        this.game = game;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return  LANG.format("treasure_skin_text");
    }

    @Override
    public void applyAchievement() {
        if(!finished) {
            game.getSkinVerwalter().getShipSkinById(SkinVars.SKIN_3).setUnlocked(true);
            game.newAchievement(this);
        }
    }

    @Override
    public int coinsGatheredByAchievement() {
        return 0;
    }

    @Override
    public String getTitle() {
        return LANG.format("treasure_skin_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("treasure_skin_short_reward_text");
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        if(!finished){
            applyAchievement();
            setFinished(true);
        }
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
