package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.background.BossBackgroundStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.beforeend.BeforeEndStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.bossintro.BossIntroStage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.Gameoverview;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.AdjustHudStageListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.PauseListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.AdjustHud;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.pause.PauseStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.pause.PauseStageListener;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.sound.MusicHandler;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class BossScreen extends Transitionable {
    ERQGame game;
    private final BossLevelDefining bld;


    Player player;
    BossStage bossStage;
    HudStage hudStage;
    PauseStage pauseStage;
    AdjustHud adjustHudStage;
    BossBackgroundStage bossBackgroundStage;
    Gameoverview gameoverview;

    private BossIntroStage bossIntroStage;
    private BeforeEndStage beforeEndStage;


    private int state = 1; //current game State
    private final int STATE_BOSS_INTRO = 1; //intro to Boss
    private final int STATE_GAMEPLAY = 2; //when the player is actually playing
    private final int STATE_PAUSE = 3; //when the player pauses the game
    private final int STATE_BEFORE_END = 4; //when the game is won / lost and over
    private final int STATE_OVERVIEW = 5; //when the overview is displayed.
    private final int STATE_ADJUST_HUD = 6; //when the player is adjusting his hud
    private boolean newScreenActivated = false; //if new screen was Activated

    public BossScreen(ERQGame erqGame, final BossLevelDefining bld){
        this.game = erqGame;
        this.bld = bld;

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(hudStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isTouched(5)){
            startOverview();
                //FIXME REMOVE BEFORE RELEASE
        }
        switch (state){
            case STATE_BOSS_INTRO:
                renderBossIntro(delta);
                break;
            case STATE_GAMEPLAY:
                renderGameplay(delta);
                break;
            case STATE_PAUSE:
                renderPause(delta);
                break;
            case STATE_BEFORE_END:
                renderBeforeEnd(delta);
                break;
            case STATE_OVERVIEW:
                renderOverview(delta);
                break;
            case STATE_ADJUST_HUD:
                renderAdjustHud(delta);
                break;
        }
    }


    @Override
    public void resize(int width, int height) {
        hudStage.getViewport().update(width, height);
        pauseStage.getViewport().update(width, height);
        bossStage.getViewport().update(width, height);
        adjustHudStage.getViewport().update(width, height);
        beforeEndStage.getViewport().update(width, height);
        gameoverview.updateAll(width, height);
        bossIntroStage.getViewport().update(width, height);
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
        bossStage.dispose();
        hudStage.dispose();
        adjustHudStage.dispose();
        pauseStage.dispose();
        bossBackgroundStage.dispose();
        gameoverview.dispose();
        bossIntroStage.dispose();
        bld.dispose();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////RENDERING///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void renderBossIntro(float delta) {
        bossBackgroundStage.draw();
        bossIntroStage.act(delta);
        bossIntroStage.draw();
    }

    private void renderGameplay(float delta) {
        bossBackgroundStage.draw();
        bossStage.act(delta);
        bossStage.draw();
        hudStage.act(delta);
        hudStage.draw();
    }

    private void renderPause(float delta) {
        bossBackgroundStage.draw();
        bossStage.draw();
        pauseStage.act();
        pauseStage.draw();
    }

    private void renderBeforeEnd(float delta) {
        bossBackgroundStage.draw();
        beforeEndStage.act();
        beforeEndStage.draw();

    }

    private void renderOverview(float delta) {
        bossBackgroundStage.draw();
        gameoverview.act(delta);
    }

    private void renderAdjustHud(float delta) {
        bossBackgroundStage.draw();
        bossStage.draw();
        adjustHudStage.act(delta);
        adjustHudStage.draw();

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////STATE CHANGE///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void startBeforeEnd(boolean playerWon){
        state = STATE_BEFORE_END;
        beforeEndStage.start(playerWon);
        Gdx.input.setInputProcessor(beforeEndStage);
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
        pauseStage.hide(new GameScreen.FinishAnimation() {
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
    public void toMenu() {
        if(!newScreenActivated) {
            game.setScreen(new TransitionScreen(game, this, new MainMenuScreen(game)));
            newScreenActivated = !newScreenActivated;
        }
    }

    /**
     * start AdjustHud
     */
    public void startAdjustHud(){
        state = STATE_ADJUST_HUD;
        Gdx.input.setInputProcessor(adjustHudStage);
        adjustHudStage.activateAdjustMode();
    }


    public void startOverview() {
        state = STATE_OVERVIEW;
        gameoverview.startGameEND(true);
    }

    public void startGameplay() {
        state = STATE_GAMEPLAY;
        Gdx.input.setInputProcessor(hudStage);
    }

    @Override
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void init() {
        game.getMusicHandler().playMusic(MusicHandler.BOSS_MUSIC);
        bossStage = new BossStage(game, this, bld);
        player = bld.getPlayer();
        hudStage = new HudStage(game, player, new PauseListener(){
            @Override
            public void pauseClicked() {
                startPause();
            }});
        bld.setHudSettings(hudStage);
        hudStage.createInfoB4Level(bld.getQuestInfo());
        beforeEndStage = new BeforeEndStage(game, this, bld);
        bossBackgroundStage = new BossBackgroundStage(bossStage.getCamera(), bld);
        pauseStage = new PauseStage(game, player, new PauseStageListener() {
            @Override
            public void endPause() {
                BossScreen.this.endPause();
            }

            @Override
            public void toMenu() {
                BossScreen.this.toMenu();
            }

            @Override
            public void startAdjustHud() {
                BossScreen.this.startAdjustHud();
            }
        });
        adjustHudStage = new AdjustHud(hudStage, new AdjustHudStageListener() {
            @Override
            public void startPause() {
                BossScreen.this.startPause();
            }
        });
        bossIntroStage = new BossIntroStage(this, bld);

        gameoverview = new Gameoverview(game, bossStage.getPlayer(), this, bld.getUnlocks());
    }

    @Override
    public void act(float delta) {

    }

    public BossLevelDefining getBossLevelDefining(){
        return bld;
    }

    public void startInfo() {
        hudStage.showInfoBeforeLevel();
    }
}
