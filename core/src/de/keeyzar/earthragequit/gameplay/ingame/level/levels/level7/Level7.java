package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level7;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level8Unlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.MagneticBagUnlocker;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 *
 */
public class Level7 extends LevelDefining {



    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new Level7EntitieCreator().createEntities(world, stage, getWorldHeight(), this, player, levelDescription.coinValue);
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
        unlockeds.add(new Level8Unlocker(game));
        unlockeds.add(new MagneticBagUnlocker(game));
        return unlockeds;
    }

    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription();
        levelDescription.width = 100;
        levelDescription.height = 400;
        levelDescription.level = 7;
        levelDescription.coinValue = 5;
        levelDescription.levelTmxPath = LevelVars.LEVEL_RED;
        levelDescription.levelName = LANG.format("7_level_title");
        return levelDescription;
    }

}