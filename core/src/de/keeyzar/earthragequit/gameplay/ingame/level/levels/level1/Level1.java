package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level1;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level2Unlocker;
import de.keeyzar.earthragequit.tutorial.TVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level1 extends LevelDefining {

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new LevelOneEntitieCreator().createEntities(game, world, stage, getWorldHeight(), levelDescription.coinValue);
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
        Array<PlayerHasUnlocked> playerUnlocks = new Array<PlayerHasUnlocked>();
        playerUnlocks.add(new Level2Unlocker(game));
        return playerUnlocks;
    }

    @Override
    public void playerFinishedMap() {
        game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.UPGRADE_INTRODUCTION, true);
    }

    @Override
    public boolean isStartingBoostDisabled() {
        return true;
    }

    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription();
        levelDescription.width = 100;
        levelDescription.height = 75;
        levelDescription.level = 1;
        levelDescription.coinValue = 1;
        levelDescription.levelTmxPath = LevelVars.LEVEL_BLUE;
        levelDescription.levelName =  LANG.format("1_level_title");
        return levelDescription;
    }

}
