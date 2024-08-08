package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling.CullPro;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;
import de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes.ParticleWall;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class BossStage extends Stage {
    private final CullPro cullPro;
    private ERQGame game;
    private BossScreen bossScreen;
    private BossLevelDefining bld;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer; //debugging purpose
    private Player player;

    WorldUtils worldUtils;
    EntityInterpolator entityInterpolator;
    RegularSpawning spawning;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private boolean fixatePlayer = true;

    ParticleWall particleWall;

    public BossStage(ERQGame game, BossScreen bossScreen, BossLevelDefining bld){
        this.game = game;
        this.bossScreen = bossScreen;
        this.bld = bld;
        setupCamera(); //setup FOV as first.
        entityInterpolator = new EntityInterpolator();
        worldUtils = new BossWorld(game, camera, this, bld, entityInterpolator);
        player = worldUtils.getPlayer();
        addActor(player);
        cullPro = new CullPro(player, worldUtils.getWorld());



        entityInterpolator.addActor(player);
        bld.createSpawning(game, worldUtils, entityInterpolator, this);
        spawning = bld.getSpawning();

        //FIXME REMOVE BEFORE RELEASE
        renderer = new Box2DDebugRenderer();
        particleWall = new ParticleWall();
        particleWall.setPosition(camera.position.x - camera.viewportWidth / 2, worldUtils.getWorldHeigth() / 2 + Player.HEIGHT / 2);
        addActor(particleWall);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(GVars.FOV_WIDTH, GVars.FOV_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        setViewport(new StretchViewport(GVars.FOV_WIDTH, GVars.FOV_HEIGHT, camera));
        camera.update();
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            entityInterpolator.safeOldPosition();
            worldUtils.getWorld().step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
            entityInterpolator.calculateInterpolation(accumulator / TIME_STEP);
        }
        checkPlayerPosition();
        checkSpawning();
        checkWinLoose();
    }

    private void checkSpawning() {
        //if anything must be spawned from time to time
        spawning.spawn();
    }

    private void checkPlayerPosition() {
        player.checkPlayerPosition();
        if(fixatePlayer && player.getBody().getPosition().y > worldUtils.getWorldHeigth() / 2 - player.getHeight()){
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyLinearImpulse(new Vector2(0, -5), player.getBody().getPosition(), true);
        } else if(player.getBody().getPosition().y > worldUtils.getWorldHeigth() - player.getHeight() * 2) {
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyLinearImpulse(new Vector2(0, -5), player.getBody().getPosition(), true);
        }
    }

    private void checkWinLoose() {
        bld.checkWinLoose(bossScreen);
    }


    @Override
    public void draw() {
        super.draw();
//        renderer.render(worldUtils.getWorld(), camera.combined);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void dispose() {
        super.dispose();
        cullPro.gameOver(worldUtils.getWorld());
        worldUtils.dispose();
        getPlayer().dispose();
        renderer.dispose();
    }

    public void setFixatePlayer(boolean fixatePlayer) {
        this.fixatePlayer = fixatePlayer;
        if(!fixatePlayer){
            particleWall.remove();
        }
    }
}
