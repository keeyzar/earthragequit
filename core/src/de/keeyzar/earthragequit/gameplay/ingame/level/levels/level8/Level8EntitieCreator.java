package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level8;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.LockedWall;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Schrott;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.KeyEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

import static de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner.*;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class Level8EntitieCreator {
    public void createEntities(World world, Stage stage, float worldWidth, float worldHeight, Player player, int coinValue) {
        int coinsPerSlice = 5;
        int coinBagPerSlice = 5; // 15
        int schrottPerTile = 2;
        int invisibleCreepPerTile = 2;
        int plattformPerTile = 4;
        setWorld(world);
        setWorldHeight(worldHeight);
        sliceWorld(20);
        ParticleEffectPool pool = Schrott.getPool();

        int tiles = MathUtils.ceil(worldHeight / 20);
        for(int i = 0; i<tiles; i++){
            for(int spawns = 0; spawns<coinsPerSlice; spawns++){
                stage.addActor(randomCoin(coinValue));
            }
            for(int spawns = 0; spawns<coinBagPerSlice; spawns++){
                stage.addActor(randomMoneyBag(2));
            }
            for(int spawns = 0; spawns<plattformPerTile; spawns++){
                stage.addActor(randomPlatform(stage));
            }
            for(int spawns = 0; spawns<schrottPerTile; spawns++){
                stage.addActor(randomSchrott(1, pool));
            }
            for(int spawns = 0; spawns<invisibleCreepPerTile; spawns++){
                stage.addActor(randomCreep(player));
            }
            nextSlice();
        }

        stage.addActor(new KeyEntity(world, EntityVars.KEY_LEV_ONE, 5, 3));
        stage.addActor(new LockedWall(world, EntityVars.KEY_LEV_ONE, worldWidth, 3, 0, 30));

        stage.addActor(new KeyEntity(world, EntityVars.KEY_LEV_ONE, worldWidth / 2, 10));
        stage.addActor(new LockedWall(world, EntityVars.KEY_LEV_ONE, worldWidth, 3, 0, 40));
    }
}
