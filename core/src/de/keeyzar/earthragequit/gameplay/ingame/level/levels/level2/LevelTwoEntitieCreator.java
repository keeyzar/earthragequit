package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class LevelTwoEntitieCreator {

    public void createEntities(World world, Stage stage, float worldHeight, int coinValue) {
        int coinsPerSlice = 30; // 15
        int bagPerTile = 4;
        int plattformsPerTile = 7; //10
        int fuelPerTile = 15;
        int beesPerTile = 3;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        int tiles = (MathUtils.ceil(worldHeight / 20));

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
            for(int spawns = 0; spawns<beesPerTile; spawns++){
                stage.addActor(randomBee());
            }
            nextSlice();
        }
    }
}
