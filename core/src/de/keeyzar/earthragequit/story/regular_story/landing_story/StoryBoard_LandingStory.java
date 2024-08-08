package de.keeyzar.earthragequit.story.regular_story.landing_story;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;
import de.keeyzar.earthragequit.story.regular_story.first_meeting.StoryBoard_FirstMeeting;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 17.03.2016
 */
public class StoryBoard_LandingStory extends Transitionable{
    private final ERQGame game;
    Stage stage;

    BackgroundActor backgroundActor;
    PlayerClone playerClone;
    TalkingStage talkingStage;
    DelayAction timer;
    private boolean nextScreen = false;


    ERQPicActor erqPicActor;

    public StoryBoard_LandingStory(ERQGame game){
        this.game = game;
        stage = new Stage();
        talkingStage = new TalkingStage();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        stage.setViewport(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera));
        timer = Actions.delay(1f);
        nextState = S_START_TALKING_2;
        if(game.getTutorialVerwalter().isAbsolved(TVars.ROCKET_EXPLODED))
        talkingStage.initSkip(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                finish();
            }
        });
        game.getMusicHandler().playMusic(MusicHandler.STORY_MUSIC);

    }

    public void show(){

    }


    private int state = 1;
    private final int S_WAIT_TIME = 1;
    private final int S_WAIT_TEXT = 2;
    private final int S_WAIT_TEXT_NEXT = 3;
    private final int S_START_TALKING_2 = 5;
    private final int S_START_ROCKET_ERROR = 6;
    private final int S_START_TALKING_3 = 7;
    private final int S_ROCKET_EXPLOSION = 10;
    private final int S_WAIT_FOR_FINISH = 11;
    private final int S_SHOW_ERQ = 12;
    private int nextState = -1;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isTouched(5)){
            state = S_SHOW_ERQ;
        }
        switch (state){
            case S_WAIT_TIME:
                waitTime(delta);
                break;
            case S_WAIT_TEXT:
                waitText(delta);
                break;
            case S_WAIT_TEXT_NEXT:
                waitTextNext(delta);
                break;
            case S_START_TALKING_2:
                startTalking2();
                break;
            case S_START_ROCKET_ERROR:
                rocketError();
                break;
            case S_START_TALKING_3:
                startTalking3();
                break;
            case S_ROCKET_EXPLOSION:
                rocketExplosion();
                break;
            case S_WAIT_FOR_FINISH:
                finishChecker();
                break;
            case S_SHOW_ERQ:
                showERQ();
                break;
        }

        stage.act(delta);
        stage.draw();
        talkingStage.act(delta);
        talkingStage.draw();
    }

    private void showERQ(){
        if(!erqPicActor.hasActions()){
            finish();
        }
    }

    private void finishChecker() {
        if(playerClone.isFinished()){
            state = S_SHOW_ERQ;
            erqPicActor.addAction(Actions.sequence(Actions.fadeIn(2), Actions.delay(3)));
        }
    }
    private void finish() {
        if(!nextScreen){
            nextScreen = true;
            game.getTutorialVerwalter().setLevelAbsolved(TVars.ROCKET_EXPLODED, true);
            game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.FIRST_MEETING, true);
            game.setScreen(new TransitionScreen(game, this, new StoryBoard_FirstMeeting(game)));
        }
    }

    private void rocketExplosion() {
        playerClone.explosion();
        state = S_WAIT_FOR_FINISH;
    }

    private void startTalking3() {
        talkingStage.startText(LANG.format("landing_story_text_2"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT;
                nextState = S_ROCKET_EXPLOSION;

            }
        });
        state = -1;
    }

    private void rocketError() {
        playerClone.rotateTo45();
        state = S_WAIT_TIME;
        nextState = S_START_TALKING_3;
        timer.setTime(0);
        timer.setDuration(3);
    }

    private void startTalking2() {
        talkingStage.startText(LANG.format("landing_story_text_1"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT;
                nextState = S_WAIT_TIME;
                timer.setDuration(2);
                timer.setTime(0);
            }

            @Override
            public void TFadeOutFinish() {
                nextState = S_START_ROCKET_ERROR;
            }
        });
        state = -1;
    }


    /////////////////////////UNIMPORTANT/////


    private void waitTime(float delta) {
        timer.act(delta);
        if(timer.getTime() > timer.getDuration()){
            state = nextState;
            timer.reset();
        }
    }



    private void waitTextNext(float delta) {
        if(Gdx.input.isTouched()){
            state = nextState;
        }
    }

    private void waitText(float delta) {
        if(Gdx.input.isTouched()){
            talkingStage.endText();
            state = nextState;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        talkingStage.getViewport().update(width, height);
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
        stage.dispose();
        talkingStage.dispose();
    }

    @Override
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void init() {
        backgroundActor = new BackgroundActor();
        playerClone = new PlayerClone(game);
        stage.addActor(backgroundActor);
        stage.addActor(playerClone);
        playerClone.moveToMid();
        erqPicActor = new ERQPicActor(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        erqPicActor.getColor().a = 0;
        stage.addActor(erqPicActor);
        Gdx.input.setInputProcessor(talkingStage);
    }

    @Override
    public void act(float delta) {

    }
}
