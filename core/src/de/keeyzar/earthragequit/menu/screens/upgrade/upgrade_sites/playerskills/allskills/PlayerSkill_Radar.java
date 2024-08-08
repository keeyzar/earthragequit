package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Ingame_Radar;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.07.2015
 */
public class PlayerSkill_Radar extends Def_PlayerSkill {

    public PlayerSkill_Radar() {
        identifier_name = SkillsVars.RADAR;
        displayName = "radar_skill_title";
        maxLevel = 7;
        isPassive = true;

        levelToCost = new IntMap<Integer>();
        levelToCost.put(1, 50);
        levelToCost.put(2, 100);
        levelToCost.put(3, 150);
        levelToCost.put(4, 300);
        levelToCost.put(5, 450);
        levelToCost.put(6, 600);
        levelToCost.put(7, 750);


        levelToBoostValue = new IntFloatMap();
        levelToBoostValue.put(1, 15f); //10
        levelToBoostValue.put(2, 18f);
        levelToBoostValue.put(3, 21f); // 16
        levelToBoostValue.put(4, 25f);
        levelToBoostValue.put(5, 30f);
        levelToBoostValue.put(6, 35f);
        levelToBoostValue.put(7, 40f);

    }

    @Override
    public String getShortInfoText() {
        return  LANG.format("radar_short_info");
    }

    @Override
    public String getSkillDescribingText() {
        return  LANG.format("radar_long_info");
    }

    @Override
    public IngameSkill getIngameSkill() {
        return new Ingame_Radar();
    }

}
