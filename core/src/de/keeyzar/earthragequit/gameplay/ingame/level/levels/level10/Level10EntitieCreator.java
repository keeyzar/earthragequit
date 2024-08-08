package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level10;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level10EntitieCreator {
    public void createEntities(World world, Stage stage, float worldHeight) {
        int coinsPerSlice = 18; // 15
        int plattformsPerTile = 2; //10
        int schrottPerTile = 3;
        int invisibleCreepPerTile = 2;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        int counter = 0;
        int tiles = MathUtils.ceil(worldHeight / 20);
//        for(int i = 0; i<tiles; i++){
//            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
//                stage.addActor(randomCoin(4));
//            }
//            for(int spawns = 0; spawns<plattformsPerTile; spawns++){
//                stage.addActor(randomPlatform());
//            }
//            for(int spawns = 0; spawns<schrottPerTile; spawns++){
//                stage.addActor(randomSchrott(2));
//            }
//            for(int spawns = 0; spawns<invisibleCreepPerTile + counter; spawns++){
//                stage.addActor(randomCreep());
//            }
//            if(i % 2 == 0){
//                counter++;
//            }
//            nextSlice();
//        }
    }
}
