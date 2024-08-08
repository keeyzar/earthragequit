package de.keeyzar.earthragequit.tutorial.movement.introstage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra.BossProgress;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.CameraUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.story.regular_story.landing_story.StoryBoard_LandingStory;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.tutorial.movement.GamePlayTutorialScreen;
import de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes.CoinIntro;
import de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes.HYActorIntro;
import de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes.MovementIntro;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class TutorialStage extends Stage {
    private final Player player;
    private final HYActorIntro hyActorIntro;
    ERQGame game;
    private GamePlayTutorialScreen gamePlayTutorialScreen;
    private TalkingStage tS;
    private EntityInterpolator entityInterpolator;
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    World world;
    Box2DDebugRenderer renderer;
    private TutorialWorld tutorialWorld;

    private int state = 1;
    private final int STATE_HYACTOR_INTRO = 1;
    private final int STATE_MOVEMENT_TUTORIAL = 2;
    private final int STATE_COINS = 3;
    private final int STATE_FADE_OUT = 4;

    private HudStage hudStage;

    MovementIntro rotationIntro;
    CoinIntro coinIntro;
    private boolean isTextWriterOpened = false;


    public TutorialStage(ERQGame game, GamePlayTutorialScreen gamePlayTutorialScreen, TalkingStage talkingStage){
        this.game = game;
        this.gamePlayTutorialScreen = gamePlayTutorialScreen;
        this.tS = talkingStage;
        setupCamera();
        entityInterpolator = new EntityInterpolator();
        tutorialWorld = new TutorialWorld((OrthographicCamera) getCamera());
        world = tutorialWorld.getWorld();
        player = new Player(world, game, tutorialWorld);
        player.useBestImage();
        entityInterpolator.addActor(player);
        renderer = new Box2DDebugRenderer();
        hyActorIntro = new HYActorIntro(tS, this);
        rotationIntro = new MovementIntro(tS, this, player, getCamera());
        coinIntro = new CoinIntro(world, tS, this, player, getCamera());
        if(game.getTutorialVerwalter().isAbsolved(TVars.ROCKET_EXPLODED)){
            talkingStage.initSkip(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    state = STATE_FADE_OUT;
                }
            });
        }
    }

    private void setupCamera() {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GVars.FOV_WIDTH, GVars.FOV_HEIGHT);
        setViewport(new StretchViewport(GVars.FOV_WIDTH, GVars.FOV_HEIGHT, orthographicCamera));
        orthographicCamera.position.set(25, GVars.FOV_HEIGHT / 2 + 1, 0);
        orthographicCamera.update();
    }


    @Override
    public void draw() {
        super.draw();
//        renderer.renderBeforeRegularStage(world, getCamera().combined);
    }

    @Override
    public void act(float delta) {
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isTouched(5)){
            state = STATE_FADE_OUT;
        }
        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            entityInterpolator.safeOldPosition();
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
            entityInterpolator.calculateInterpolation(accumulator / TIME_STEP);
        }
        super.act(delta);
        CameraUtils.centerPlayerTutorial(player, (OrthographicCamera) getCamera(), tutorialWorld.getWorldWidth());

        //introStuff.
        switch (state){
            case STATE_HYACTOR_INTRO:
                hyActorIntro.act();
                break;
            case STATE_MOVEMENT_TUTORIAL:
                rotationIntro.act();
                break;
            case STATE_COINS:
                coinIntro.act();
                break;
            case STATE_FADE_OUT:
                state = -1;
                game.getTutorialVerwalter().setLevelAbsolved(TVars.TUTORIAL, true);
                game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.ROCKET_EXPLODED, true);
                game.setScreen(new TransitionScreen(game, gamePlayTutorialScreen, new StoryBoard_LandingStory(game)));
        }
    }



    public Player getPlayer() {
        return player;
    }

    public void start() {
        player.getPlayerCalculatedVars().setFixFuel(1);
        player.getPlayerCalculatedVars().setFixMaxSpeed(5);
        player.getPlayerCalculatedVars().setFixRotation(1);
        player.getPlayerCalculatedVars().setFixSpeed(2);
        addActor(player);
        addActor(tutorialWorld.getGround());
    }

    public void setHudStage(HudStage hudStage) {
        this.hudStage = hudStage;
        hudStage.getHudActor(HudVars.BOSSPROGRESS).setVisible(false);
    }

    public void nextPart(boolean isTextWriterOpened) {
        this.isTextWriterOpened = isTextWriterOpened;
        state++;
    }

    public boolean isTextWriterOpened() {
        return isTextWriterOpened;
    }

    public HudStage getHudStage() {
        return hudStage;
    }

    @Override
    public void dispose() {
        getPlayer().dispose();
        renderer.dispose();
    }

    public BossProgress getBossProgress() {
        return coinIntro.getBossProgress();

    }
}
