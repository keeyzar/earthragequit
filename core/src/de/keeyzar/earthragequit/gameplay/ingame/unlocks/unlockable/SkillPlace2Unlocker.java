package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class SkillPlace2Unlocker extends Unlock{
    private ERQGame game;

    public SkillPlace2Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skillplace_2_title";
        unlockerText = "Do you know a person who has 2 skillplaces? OH YOU? CONGRATZ!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().unlock(2);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().isLocked(2);
    }

    @Override
    public boolean shouldBeDisplayed() {
        return true;
    }

    @Override
    public int getType() {
        return UpgradeMainDialog.SITE_SKILLS;
    }
}
