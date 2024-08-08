package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class SkillPlace4Unlocker extends Unlock{
    private ERQGame game;

    public SkillPlace4Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skillplace_4_title";
        unlockerText = "Last but not least. Have fun with the last skillplace";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().unlock(4);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().isLocked(4);
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
