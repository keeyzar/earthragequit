package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.UnlockVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Level4Unlocker extends Unlock{
    private ERQGame game;

    public Level4Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = UnlockVars.LEVEL_TITEL;
        unlockerText = "Congratulations, you're ready for your next Journey";
    }

    @Override
    public void unlock() {
        game.getLevelVerwalter().unlockLevel(4);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getLevelVerwalter().getLevelLockMap().get(4);
    }
}
