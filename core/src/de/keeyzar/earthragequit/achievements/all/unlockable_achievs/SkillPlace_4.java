package de.keeyzar.earthragequit.achievements.all.unlockable_achievs;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.SkillPlace4Unlocker;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class SkillPlace_4 implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;

    private boolean finished = false;


    public SkillPlace_4(ERQGame game){
        this.game = game;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("skillplace_4_text");
    }

    @Override
    public void applyAchievement() {
        if(!finished) {
            new SkillPlace4Unlocker(game).unlock();
            game.newAchievement(this);
        }
    }

    @Override
    public int coinsGatheredByAchievement() {
        return 0;
    }

    @Override
    public String getTitle() {
        return LANG.format("skillplace_4_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("skillplace_4_short_reward_text");
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
