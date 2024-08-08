package de.keeyzar.earthragequit.tutorial.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.tutorial.movement.introstage.BackgroundIntroStage;
import de.keeyzar.earthragequit.tutorial.movement.introstage.TutorialStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class GamePlayTutorialScreen extends Transitionable {
    private ERQGame game;
    private int state = 1;
    private final int STATE_GAMEPLAY =1;
    BackgroundIntroStage backgroundStage;
    TutorialStage tutorialStage;
    TalkingStage talkingStage;
    HudStage hudStage;
    private InputMultiplexer inputMultiplexer;

    public GamePlayTutorialScreen(ERQGame game) {
        this.game = game;
        inputMultiplexer = new InputMultiplexer();

        //stages to playThrough
        talkingStage = new TalkingStage();
        tutorialStage = new TutorialStage(game, this, talkingStage);
        hudStage = new HudStage(game, tutorialStage.getPlayer(), null);
        hudStage.setTutorial(true);
        hudStage.enable(false, false, false);
        hudStage.setEnemyProgressBarEnabled(tutorialStage.getBossProgress());
        hudStage.createSteuerung();
        tutorialStage.getPlayer().setHudStage(hudStage);
        tutorialStage.setHudStage(hudStage);
        talkingStage.setDisabledEnableInput(inputMultiplexer, hudStage);
        backgroundStage = new BackgroundIntroStage((OrthographicCamera) tutorialStage.getCamera());
        game.getMusicHandler().playMusic(MusicHandler.INTRO_MUSIC);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(talkingStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state){
            case STATE_GAMEPLAY:
                renderGameplay(delta);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        tutorialStage.getViewport().update(width, height);
        talkingStage.getViewport().update(width, height);
        hudStage.getViewport().update(width, height);
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
        tutorialStage.dispose();
        talkingStage.dispose();
        hudStage.dispose();
        backgroundStage.dispose();
    }




    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
                            //RENDERING//
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    private void renderGameplay(float delta){
        backgroundStage.draw();
        tutorialStage.act(delta);
        tutorialStage.draw();
        hudStage.act(delta);
        hudStage.draw();
        talkingStage.act(delta);
        talkingStage.draw();
    }


    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
                                //MISC//
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    @Override
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void init() {

    }

    @Override
    public void act(float delta) {

    }
}
