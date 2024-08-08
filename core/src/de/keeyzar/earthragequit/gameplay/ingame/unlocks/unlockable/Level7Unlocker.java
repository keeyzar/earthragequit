package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.UnlockVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Level7Unlocker extends Unlock{
    private ERQGame game;

    public Level7Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = UnlockVars.LEVEL_TITEL;
        unlockerText = "Good Job. You unlocked a new Level";
    }
    @Override
    public void unlock() {
        game.getLevelVerwalter().unlockLevel(7);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getLevelVerwalter().getLevelLockMap().get(7);
    }
}
