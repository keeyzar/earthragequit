package de.keeyzar.earthragequit.story.regular_story.first_meeting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * This Story board should show the first Meeting between
 * Alien ronax and crazy scientist Al'
 *
 * ronax is waking up, and he sees a note, wheres something written on it.
 * But he can't read it obviously.
 *
 * Ronax stands up, and walks outside, to see Al' trying to build a rocket, with some
 * pieces that fell on earth with Ronax
 * @author = Keeyzar on 20.03.2016
 */
public class StoryBoard_FirstMeeting extends Transitionable {
    private final ERQGame game;
    Stage stage;
    TalkingStage talkingStage;
    FirstMeetingWorld firstMeetingWorld;


    public StoryBoard_FirstMeeting(ERQGame game) {
        this.game = game;
        stage = new Stage();
        talkingStage = new TalkingStage();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        stage.setViewport(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera));
        firstMeetingWorld = new FirstMeetingWorld();
        stage.addActor(firstMeetingWorld);
        stage.addActor(new RonaxAndAl());

        if(game.getTutorialVerwalter().isAbsolved(TVars.FIRST_MEETING)){
            talkingStage.initSkip(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    finish();
                }
            });
        }
        game.getMusicHandler().playMusic(MusicHandler.STORY_MUSIC);

    }



    private int state = 3;
    private final int S_WAIT = 1;
    private final int S_WAIT_NEXT_TEXT = 2;
    private final int S_TEXT_1 = 3;
    private final int S_TEXT_2 = 4;
    private final int S_TEXT_3 = 5;
    private final int S_TEXT_4 = 6;
    private final int S_FINISHED = 10;
    private int nextState = -1;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isTouched(5)){
            state = S_FINISHED;
        }
        switch (state){
            case S_WAIT:
                if(Gdx.input.isTouched()){
                    talkingStage.endText();
                    state = nextState;
                }
                break;
            case S_WAIT_NEXT_TEXT:
                if(Gdx.input.isTouched()){
                    state = nextState;
                }
                break;
            case S_TEXT_1:
                showText1();
                break;
            case S_TEXT_2:
                showText2();
                break;
            case S_TEXT_3:
                showText3();
                break;
            case S_TEXT_4:
                showText4();
                break;
            case S_FINISHED:
                finish();
                break;
        }

        stage.act(delta);
        stage.draw();
        talkingStage.act(delta);
        talkingStage.draw();
    }

    private void showText4() {
        talkingStage.newText(LANG.format("first_meeting_story_text_4"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_NEXT_TEXT;
                nextState = S_FINISHED;
            }
        });
        state = -1;
    }

    private void showText3() {
        talkingStage.newText(LANG.format("first_meeting_story_text_3"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_NEXT_TEXT;
                nextState = S_TEXT_4;
            }
        });
        state = -1;
    }

    private void showText2() {
        talkingStage.newText(LANG.format("first_meeting_story_text_2"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_NEXT_TEXT;
                nextState = S_TEXT_3;
            }
        });
        state = -1;
    }

    private void showText1() {
        talkingStage.startText(LANG.format("first_meeting_story_text_1"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_NEXT_TEXT;
                nextState = S_TEXT_2;
            }
        });
        state = -1;
    }

    private void finish() {
        game.getTutorialVerwalter().setLevelAbsolved(TVars.FIRST_MEETING, true);
        game.setScreen(new TransitionScreen(game, this, new MainMenuScreen(game)));
        state = -1;
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
    public void show() {
        Gdx.input.setInputProcessor(talkingStage);
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

    }

    @Override
    public void act(float delta) {

    }
}
