package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level9;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.LockedCircle;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.LockedWall;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott2;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.FuelStation;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.KeyEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level9EntitieCreator {
    public void createEntities(World world, Stage stage, Player player, float worldWidth, float worldHeight, LevelDefining levelDefining) {
        int moneybagPerSlice = 10; // 15
        int plattformsPerTile = 2; //10
        int schrottPerTile = 3;
        setWorld(world);
        setWorldHeight(worldHeight);

        float slicingSize = 25;
        sliceWorld(slicingSize);
        ParticleEffectPool poolSchrott1 = Schrott.getPool();
        levelDefining.addToPool(poolSchrott1);
        ParticleEffectPool poolSchrott2 = Schrott2.getPool();
        levelDefining.addToPool(poolSchrott2);

        int tiles = MathUtils.ceil(worldHeight / slicingSize);
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<moneybagPerSlice - (MathUtils.ceil(i/6) + 1) * 5; spawns++){
                stage.addActor(randomMoneyBag(levelDefining.getLevelDescription().coinValue));
            }
            for(int spawns = 0; spawns<3; spawns++){
                stage.addActor(randomFuel());
            }
            if(i < 2){
                for(int spawns = 0; spawns<15; spawns++){
                    stage.addActor(randomBee());
                }
            }else if(i<4){
                for(int spawns = 0; spawns<5; spawns++){
                    stage.addActor(randomSchrott(1, poolSchrott1));
                }
            }else if(i<6){
                for(int spawns = 0; spawns<5; spawns++){
                    stage.addActor(randomSchrott(2, poolSchrott2));
                }
            }else{
                for(int spawns = 0; spawns<4; spawns++){
                    stage.addActor(randomCreep(player));
                }
            }
            nextSlice();
        }


        stage.addActor(new LockedWall(world, EntityVars.KEY_LEV_TWO, worldWidth, 3, 0, 150));
        stage.addActor(new KeyEntity(world, EntityVars.KEY_LEV_ONE, 50, 140));


        stage.addActor(new LockedCircle(world, EntityVars.KEY_LEV_ONE, 10, worldWidth/ 4, 10));
        stage.addActor(new KeyEntity(world, EntityVars.KEY_LEV_TWO, worldWidth/4, 10));

        stage.addActor(new FuelStation(world, worldWidth / 4 * 3, 4));


    }
}
