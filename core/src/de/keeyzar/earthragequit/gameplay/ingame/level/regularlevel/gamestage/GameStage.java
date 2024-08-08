package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gamestage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.CameraUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.EntityCreation;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling.CullPro;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;
import de.keeyzar.earthragequit.sound.MusicHandler;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class GameStage extends Stage {
    private final CullPro cullPro;
    private ERQGame game;
    private GameScreen gameScreen;
    private Player player;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private WorldUtils worldUtils;
    private CameraUtils cameraUtils;
    private EntityInterpolator entityInterpolator;

    private final float TIME_STEP = 1 / 45f;
    private float accumulator = 0f;

    private boolean isGameOver;
    private boolean isGameOverActivated;
    private long gameOverTimer;


    public GameStage(ERQGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        setupCamera(gameScreen.getLevelDefining().getWorldWidth());

        entityInterpolator = new EntityInterpolator();
        worldUtils = new RegularWorld(gameScreen.getLevelDefining(), game, camera, entityInterpolator);
        player = worldUtils.getPlayer();

        cullPro = new CullPro(player, worldUtils.getWorld());

        gameScreen.getLevelDefining().modifyPlayerStats(player);
        new EntityCreation(worldUtils, this, getPlayer(), gameScreen);

        addActor(worldUtils.getGround());
        addActor(player);

        cameraUtils = new CameraUtils(player.body.getPosition());
        camera.position.set(player.body.getPosition().x, camera.viewportHeight / 2, 0);
        renderer = new Box2DDebugRenderer();


        entityInterpolator.addActor(player);

        isGameOver = false;
        isGameOverActivated = false;

        gameOverTimer = 0;

    }

    @Override
    public void act(float delta) {
        // Fixed timestep

        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            entityInterpolator.safeOldPosition();
            worldUtils.getWorld().step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
        entityInterpolator.calculateInterpolation(accumulator / TIME_STEP);
        super.act(delta);
        updateCamera();
        getPlayer().checkPlayerPosition();

        checkIfGameOver();
        checkIfWon();

        if(Gdx.input.isKeyPressed(Input.Keys.E) || Gdx.input.isTouched(4)){ //FIXME REMOVE BEFORE RELEASE
            player.getPlayerCalculatedVars().setFixSpeed(30);
            player.getPlayerCalculatedVars().setFixMaxSpeed(40);
            player.getPlayerCalculatedVars().setFixFuel(10);
            player.getPlayerCalculatedVars().setFixLife(10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            player.getPlayerCalculatedVars().addU_fuel(3);
        }
    }
    private void checkIfWon() {
        if(player.body.getPosition().y > worldUtils.getWorldHeigth()){
            gameScreen.startGameFinished();
        }
    }


    @Override
    public void draw() {
        super.draw();
//        renderer.render(worldUtils.getWorld(), camera.combined);
    }

    private boolean FUEL_EMPTY = false;
    private boolean DEATH = false;

    /**
     * check if the player lost
     */
    private void checkIfGameOver() {
        if(!isGameOverActivated && getPlayer().getPlayerCalculatedVars().getU_speed_fuel() <= 0){
            isGameOver = true;
            FUEL_EMPTY = true;
        }
        if(!isGameOverActivated && player.getPlayerCalculatedVars().getLife() <= 0){
            isGameOver = true;
            DEATH = true;
        }

        if(isGameOver && !isGameOverActivated){
            isGameOverActivated = true;
            gameOverTimer = TimeUtils.millis() + 2000;
            Gdx.input.setInputProcessor(this);
            //destroy joint, so the enemies does not disappear
            cullPro.gameOver(worldUtils.getWorld());
            game.getMusicHandler().playMusic(MusicHandler.LEVEL_LOST_MUSIC);

        }

        if(isGameOver && isGameOverActivated){
            gameScreen.cancelTouchFocus();
            if(TimeUtils.millis() - gameOverTimer > 0){
                gameScreen.startGameOver();
                getRoot().addAction(Actions.fadeOut(0.5f));
                gameScreen.getLevelDefining().additionalInfosForGameOver(gameScreen);
                if(FUEL_EMPTY){
                    game.getAchievementVerwalter().addStat(AVars.COUNTER_FUEL_EMPTY, 1);
                } else if(DEATH){
                    game.getAchievementVerwalter().addStat(AVars.COUNTER_DEATH, 1);
                }
            }
        }
    }

    /**
     * Camera updating (smooth player following)
     */
    private void updateCamera() {
        if(!isGameOverActivated) {
            cameraUtils.updateCamera(getPlayer(), camera, worldUtils.getWorldWidth(), worldUtils.getWorldHeigth()
            ,player.getPlayerCurrentStates().isShaking(), player.getPlayerCurrentStates().getShakingIntensity());
        }
    }

    /**
     * camera setup
     * @param worldWidth
     */
    private void setupCamera(int worldWidth) {
        camera = new OrthographicCamera(GVars.FOV_WIDTH, GVars.FOV_HEIGHT);
        //center camera in world on x axis, that's where the player starts
        camera.position.set(worldWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
        setViewport(new StretchViewport(GVars.FOV_WIDTH, GVars.FOV_HEIGHT, camera));

    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void dispose() {
        super.dispose();
        player.dispose();
        worldUtils.dispose();
        renderer.dispose();
        System.gc();
    }

    /**
     * do not update the other stuff! only fading out action
     * @param delta
     */
    public void fadeOutAct(float delta) {
        super.act(delta);
    }

    public World getWorld() {
        return worldUtils.getWorld();
    }
}
