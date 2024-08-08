package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 02.04.2016
 */
public abstract class Unlock implements PlayerHasUnlocked {
    public String unlockerText;
    public String unlockTitel;


    @Override
    public String getUnlockText() {
        return ERQAssets.LANG.format(unlockerText);
    }

    @Override
    public String getUnlockTitel() {
        return ERQAssets.LANG.format(unlockTitel);
    }

    /**
     * override, if you want to be displayed
     * @return true to display, false to not display
     */
    @Override
    public boolean shouldBeDisplayed() {
        return false;
    }

    @Override
    public Sprite getImage() {
        return ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.SKILL_PLACE);
    }

    @Override
    public int getType() {
        return 0;
    }
}
