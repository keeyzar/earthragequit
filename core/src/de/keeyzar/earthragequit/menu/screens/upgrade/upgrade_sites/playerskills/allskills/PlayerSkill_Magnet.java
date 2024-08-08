package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntMap;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.Ingame_Magnet;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.07.2015
 */
public class PlayerSkill_Magnet extends Def_PlayerSkill {

    public PlayerSkill_Magnet() {
        identifier_name = SkillsVars.MAGNET;
        displayName = "magnet_skill_title";
        maxLevel = 6;
        isPassive = true;

        levelToCost = new IntMap<Integer>();
        levelToCost.put(1, 20);
        levelToCost.put(2, 40);
        levelToCost.put(3, 80);
        levelToCost.put(4, 150);
        levelToCost.put(5, 250);
        levelToCost.put(6, 450);

        levelToBoostValue = new IntFloatMap();
        levelToBoostValue.put(1, 1f); //1
        levelToBoostValue.put(2, 1.5f);
        levelToBoostValue.put(3, 2f); // 2
        levelToBoostValue.put(4, 2.5f);
        levelToBoostValue.put(5, 3f);
        levelToBoostValue.put(6, 4.5f);
    }


    @Override
    public String getShortInfoText() {
        return  LANG.format("magnet_short_info");
    }

    @Override
    public String getSkillDescribingText() {
        return  LANG.format("magnet_long_info");
    }

    @Override
    public void activate(Player player) {
        //Nothing, da passiv, muss anderst implementiert werden
    }

    @Override
    public float getProgress(Player player) {
        //not used
        return 0;
    }

    @Override
    public void dispose() {
        //do nothing
    }

    @Override
    public IngameSkill getIngameSkill() {
        return new Ingame_Magnet();
    }

}
