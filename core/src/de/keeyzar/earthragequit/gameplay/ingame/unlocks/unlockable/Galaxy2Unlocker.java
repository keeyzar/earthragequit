package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.UnlockVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Galaxy2Unlocker extends Unlock{
    private ERQGame game;

    public Galaxy2Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = UnlockVars.GALAXY_TITEL;
        unlockerText = "You've ready to enter a new Galaxy. Good Job Ronax";
    }
    @Override
    public void unlock() {
        game.getLevelVerwalter().getGalaxyLockMap().put(2, false);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getLevelVerwalter().getGalaxyLockMap().get(2, true);
    }
}
