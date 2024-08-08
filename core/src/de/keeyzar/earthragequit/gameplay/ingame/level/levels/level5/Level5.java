package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level5;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level6Unlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.RadarUnlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.SkillPlace3Unlocker;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 *
 */
public class Level5 extends LevelDefining {

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new Level5EntitieCreator().createEntities(world, stage, getWorldHeight(), this);
    }



    @Override
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
        unlockeds.add(new Level6Unlocker(game));
        unlockeds.add(new RadarUnlocker(game));
        unlockeds.add(new SkillPlace3Unlocker(game));
        return unlockeds;
    }


    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription();
        levelDescription.width = 100;
        levelDescription.height = 250;
        levelDescription.level = 5;
        levelDescription.coinValue = 3;
        levelDescription.levelTmxPath = LevelVars.LEVEL_GREEN;
        levelDescription.levelName = LANG.format("5_level_title");
        return levelDescription;
    }


}
