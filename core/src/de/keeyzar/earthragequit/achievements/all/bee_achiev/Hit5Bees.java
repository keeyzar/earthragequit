package de.keeyzar.earthragequit.achievements.all.bee_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementVerwalter;
import de.keeyzar.earthragequit.achievements.all.AVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class Hit5Bees implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private AchievementVerwalter achievementVerwalter;

    private boolean finished = false;

    private int coinBelohnung;
    private int beesToHit;

    public Hit5Bees(ERQGame game, AchievementVerwalter achievementVerwalter){
        this.game = game;
        this.achievementVerwalter = achievementVerwalter;

        coinBelohnung = 30;
        beesToHit = 5;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("flyflys_hit_text", coinBelohnung);
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
        return LANG.format("flyflys_hit_title", beesToHit);
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("flyflys_short_reward_text", coinBelohnung);
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        if(!finished && achievementVerwalter.getStat(AVars.OVERALL_HIT_BEES) >= beesToHit){
            applyAchievement();
            setFinished(true);
        }
    }

    @Override
    public int getProgress() {
        return (int) Math.min(100f, achievementVerwalter.getStat(AVars.OVERALL_HIT_BEES) / (float)beesToHit * 100);
    }

    @Override
    public boolean shouldHide() {
        return false;
    }
}
