package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level2;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.Boss1_Defined;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level2 extends LevelDefining {

    @Override
    public void createEntities(World world, Stage stage, Player player) {
        new LevelTwoEntitieCreator().createEntities(world, stage, getWorldHeight(), levelDescription.coinValue);
    }

    ///////////////////////////////////////
    ///////LEVEL_DESCRIPTION/////////////////////
    ///////////////////////////////////////

    @Override
    public LevelDescription getLevelDescription() {
        if(levelDescription != null) return levelDescription;
        final LevelDescription levelDescription = new LevelDescription(){
            @Override
            public BossLevelDefining getBossLevel() {
                return new Boss1_Defined();
            }
        };
        levelDescription.width = 100;
        levelDescription.height = 100;
        levelDescription.level = 2;
        levelDescription.coinValue = 1;
        levelDescription.levelTmxPath = LevelVars.LEVEL_GREEN;
        levelDescription.levelName = LANG.format("2_level_title");
        levelDescription.hasBossLevel = true;
        return levelDescription;
    }
}
