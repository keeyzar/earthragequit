package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.UnlockVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Level9Unlocker extends Unlock{
    private ERQGame game;

    public Level9Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = UnlockVars.LEVEL_TITEL;
        unlockerText = "Good Job. You unlocked a new Level";
    }
    @Override
    public void unlock() {
        game.getLevelVerwalter().unlockLevel(9);
    }

    @Override
    public boolean isUnlocked() {
        return !game.getLevelVerwalter().getLevelLockMap().get(9);
    }
}
