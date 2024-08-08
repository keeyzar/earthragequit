package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level7;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott2;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level7EntitieCreator {
    public void createEntities(World world, Stage stage, float worldHeight, LevelDefining levelDefining, Player player, int coinValue) {
        int coinsPerSlice = 5; // 15
        int bagsPerSlice = 5; // 15
        int plattformsPerTile = 5; //10
        int schrottPerTile = 3;
        int invisibleCreepPerTile = 1;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        ParticleEffectPool pool = Schrott2.getPool();
        levelDefining.addToPool(pool);
        int counter = 3;
        int tiles = MathUtils.ceil(worldHeight / 20);
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(randomCoin(coinValue));
            }
            for(int spawns = 0; spawns<bagsPerSlice; spawns++){
                stage.addActor(randomMoneyBag(coinValue));
            }
            for(int spawns = 0; spawns<plattformsPerTile; spawns++){
                stage.addActor(randomPlatform(stage));
            }
            for(int spawns = 0; spawns<schrottPerTile; spawns++){
                stage.addActor(randomSchrott(2, pool));
            }
            for(int spawns = 0; spawns<invisibleCreepPerTile + counter; spawns++){
                stage.addActor(randomCreep(player));
            }
            if(i % 3 == 0){
                counter++;
            }
            nextSlice();
        }

    }
}
