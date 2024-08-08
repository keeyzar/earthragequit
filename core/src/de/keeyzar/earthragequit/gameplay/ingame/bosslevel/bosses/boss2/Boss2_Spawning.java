package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.Balthazar;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 11.03.2016
 */
public class Boss2_Spawning implements RegularSpawning {
    private final World world;
    private final OrthographicCamera camera;
    private ERQGame game;
    private final WorldUtils worldUtils;
    private EntityInterpolator entityInterpolator;
    private final BossStage bossStage;
    private DelayAction delayAction;

    private BossEnemy balthazar;


    public Boss2_Spawning(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        this.game = game;
        this.worldUtils = worldUtils;
        this.entityInterpolator = entityInterpolator;
        this.bossStage = bossStage;
        this.camera = worldUtils.getCamera();
        this.world = worldUtils.getWorld();
        spawnBalthazar();
    }

    private void spawnBalthazar() {
        balthazar = new Balthazar(game, world, worldUtils.getPlayer(), camera);
        bossStage.addActor(balthazar);
    }

    @Override
    public void spawn(){
        //do nothing
    }

    @Override
    public BossEnemy getBoss() {
        return balthazar;
    }
}
