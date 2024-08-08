package de.keeyzar.earthragequit.achievements.all.plat_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.04.2016
 */
public abstract class Platt_Achiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private de.keeyzar.earthragequit.achievements.AchievementVerwalter achievementVerwalter;

    private boolean finished = false;

    private int totalPlatsToHit;
    private int coinBelohnung;

    public Platt_Achiev(ERQGame game, de.keeyzar.earthragequit.achievements.AchievementVerwalter achievementVerwalter, int totalPlatsToHit, int coinBelohnung){
        this.game = game;
        this.achievementVerwalter = achievementVerwalter;
        this.totalPlatsToHit = totalPlatsToHit;
        this.coinBelohnung = coinBelohnung;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("plat_text", totalPlatsToHit, coinBelohnung);
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
        return LANG.format("plat_title", totalPlatsToHit);
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        if(!finished && achievementVerwalter.getStat(AVars.OVERALL_HIT_PLATFORMS) >= totalPlatsToHit){
            applyAchievement();
            setFinished(true);
        }
    }

    @Override
    public int getProgress() {
        return (int) Math.min(100f, achievementVerwalter.getStat(AVars.OVERALL_HIT_PLATFORMS) / (float)totalPlatsToHit * 100);
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("plat_short_reward_text", coinBelohnung);
    }

    @Override
    public boolean shouldHide() {
        return false;
    }
}
