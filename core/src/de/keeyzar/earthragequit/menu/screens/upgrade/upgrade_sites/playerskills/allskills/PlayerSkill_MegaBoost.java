package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.Ingame_Megaboost;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.07.2015
 */
public class PlayerSkill_MegaBoost extends Def_PlayerSkill {
    private Ingame_Megaboost ingame_megaboost;

    public PlayerSkill_MegaBoost() {
        //configuration
        identifier_name = SkillsVars.MEGABOOST;
        displayName = "megaboost_skill_title";
        maxLevel = 6;

        levelToCost = new IntMap<Integer>();
        levelToCost.put(1, 20);
        levelToCost.put(2, 40);
        levelToCost.put(3, 70);
        levelToCost.put(4, 100);
        levelToCost.put(5, 140);
        levelToCost.put(6, 300);

        levelToBoostValue = new IntFloatMap();
        levelToBoostValue.put(1, 0.5f);
        levelToBoostValue.put(2, 0.75f);
        levelToBoostValue.put(3, 1f);
        levelToBoostValue.put(4, 1.5f);
        levelToBoostValue.put(5, 2f);
        levelToBoostValue.put(6, 3f);
    }

    @Override
    public String getShortInfoText() {
        String x;
        if(isMaxLevel()){
            x = LANG.format("megaboost_short_info_current_time", getBoostForCurrentLevel());
        } else if(getCurrentLevel() == 0) {
            x = LANG.format("megaboost_short_info_next_level_time", getBoostForNextLevel());
        } else {
            x = LANG.format("megaboost_short_info_current_time", getBoostForCurrentLevel()) + "\n" +
                    LANG.format("megaboost_short_info_next_level_time", getBoostForNextLevel());
        }

        return LANG.format("megaboost_short_info") + x ;
    }

    @Override
    public String getSkillDescribingText() {
        return  LANG.format("megaboost_long_info");
    }

    @Override
    public void activate(Player player) {
        player.getPlayerIngameSkills().getActiveIngameSkills(SkillsVars.MEGABOOST).activate();
    }

    @Override
    public float getProgress(Player player) {
        ingame_megaboost = (Ingame_Megaboost) player.getPlayerIngameSkills().getActiveIngameSkills(SkillsVars.MEGABOOST);
        return ingame_megaboost.getProgress();
    }

    @Override
    public void dispose() {
        ingame_megaboost = null;
    }

    @Override
    public IngameSkill getIngameSkill() {
        ingame_megaboost = new Ingame_Megaboost();
        return ingame_megaboost;
    }

}
