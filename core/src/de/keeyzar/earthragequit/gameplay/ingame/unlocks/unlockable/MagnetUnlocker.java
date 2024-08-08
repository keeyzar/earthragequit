package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

/**
 * @author = Keeyzar on 14.03.2016
 */
public class MagnetUnlocker extends Unlock{
    private ERQGame game;

    public MagnetUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skill_magnet_title";
        unlockerText = "Dude, you know what? You just got a new Skill! A Magnet! Congratulations!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().getSkillByName(SkillsVars.MAGNET).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().getSkillByName(SkillsVars.MAGNET).isLocked();
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
