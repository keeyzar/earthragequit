package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class RadarUnlocker extends Unlock{
    private ERQGame game;

    public RadarUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skill_radar_title";
        unlockerText = "Yay, you unlocked a new skill, a radar!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().getSkillByName(SkillsVars.RADAR).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().getSkillByName(SkillsVars.RADAR).isLocked();
    }


    @Override
    public boolean shouldBeDisplayed() {
        return true;
    }

    @Override
    public int getType() {
        return UpgradeMainDialog.DIALOG_SKILLS_IF_FREE;
    }
}
