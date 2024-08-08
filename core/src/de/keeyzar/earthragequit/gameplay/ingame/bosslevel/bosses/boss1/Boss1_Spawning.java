package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.Arrow;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.BossWaspMother;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 11.03.2016
 */
class Boss1_Spawning implements RegularSpawning {
    private final World world;
    private final OrthographicCamera camera;
    private ERQGame game;
    private final WorldUtils worldUtils;
    private EntityInterpolator entityInterpolator;
    private final BossStage bossStage;

    private float arrowTimer;
    private float arrowSpawnTime = 2;
    private Rectangle arrowSpawnBounds;
    private BossEnemy bossWaspMother;


    public Boss1_Spawning(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        this.game = game;
        this.worldUtils = worldUtils;
        this.entityInterpolator = entityInterpolator;
        this.bossStage = bossStage;
        this.camera = worldUtils.getCamera();
        this.world = worldUtils.getWorld();
        spawnWasp();
        arrowTimer = 0;
        arrowSpawnBounds = new Rectangle();
        arrowSpawnBounds.setX(camera.position.x - camera.viewportWidth / 2);
        arrowSpawnBounds.setY(3);
        arrowSpawnBounds.setWidth(camera.viewportWidth - 3);
        arrowSpawnBounds.setHeight(camera.viewportHeight / 2 - 2);
    }

    private void spawnWasp() {
        bossWaspMother = new BossWaspMother(world, camera);
        bossStage.addActor(bossWaspMother);
    }

    private void spawnArrow() {
        if(arrowTimer > arrowSpawnTime){
            arrowSpawnTime = 2;
            arrowTimer = 0;
            bossStage.addActor(new Arrow(world, arrowSpawnBounds));
        }
    }

    @Override
    public void spawn(){
        arrowTimer += Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
        spawnArrow();
    }

    @Override
    public BossEnemy getBoss() {
        return bossWaspMother;
    }

    public BossEnemy getBossWaspMother() {
        return bossWaspMother;
    }
}
