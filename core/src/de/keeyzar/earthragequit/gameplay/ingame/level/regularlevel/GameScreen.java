package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.background.BackgroundStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.finished.LevelFinishedStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.Gameoverview;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gamestage.GameStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.AdjustHudStageListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.PauseListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.AdjustHud;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.pause.PauseStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.pause.PauseStageListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.starting.StartingStage;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class GameScreen extends Transitionable {
    private ERQGame game;
    private LevelDefining levelDefining; //this is the LevelCreator. from this interface the gamescreen and all
                                         //modules get their not static informations(level, entityCreation etc.)

    private int state = 1; //current game State
    private final int STATE_STARTING = 1; //when the player is playin' the minigame.
    private final int STATE_GAMEPLAY = 2; //when the player is actually playing
    private final int STATE_PAUSE = 3; //when the player pauses the game
    private final int STATE_WON_OR_GAME_OVER = 4; //when the player pauses the game
    private final int STATE_FINISHED = 5; //when the player reaches a given Goal
    private final int STATE_ADJUST_HUD = 6; //when the player is adjusting his hud

    //gameplay stuff
    GameStage gameStage;
    //The Hud
    HudStage hudStage;
    //The Background
    BackgroundStage backgroundStage;
    //when the player is adjusting the Hud
    AdjustHud adjustHudStage;
    //when the game is won, and the player presses "bossfight"
    LevelFinishedStage levelFinishedStage;
    //when game is won or over
    Gameoverview wonOrGameOverStage;
    //starting stuff(Early Boost
    StartingStage startingStage;
    //pause stuff
    PauseStage pauseStage;
    //explaining stuff. Must not be shouldShow, can be.
    TalkingStage talkingStage;





    //FIXME REMOVE BEFORE RELEASE
    private FPSLogger fpsLogger;

    public GameScreen(ERQGame game, LevelDefining levelDefining) {
        this.game = game;
        this.levelDefining = levelDefining;
        levelDefining.initLevel(game);
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state){
            case STATE_STARTING:
                renderStart();
                break;
            case STATE_GAMEPLAY:
                renderGameplay(delta);
                break;
            case STATE_PAUSE:
                renderPause();
                break;
            case STATE_WON_OR_GAME_OVER:
                renderGameOver(delta);
                break;
            case STATE_FINISHED:
                renderFinished(delta);
                break;
            case STATE_ADJUST_HUD:
                renderAdjustHud();
                break;
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        if(gameStage != null) {
            gameStage.getViewport().update(width, height);
            hudStage.getViewport().update(width, height);
            startingStage.getViewport().update(width, height);
            pauseStage.getViewport().update(width, height);
            adjustHudStage.getViewport().update(width, height);
            levelFinishedStage.getViewport().update(width, height);
            wonOrGameOverStage.updateAll(width, height);
            if (talkingStage != null) {
                talkingStage.getViewport().update(width, height);
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameStage.dispose();
        hudStage.dispose();
        backgroundStage.dispose();
        pauseStage.dispose();
        adjustHudStage.dispose();
        wonOrGameOverStage.dispose();
        levelDefining.dispose();

        //null references, because they may hold references to this class, so the chain will be broken

        gameStage = null;
        hudStage = null;
        backgroundStage = null;
        adjustHudStage = null;
        levelFinishedStage = null;
        wonOrGameOverStage = null;
        startingStage = null;
        pauseStage = null;
        talkingStage = null;
    }

    ///////////////////////////////////////
    ///////////////////////////////////////
    ///////////////////////////////////////RENDERING
    ///////////////////////////////////////
    ///////////////////////////////////////

    /**
     * Everything that has to do with the pause must be rendered here
     */
    private void renderPause() {
        backgroundStage.draw();
        gameStage.draw();
        pauseStage.act();
        pauseStage.draw();
    }

    /**
     * renderBeforeRegularStage start stuff
     */
    private void renderStart() {
        backgroundStage.draw();
        gameStage.draw();
        startingStage.act();
        startingStage.draw();
    }

    /**
     * renderBeforeRegularStage gameplay stuff
     * @param delta
     */
    private void renderGameplay(float delta) {
//        Utils.debug(2);
        backgroundStage.draw();
//        Utils.debug(3);
        //deactivate gamestage acting, if the talkingstage is acting
        if(talkingStage != null){
            //if talkingstage is acting, it must be called before
            //gamestage acting to allow correct starting from methods.
            levelDefining.talkingAct(delta);
            if(!talkingStage.isActing()){
                gameStage.act(delta);
            }
        } else {
            gameStage.act(delta);
//            Utils.debug(4);
        }

        gameStage.draw();
//        Utils.debug(5);
        //draw and actAndDraw after the gamestage, because hud is ALWAYS
        //in the front.
        hudStage.act(delta);
        hudStage.draw();
//        Utils.debug(6);

        if(talkingStage != null){
            talkingStage.act(delta);
            talkingStage.draw();
        }

        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            startGameOver();
        } else if (Gdx.input.isKeyPressed(Input.Keys.M) || Gdx.input.isTouched(5)){
            startGameFinished();
        }

//        fpsLogger.log();
    }


    /**
     * renderBeforeRegularStage GameOverStuff
     * @param delta
     */
    private void renderGameOver(float delta) {
        if(!levelDefining.isGameOverAllowed()){
            levelDefining.gameOverStuff(this);
        }
            backgroundStage.draw();
            wonOrGameOverStage.act(delta);
            if(gameStage.getRoot().hasActions()){
                gameStage.draw();
                gameStage.fadeOutAct(delta);
            }

    }


    /**
     * renderBeforeRegularStage adjust Hud stuff
     */
    private void renderAdjustHud() {
        backgroundStage.draw();
        gameStage.draw();
        adjustHudStage.act();
        adjustHudStage.draw();
    }
    /**
     * renderBeforeRegularStage finished stuff
     */
    private void renderFinished(float delta) {
        backgroundStage.draw();
        levelFinishedStage.act();
        levelFinishedStage.draw();
        if(wonOrGameOverStage.hasActions()){
            wonOrGameOverStage.act(delta);
        }
    }


    ///////////////////////////////
    ///////////STARTING////////////
    ///////////////////////////////

    /**
     * start AdjustHud
     */
    public void startAdjustHud(){
        state = STATE_ADJUST_HUD;
        Gdx.input.setInputProcessor(adjustHudStage);
        adjustHudStage.activateAdjustMode();
    }

    /**
     * start bossfight
     */
    public void startBossFight(){
        state = STATE_FINISHED;
        wonOrGameOverStage.addAction(Actions.fadeOut(0.5f));
        Gdx.input.setInputProcessor(levelFinishedStage);
    }

    /**
     * start game Finished (he reached the end)
     */
    public void startGameFinished(){
        Sound sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_LEVEL_WON);
        sound.play(MusicHandler.getSoundVolume());
        game.getMusicHandler().playMusic(MusicHandler.LEVEL_WON_MUSIC);
        state = STATE_WON_OR_GAME_OVER;
        levelDefining.playerFinishedMap();
        wonOrGameOverStage.startGameEND(true);
        hudStage.cancelTouchFocus();
    }


    /**
     * The Gameplay starts
     */
    public void startGameplay() {
        state = STATE_GAMEPLAY;
        Gdx.input.setInputProcessor(hudStage);
    }

    /**
     * game overview lost
     */
    public void startGameOver(){
        state = STATE_WON_OR_GAME_OVER;
        wonOrGameOverStage.startGameEND(false);
        hudStage.cancelTouchFocus();
    }

    /**
     * pause the game
     */
    public void startPause(){
        state = STATE_PAUSE;
        Gdx.input.setInputProcessor(pauseStage);
        pauseStage.show();
        hudStage.cancelTouchFocus();
    }

    /**
     * ends Pause and starts game
     */
    public void endPause(){
        pauseStage.hide(new FinishAnimation() {
            @Override
            public void finishedHiding() {
                hudStage.cancelTouchFocus();
                state = STATE_GAMEPLAY;
                Gdx.input.setInputProcessor(hudStage);
            }
        });
    }

    /**
     * dispose all and go to menu
     */
    public void disposeAndToMenu() {
        game.setScreen(new TransitionScreen(game, GameScreen.this, new MainMenuScreen(game)));
    }

    public LevelDefining getLevelDefining() {
        return levelDefining;
    }

    @Override
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void init() {
        gameStage = new GameStage(game, this);
        gameStage.getRoot().getColor().a = 0;
        hudStage = new HudStage(game, gameStage.getPlayer(), new PauseListener() {
            @Override
            public void pauseClicked() {
                startPause();
            }
        });
        gameStage.getPlayer().setHudStage(hudStage);
        levelDefining.modifyHudStage(hudStage);
        hudStage.createSteuerung();
        hudStage.createInfoB4Level(levelDefining.getQuestInfo());
        backgroundStage = new BackgroundStage(gameStage.getCamera(), this);
        startingStage = new StartingStage(gameStage.getPlayer(), this);
        pauseStage = new PauseStage(game, gameStage.getPlayer(), new PauseStageListener() {
            @Override
            public void endPause() {
                GameScreen.this.endPause();
            }

            @Override
            public void toMenu() {
                GameScreen.this.disposeAndToMenu();
            }

            @Override
            public void startAdjustHud() {
                GameScreen.this.startAdjustHud();
            }
        });
        adjustHudStage = new AdjustHud(hudStage, new AdjustHudStageListener() {
            @Override
            public void startPause() {
                GameScreen.this.startPause();
            }
        });
        levelFinishedStage = new LevelFinishedStage(this, game);
        wonOrGameOverStage = new Gameoverview(game, gameStage.getPlayer(), this, levelDefining.getUnlocksForThisLevel(game));
        talkingStage = levelDefining.getTalkingStage();
        game.getMusicHandler().playMusic(MusicHandler.GAME_MUSIC);
        if(levelDefining.isStartingBoostDisabled()){
            startGameplay();
            fadeInFromGameStage();
            hudStage.showInfoBeforeLevel();
        }

        //FIXME REMOVE BEFORE RELEASE
        fpsLogger = new FPSLogger();
        //hudStage.setWorld(gameStage.getWorld());

    }

    public Player getPlayer(){
        return gameStage.getPlayer();
    }

    @Override
    public void act(float delta) {
    }

    public void showInfoForLevelQuest() {
        hudStage.showInfoBeforeLevel();
    }

    public void fadeInFromGameStage() {
        gameStage.getRoot().addAction(Actions.fadeIn(0.3f));
    }

    public void cancelTouchFocus() {
        hudStage.cancelTouchFocus();
    }

    public interface FinishAnimation{
        void finishedHiding();
    }
}
