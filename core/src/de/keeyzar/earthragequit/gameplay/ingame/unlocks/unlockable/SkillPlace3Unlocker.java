package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class SkillPlace3Unlocker extends Unlock{
    private ERQGame game;

    public SkillPlace3Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skillplace_3_title";
        unlockerText = "Oh great. You got a new Skillplace. Congratz!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().unlock(3);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().isLocked(3);
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
