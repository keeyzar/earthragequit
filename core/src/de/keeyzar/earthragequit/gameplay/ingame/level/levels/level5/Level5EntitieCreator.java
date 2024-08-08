package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level5;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott2;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level5EntitieCreator {
    public void createEntities(World world, Stage stage, float worldHeight, LevelDefining levelDefining) {
        int coinsPerSlice = 30; // 15
        int plattformsPerTile = 2; //10
        int schrottPerTile = 10;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        ParticleEffectPool pool = Schrott2.getPool();
        levelDefining.addToPool(pool);
        int tiles = MathUtils.ceil(worldHeight / 20);
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(randomCoin(levelDefining.getLevelDescription().coinValue));
            }
            for(int spawns = 0; spawns<plattformsPerTile; spawns++){
                stage.addActor(randomPlatform(stage));
            }
            for(int spawns = 0; spawns<schrottPerTile; spawns++){
//                stage.addActor(randomSchrott());
                stage.addActor(randomSchrott(2, pool));
            }
            nextSlice();
        }
    }
}
