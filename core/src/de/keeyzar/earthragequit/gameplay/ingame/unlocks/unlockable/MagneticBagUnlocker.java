package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.03.2016
 */
public class MagneticBagUnlocker extends Unlock{
    private ERQGame game;

    public MagneticBagUnlocker(ERQGame game){
        this.game = game;
        unlockTitel = LANG.format("unlock_stat_magnetic_moneybags_title");
        unlockerText = "Magnetic moneybags I heard?";
    }

    @Override
    public void unlock() {
        game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_MAGNETIC_MONEYBAG).setLocked(false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_MAGNETIC_MONEYBAG).isLocked();
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
