package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class ShieldUnlocker extends Unlock{
    private ERQGame game;

    public ShieldUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skill_shield_title";
        unlockerText = "Screw the enemies. You'll shield yourself now!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().getSkillByName(SkillsVars.SHIELD).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().getSkillByName(SkillsVars.SHIELD).isLocked();
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
