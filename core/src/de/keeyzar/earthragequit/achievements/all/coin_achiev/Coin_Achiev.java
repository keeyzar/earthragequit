package de.keeyzar.earthragequit.achievements.all.coin_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementVerwalter;
import de.keeyzar.earthragequit.achievements.all.AVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.04.2016
 */
public abstract class Coin_Achiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private AchievementVerwalter achievementVerwalter;
    private int coinBelohnung;
    private boolean finished = false;
    private int coinsToCollect;


    public Coin_Achiev(ERQGame game, AchievementVerwalter achievementVerwalter, int coinsToCollect, int coinBelohnung){
        this.game = game;
        this.achievementVerwalter = achievementVerwalter;
        this.coinBelohnung = coinBelohnung;
        this.coinsToCollect = coinsToCollect;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getAchievementText() {
        return LANG.format("coin_text", coinsToCollect, coinBelohnung);
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
        return  LANG.format("coin_title", coinsToCollect);
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
        if(!finished && achievementVerwalter.getStat(AVars.OVERALL_COLLECTED_COINS) >= coinsToCollect){
            applyAchievement();
            setFinished(true);
        }
    }

    @Override
    public int getProgress() {
        return (int) Math.min(100f, achievementVerwalter.getStat(AVars.OVERALL_COLLECTED_COINS) / (float)coinsToCollect * 100);
    }

    @Override
    public boolean shouldHide() {
        return false;
    }
}
