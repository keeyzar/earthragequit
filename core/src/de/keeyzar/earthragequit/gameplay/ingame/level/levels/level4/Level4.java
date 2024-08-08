package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level4;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.Boss2_Defined;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level4 extends LevelDefining {

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new Level4EntitieCreator().createEntities(world, stage, getWorldHeight(), this, levelDescription.coinValue);
    }

    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    public LevelDescription getLevelDescription(){
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription(){
            @Override
            public BossLevelDefining getBossLevel() {
                return new Boss2_Defined();
            }
        };
        levelDescription.width = 100;
        levelDescription.height = 150;
        levelDescription.level = 4;
        levelDescription.coinValue = 2;
        levelDescription.levelTmxPath = LevelVars.LEVEL_RED;
        levelDescription.levelName = LANG.format("4_level_title");
        levelDescription.hasBossLevel = true;
        return levelDescription;
    }
}
