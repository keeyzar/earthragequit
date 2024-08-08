package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class MegaboostUnlocker extends Unlock{
    private ERQGame game;

    public MegaboostUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skill_megaboost_title";
        unlockerText = "M to the egaboost. you Unlocked a new skill man - Megaboost.";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().getSkillByName(SkillsVars.MEGABOOST).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().getSkillByName(SkillsVars.MEGABOOST).isLocked();
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
