package de.keeyzar.earthragequit.achievements;

import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.Achiev_DemoEnd;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.achievements.all.bee_achiev.Hit5Bees;
import de.keeyzar.earthragequit.achievements.all.coin_achiev.*;
import de.keeyzar.earthragequit.achievements.all.level_achiev.FirstBossAchiev;
import de.keeyzar.earthragequit.achievements.all.level_achiev.SecondBossAchiev;
import de.keeyzar.earthragequit.achievements.all.level_achiev.ThirdBossAchiev;
import de.keeyzar.earthragequit.achievements.all.plat_achiev.Hit100Platforms;
import de.keeyzar.earthragequit.achievements.all.plat_achiev.Hit1Platforms;
import de.keeyzar.earthragequit.achievements.all.plat_achiev.Hit50Platforms;
import de.keeyzar.earthragequit.achievements.all.unlockable_achievs.SkillPlace_4;
import de.keeyzar.earthragequit.achievements.all.unlockable_achievs.TreasureSkinAchiev;
import de.keeyzar.earthragequit.achievements.all.upgrade_achiev.Achiev_MaxFuel;
import de.keeyzar.earthragequit.achievements.all.upgrade_achiev.FirstUpgradeAchiev;
import de.keeyzar.earthragequit.saving.Safeable;

import static de.keeyzar.earthragequit.achievements.all.AVars.*;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class AchievementVerwalter implements Safeable {
    private ObjectIntMap<Integer> counterMap;
    ObjectMap<Integer, Achievement> achievementMap;
    AchievementSaferLoader achievementSaferLoader;

    public AchievementVerwalter(ERQGame game){
        achievementMap = new ObjectMap<Integer, Achievement>();
        achievementMap.put(BEES_5, new Hit5Bees(game, this));
        achievementMap.put(PLATFORM_1, new Hit1Platforms(game, this));
        achievementMap.put(PLATFORM_50, new Hit50Platforms(game, this));
        achievementMap.put(PLATFORM_100, new Hit100Platforms(game, this));
        achievementMap.put(COLL_COIN_50, new Collect50Coins(game, this));
        achievementMap.put(COLL_COIN_100, new Collect100Coins(game, this));
        achievementMap.put(COLL_COIN_200, new Collect200Coins(game, this));
        achievementMap.put(COLL_COIN_500, new Collect500Coins(game, this));
        achievementMap.put(COLL_COIN_1000, new Collect1000Coins(game, this));
        achievementMap.put(UPGRADE_ACHIEV_FIRST, new FirstUpgradeAchiev(game));
        achievementMap.put(UPGRADE_FUEL_MAX, new Achiev_MaxFuel(game));
        achievementMap.put(BOSS_1_KILLED, new FirstBossAchiev(game));
        achievementMap.put(BOSS_2_KILLED, new SecondBossAchiev(game));
        achievementMap.put(BOSS_3_KILLED, new ThirdBossAchiev(game));
        achievementMap.put(DEMO_FINISHED, new Achiev_DemoEnd(game));
        achievementMap.put(SKIN_UNLOCK_1, new TreasureSkinAchiev(game));
        achievementMap.put(SKILLPLACE_4, new SkillPlace_4(game));

        counterMap = new ObjectIntMap<Integer>();
        counterMap.put(OVERALL_COLLECTED_COINS, 0);
        counterMap.put(OVERALL_COLLECTED_COINS_VALUE, 0);
        counterMap.put(OVERALL_HIT_BEES, 0);
        counterMap.put(OVERALL_COLL_FUEL, 0);
        counterMap.put(OVERALL_HIT_PLATFORMS, 0);
        counterMap.put(OVERALL_HIT_SCHROTT, 0);
        counterMap.put(OVERALL_HIT_INVISIBLE_CREEP, 0);
        counterMap.put(COUNTER_DEATH, 0);
        counterMap.put(COUNTER_FUEL_EMPTY, 0);

        achievementSaferLoader = new AchievementSaferLoader(this);
        achievementSaferLoader.load();
    }

    public void addCoin(float value) {
        counterMap.put(OVERALL_COLLECTED_COINS_VALUE, counterMap.get(OVERALL_COLLECTED_COINS_VALUE, 0) + (int)value);
        counterMap.put(OVERALL_COLLECTED_COINS, counterMap.get(OVERALL_COLLECTED_COINS, 0) + 1);
        achievementMap.get(COLL_COIN_50).checkConditionsAndApplyIfTrue();
        achievementMap.get(COLL_COIN_100).checkConditionsAndApplyIfTrue();
        achievementMap.get(COLL_COIN_200).checkConditionsAndApplyIfTrue();
        achievementMap.get(COLL_COIN_500).checkConditionsAndApplyIfTrue();
        achievementMap.get(COLL_COIN_1000).checkConditionsAndApplyIfTrue();
    }



    public int getStat(int statId){
        return counterMap.get(statId, 0);
    }

    public void setStat(int statId, int value){
        counterMap.put(statId, value);
    }

    public void addStat(int statId, int value){
        counterMap.put(statId, counterMap.get(statId, 0) + value);
        switch (statId){
            case OVERALL_HIT_PLATFORMS:
                checkForPlatformAchievements();
                break;
            case OVERALL_COLL_FUEL:
//                checkForFuelAchievements();
                break;
            case OVERALL_HIT_BEES:
                checkForBeeAchievements();
                break;
            case OVERALL_HIT_ION_CLOUD:
//                checkForIonCloudAchievements();
                break;
            case OVERALL_HIT_SCHROTT:
//                checkForSchrottAchievements();
                break;
            case OVERALL_HIT_INVISIBLE_CREEP:
//                checkForInvisibleCreepAchievements();
                break;
            case COUNTER_DEATH:
//                checkForDeathAchievements();
                break;
            case COUNTER_FUEL_EMPTY:
//                checkForFuelEmptyAchievements();
                break;
        }
    }

    private void checkForPlatformAchievements() {
        achievementMap.get(PLATFORM_1).checkConditionsAndApplyIfTrue();
        achievementMap.get(PLATFORM_50).checkConditionsAndApplyIfTrue();
        achievementMap.get(PLATFORM_100).checkConditionsAndApplyIfTrue();
    }

    private void checkForBeeAchievements() {
        achievementMap.get(BEES_5).checkConditionsAndApplyIfTrue();
    }

    public ObjectMap<Integer, Achievement> getAchievementMap() {
        return achievementMap;
    }

    public ObjectIntMap<Integer> getCounterMap() {
        return counterMap;
    }

    @Override
    public void save() {
        achievementSaferLoader.safe();
    }

    @Override
    public void reset() {
        achievementSaferLoader.reset();
        achievementSaferLoader.load();
    }
}
