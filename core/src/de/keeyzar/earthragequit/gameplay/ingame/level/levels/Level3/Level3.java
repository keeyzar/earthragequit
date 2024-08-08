package de.keeyzar.earthragequit.gameplay.ingame.level.levels.Level3;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level4Unlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.MagnetUnlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.PlatformHeightUnlocker;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level3 extends LevelDefining {

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new Level3EntitieCreator().createEntities(world, stage, getWorldHeight(), levelDescription.coinValue);
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
        Array<PlayerHasUnlocked> array = new Array<PlayerHasUnlocked>();
        array.add(new MagnetUnlocker(game));
        array.add(new PlatformHeightUnlocker(game));
        array.add(new Level4Unlocker(game));
        return array;
    }

    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription();
        levelDescription.width = 100;
        levelDescription.height = 150;
        levelDescription.level = 3;
        levelDescription.coinValue = 2;
        levelDescription.levelTmxPath = LevelVars.LEVEL_BLUE;
        levelDescription.levelName = LANG.format("3_level_title");
        return levelDescription;
    }

}
