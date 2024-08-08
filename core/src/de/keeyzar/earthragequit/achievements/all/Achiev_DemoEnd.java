package de.keeyzar.earthragequit.achievements.all;

import de.keeyzar.earthragequit.ERQGame;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.07.2016.
 */
public class Achiev_DemoEnd implements Achievement {
    private ERQGame game;
    private int coinBelohnung;
    private boolean finished = false;

    public Achiev_DemoEnd(ERQGame game){
        this.game = game;
        coinBelohnung = 1000;
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
        return LANG.format("demo_end_text");
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
        return LANG.format("demo_end_title");
    }

    @Override
    public String getShortRewardDesc() {
        return LANG.format("demo_end_short_reward_text", coinBelohnung);
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
