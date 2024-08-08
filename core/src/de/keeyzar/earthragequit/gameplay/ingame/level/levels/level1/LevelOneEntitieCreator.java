package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level1;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Fuel;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.PlatformWithText;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class LevelOneEntitieCreator {
    public void createEntities(ERQGame game, World world, Stage stage, float worldHeight, int coinValue) {
        int coinsPerSlice = 20;
        int plattformsPerTile = 20;
        int fuelPerTile = 1;
        int bagPerTile = 3;
        Spawner.setWorld(world);
        Spawner.setWorldHeight(worldHeight);
        Spawner.sliceWorld(20);
        int tiles = (MathUtils.ceil(worldHeight / 20));
        stage.addActor(new PlatformWithText(world, 50, 6, stage));
        stage.addActor(new Fuel(world, 50, 10));
        stage.addActor(new Fuel(world, 50, 11));
        stage.addActor(new Fuel(world, 50, 12));
        stage.addActor(new Fuel(world, 50, 13));
        stage.addActor(new Fuel(world, 50, 14));
        stage.addActor(new Fuel(world, 50, 15));
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(Spawner.randomCoin(coinValue));
            }
            for(int spawns = 0; spawns<bagPerTile; spawns++){
                stage.addActor(Spawner.randomMoneyBag(coinValue));
            }
            if(i > 0) {
                for (int spawns = 0; spawns < plattformsPerTile; spawns++) {
                    stage.addActor(Spawner.randomPlatform(stage));
                }
            }
            for(int spawns = 0; spawns<fuelPerTile; spawns++){
                stage.addActor(Spawner.randomFuel());
            }
            Spawner.nextSlice();
        }
    }


}
