package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities.*;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 11.03.2016
 */
public class Boss3_Spawning implements RegularSpawning {
    private final World world;
    private Player player;
    private final OrthographicCamera camera;
    private ERQGame game;
    private final WorldUtils worldUtils;
    private EntityInterpolator entityInterpolator;
    private final BossStage bossStage;
    private RepeatAction delayAction;

    private Stone stone;
    private WallWithHole actualWall;



    private int counter = 0;
    private TriggerButton triggerButton1;
    private TriggerButton triggerButton2;
    private TriggerButton triggerButton3;
    private boolean firstPhaseTriggered = false;


    public Boss3_Spawning(ERQGame game, final WorldUtils worldUtils, final EntityInterpolator entityInterpolator, final BossStage bossStage, Player player) {
        this.game = game;
        this.worldUtils = worldUtils;
        this.entityInterpolator = entityInterpolator;
        this.bossStage = bossStage;
        this.camera = worldUtils.getCamera();
        this.world = worldUtils.getWorld();
        this.player = player;
        spawnStone();
        spawnEntities();

        delayAction = Actions.forever(Actions.delay(5, Actions.run(new Runnable() {
            @Override
            public void run() {
                bossStage.addActor(new B3Arrow(world));
            }
        })));
    }

    private void spawnEntities() {
        triggerButton1 = new TriggerButton(1, world, bossStage);
        bossStage.addActor(triggerButton1);
        triggerButton2 = new TriggerButton(2, world, bossStage);
        bossStage.addActor(triggerButton2);
        triggerButton3 = new TriggerButton(3, world, bossStage);
        bossStage.addActor(triggerButton3);
        bossStage.addActor(new Bullseye(world, this));
    }

    private void spawnStone() {
        stone = new Stone(game, world, worldUtils.getPlayer());
        bossStage.addActor(stone);
    }

    @Override
    public void spawn(){
        if(delayAction != null){
            delayAction.act(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public BossEnemy getBoss() {
        return stone;
    }

    public void firstPhaseFinished(){
        if(firstPhaseTriggered) return;
        firstPhaseTriggered = true;
        triggerButton1.destroy();
        triggerButton2.destroy();
        triggerButton3.destroy();
        for (Actor next : bossStage.getActors()) {
            if(next instanceof B3Arrow){
                ((B3Arrow) next).destroy();
            }
        }
        bossStage.addActor(new KeyHolderStone(world, bossStage,
                new KeyHolderStone.HitCallback() {
            @Override
            public void gotHit() {
                if(actualWall != null){
                    actualWall.destroy();
                    actualWall = null;
                }
                if(counter == 0){
                    counter++;
                    actualWall = new WallWithHole(world, bossStage, 4, 10, 5);
                } else if(counter == 1){
                    actualWall = new WallWithHole(world, bossStage, 4.5f, 7, 5);
                    counter++;
                } else if(counter == 2){
                    counter++;
                    secondPhaseFinished();
                }
            }
        }));

        actualWall = new WallWithHole(world, bossStage, 8, 8, 6);
        delayAction = Actions.forever(Actions.delay(5, Actions.run(new Runnable() {
            @Override
            public void run() {
                bossStage.addActor(new B3ArrowRocket(world));
            }
        })));
    }

    private void secondPhaseFinished() {
        delayAction = null;
        bossStage.setFixatePlayer(false);
    }

    public boolean gameWon() {
        return stone.isOver();
    }
}
