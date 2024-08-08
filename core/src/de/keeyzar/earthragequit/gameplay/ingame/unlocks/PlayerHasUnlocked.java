package de.keeyzar.earthragequit.gameplay.ingame.unlocks;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author = Keeyzar on 12.03.2016
 */
public interface PlayerHasUnlocked {

    /**
     * @return long unlockable text.
     */
    String getUnlockText();

    /**
     * @return short Title for unlock
     */
    String getUnlockTitel();

    /**
     * unlock this!
     */
    void unlock();

    /**
     * is this unlockable already unlocked?
     * @return
     */
    boolean isUnlocked();

    /**
     * whether or not it should be displayed, or it is only used as a trigger to open e.g. a new level
     * @return
     */
    boolean shouldBeDisplayed();

    /**
     * May be null
     */
    Sprite getImage();

    /**
     * @return which type it is (skills, skins or stats, see at {@link de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog}
     */
    int getType();
}
