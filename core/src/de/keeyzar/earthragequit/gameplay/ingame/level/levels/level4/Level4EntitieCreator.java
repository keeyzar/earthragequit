package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level4;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level4EntitieCreator {
    public void createEntities(World world, Stage stage, float worldHeight, LevelDefining levelDefining, int coinValue) {
        int coinsPerSlice = 30; // 15
        int bagPerTile = 4;
        int plattformsPerTile = 5; //10
        int fuelPerTile = 5;
        int schrottPerTile = 6;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        ParticleEffectPool pool = Schrott.getPool();
        levelDefining.addToPool(pool);

        int tiles = MathUtils.ceil(worldHeight / 20);
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(randomCoin(coinValue));
            }
            for(int spawns = 0; spawns<bagPerTile; spawns++){
                stage.addActor(Spawner.randomMoneyBag(coinValue));
            }
            for(int spawns = 0; spawns<plattformsPerTile; spawns++){
                stage.addActor(randomPlatform(stage));
            }
            for(int spawns = 0; spawns<fuelPerTile; spawns++){
                stage.addActor(randomFuel());
            }
            for(int spawns = 0; spawns<schrottPerTile; spawns++){
                stage.addActor(randomSchrott(1, pool));
            }
            nextSlice();
        }
    }
}
