package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;

/**
 * This saves some manual chosen Statistics.
 * @author = Keeyzar on 08.03.2016
 */
public class PlayerStatistic {
    private final Player player;
    private final ERQGame game;

    private int coinsBeforeGame;
    private int coinsGathered;
    private int coinValueGatheredIngame;
    private int coinsThroughAchievement;
    private int currLevelPercentage;

    private int beesHit;
    private int platformHit;
    private int cloudHit;
    private boolean radarActive;
    private int schrottHit;
    private int invisibleCreepHit;

    public PlayerStatistic(Player player, ERQGame game) {
        this.player = player;
        this.game = game;

        init();
    }

    private void init() {
        coinsBeforeGame = game.getGlobalPlayerInformation().getCoins();
        coinsGathered = 0;
        coinValueGatheredIngame = 0;
        currLevelPercentage = 0;
        coinsThroughAchievement = 0;
        beesHit = 0;
        invisibleCreepHit = 0;
    }

    //some statistics, that need to be updated every frame
    //like current height;
    public void act() {

        int temp = (int) Math.min(100,  (player.getBody().getPosition().y / player.getRegularWorld().getWorldHeigth()) * 100);
        if (temp > currLevelPercentage){
            currLevelPercentage = temp;
        }
    }

    public int getCoinsBeforeGame() {
        return coinsBeforeGame;
    }

    public void setCoinsBeforeGame(int coinsBeforeGame) {
        this.coinsBeforeGame = coinsBeforeGame;
    }

    public int getCoinsGatheredIngame() {
        return coinsGathered;
    }

    public void setCoinsGathered(int coinsGathered) {
        this.coinsGathered = coinsGathered;
    }

    public int getCoinValueGatheredIngame() {
        return coinValueGatheredIngame;
    }

    public void setCoinValueGatheredIngame(int coinValueGatheredIngame) {
        this.coinValueGatheredIngame = coinValueGatheredIngame;
    }

    public int getCurrLevelPercentage() {
        return currLevelPercentage;
    }

    public void setCurrLevelPercentage(int currLevelPercentage) {
        this.currLevelPercentage = currLevelPercentage;
    }

    public int getCoinsThroughAchievement() {
        return coinsThroughAchievement;
    }

    public void addCoin(int value) {
        coinsGathered += 1;
        coinValueGatheredIngame += value;
        game.getAchievementVerwalter().addCoin(value);
    }

    public void beeHit(){
        beesHit += 1;
        game.getAchievementVerwalter().addStat(AVars.OVERALL_HIT_BEES, 1);
    }

    public void platformHit(){
        platformHit += 1;
        game.getAchievementVerwalter().addStat(AVars.OVERALL_HIT_PLATFORMS, 1);
    }

    public void cloudHit() {
        cloudHit +=1;
        game.getAchievementVerwalter().addStat(AVars.OVERALL_HIT_ION_CLOUD, 1);
    }

    public void schrottHit() {
        schrottHit += 1;
        game.getAchievementVerwalter().addStat(AVars.OVERALL_HIT_SCHROTT, 1);
    }


    /**
     * adds statistic to the hidden entity
     * @param ENTITY
     */
    public void hit(int ENTITY){
        switch (ENTITY){
            case EntityVars.INVISIBLE_CREEP:
                invisibleCreepHit +=1;
                game.getAchievementVerwalter().addStat(AVars.OVERALL_HIT_INVISIBLE_CREEP, 1);
                break;
            case EntityVars.WASP:
                break;
            case EntityVars.COIN:
                break;
            case EntityVars.SCHROTT:
                break;
            case EntityVars.SCHROTT_2:
                break;
            case EntityVars.FUEL:
                break;
            case EntityVars.PLATFORM:
                break;
            case EntityVars.IONIZED_CLOUD:
                break;
        }
    }
}
