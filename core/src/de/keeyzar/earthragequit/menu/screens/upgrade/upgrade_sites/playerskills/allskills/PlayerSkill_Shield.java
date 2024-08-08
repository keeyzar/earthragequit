package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.Ingame_Shield;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.07.2015
 */
public class PlayerSkill_Shield extends Def_PlayerSkill {
    private Ingame_Shield shield;

    public PlayerSkill_Shield() {
        identifier_name = SkillsVars.SHIELD;
        displayName = "shield_skill_title";
        maxLevel = 6;

        levelToCost = new IntMap<Integer>();
        levelToCost.put(1, 15);
        levelToCost.put(2, 40);
        levelToCost.put(3, 100);
        levelToCost.put(4, 300);
        levelToCost.put(5, 800);
        levelToCost.put(6, 1500);

        levelToBoostValue = new IntFloatMap();
        levelToBoostValue.put(1, 5f);
        levelToBoostValue.put(2, 6f);
        levelToBoostValue.put(3, 7f);
        levelToBoostValue.put(4, 9f);
        levelToBoostValue.put(5, 11f);
        levelToBoostValue.put(6, 13f);
    }

    @Override
    public String getShortInfoText() {
        String x;
        if(isMaxLevel()){
            x =  LANG.format("shield_short_info_current_time", getBoostForCurrentLevel());
        } else if(getCurrentLevel() == 0) {
            x =  LANG.format("shield_short_info_next_level_time", getBoostForNextLevel());
        } else {
            x =  LANG.format("shield_short_info_current_time", getBoostForCurrentLevel()) + "\n" +
                    LANG.format("shield_short_info_next_level_time", getBoostForNextLevel());
        }

        return  LANG.format("shield_short_info") + x ;
    }

    @Override
    public String getSkillDescribingText() {
        return  LANG.format("shield_long_info");
    }

    @Override
    public void activate(Player player) {
        shield.activate();
    }

    @Override
    public float getProgress(Player player) {
        return shield.getProgress();
    }

    @Override
    public void dispose() {
        shield = null;
    }

    @Override
    public IngameSkill getIngameSkill() {
        shield = new Ingame_Shield();
        return shield;
    }
}
