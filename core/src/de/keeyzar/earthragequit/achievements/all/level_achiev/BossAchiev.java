package de.keeyzar.earthragequit.achievements.all.level_achiev;

import de.keeyzar.earthragequit.ERQGame;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.07.2016.
 */
public abstract class BossAchiev implements de.keeyzar.earthragequit.achievements.all.Achievement {
    private ERQGame game;
    private int coinBelohnung;
    private boolean finished = false;

    public BossAchiev(ERQGame game, int coinBelohnung) {
        this.game = game;
        this.coinBelohnung = coinBelohnung;
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
    public void applyAchievement() {
        if (!finished) {
            game.getGlobalPlayerInformation().addCoins(coinBelohnung);
            game.newAchievement(this);
        }
    }

    @Override
    public int coinsGatheredByAchievement() {
        return coinBelohnung;
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("boss_short_reward_text", coinBelohnung);
    }

    @Override
    public void checkConditionsAndApplyIfTrue() {
        if (!finished) {
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
