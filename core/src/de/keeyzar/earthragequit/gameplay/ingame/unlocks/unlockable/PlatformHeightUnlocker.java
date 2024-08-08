package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 14.03.2016
 */
public class PlatformHeightUnlocker extends Unlock{
    private ERQGame game;

    public PlatformHeightUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_stat_trampoline_title";
        unlockerText = "Uh. Increase your platform boost HERE.";
    }

    @Override
    public void unlock() {
        game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_PLATFORM_JUMP_HEIGHT).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_PLATFORM_JUMP_HEIGHT).isLocked();
    }

    @Override
    public boolean shouldBeDisplayed() {
        return true;
    }

    @Override
    public Sprite getImage() {
        return ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.SKILL_STAT);
    }

    @Override
    public int getType() {
        return UpgradeMainDialog.SITE_STATS;
    }
}
