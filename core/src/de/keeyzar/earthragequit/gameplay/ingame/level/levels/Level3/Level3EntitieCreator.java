package de.keeyzar.earthragequit.gameplay.ingame.level.levels.Level3;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level3EntitieCreator {
    public void createEntities(World world, Stage stage, float worldHeight, int coinValue) {
        int coinsPerSlice = 25; // 15
        int bagPerTile = 3; // 15
        int plattformsPerTile = 8; //10
        int fuelPerTile = 5;
        int waspsPerTile = 5;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        int tiles = (MathUtils.ceil(worldHeight / 20));
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(randomCoin(coinValue));
            }
            for(int spawns = 0; spawns<bagPerTile; spawns++){
                stage.addActor(Spawner.randomMoneyBag(coinValue - 1));
            }
            for(int spawns = 0; spawns<plattformsPerTile; spawns++){
                stage.addActor(randomPlatform(stage));
            }
            for(int spawns = 0; spawns<fuelPerTile; spawns++){
                stage.addActor(randomFuel());
            }
            for(int spawns = 0; spawns<waspsPerTile; spawns++){
                stage.addActor(randomBee());
            }
            nextSlice();
        }
    }
}
