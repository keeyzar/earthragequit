package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.UnlockVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Level2Unlocker extends Unlock{
    private ERQGame game;

    public Level2Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = UnlockVars.LEVEL_TITEL;
        unlockerText = "Nice - You've finished your first Level - You've unlocked the next one!";
    }

    @Override
    public void unlock() {
        game.getLevelVerwalter().unlockLevel(2);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getLevelVerwalter().getLevelLockMap().get(2);
    }
}
