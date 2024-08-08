package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.story.regular_story.demoend.ToBeContinued;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * This is the storyboard, where the user gets a specific item (as example a stone)
 * with which he can enter new dimensions / galaxies
 * @author = Keeyzar on 13.01.2017.
 */
public class StoryBoard_NewDimension extends Transitionable {
    private final ERQGame game;
    private final RonaxAlAndOrb ronaxAlAndOrb = new RonaxAlAndOrb();
    private Stage stage;
    private TalkingStage talkingStage;
    private NewDimensionBackground background;

    private NewDimension_PlayerClone player;
    private NewDimension_StoneClone stoneBoss;
    private DimensionOrb_Part1 dimensionOrb;
    private BlackActor blackActor;

    private DimensionRoom dimensionRoom;
    private Portal portal;

    public StoryBoard_NewDimension(ERQGame game) {
        this.game = game;

        talkingStage = new TalkingStage();

        stage = new Stage();
//        stage.setDebugAll(true);
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        stage.setViewport(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera));


        background = new NewDimensionBackground(camera);
        game.getMusicHandler().playMusic(MusicHandler.STORY_MUSIC);
    }

    @Override
    public void init() {
        player = new NewDimension_PlayerClone(game);
        stoneBoss = new NewDimension_StoneClone(new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                dimensionOrb.start();
                final Sound sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_MISTERY_ORB, Sound.class);
                sound.play(MusicHandler.getSoundVolume());
            }
        });
        final Vector2 position = new Vector2(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
        dimensionOrb = new DimensionOrb_Part1(stoneBoss, position, new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                state = S_FIRST_TEXT;
                player.start();
            }
        });

        blackActor = new BlackActor();
        stage.addActor(player);
        stage.addActor(dimensionOrb);
        stage.addActor(stoneBoss);
        stage.addActor(blackActor);
    }


    private final int S_WAIT_TIME = 1;
    private final int S_WAIT_TEXT = 2;
    private final int S_WAIT_TEXT_NEXT = 3;
    private final int S_WAIT_FOR_ANIM_END = 4;
    private final int S_FIRST_TEXT = 5;
    private final int S_FADE_OUT = 6;
    private final int S_FADE_IN = 7;
    private final int S_PREPARE_SECOND_SCENE = 8;
    private final int S_AL_AND_RONAX = 9;
    private final int S_PARSE_ORB_TO_AL = 10;
    private final int S_AL_SAYS_WHAT_THAT_ORB_IS = 11;
    private final int S_AL_SAYS_WHAT_THAT_ORB_IS_2 = 12;
    private final int S_AL_SAYS_WHAT_THAT_ORB_IS_3 = 13;
    private final int S_SHOW_PORTAL = 14;
    private final int S_AL_SAYS_GOODBYE = 15;
    private final int S_RONAX_GOES_INTO_PORTAL = 16;

//    private final int S_RONAX_SHOWS_RED_BALL = 8;
//    private final int S_AL_EXPLAINS_GALAXY_DRIFT = 9;
//    private final int S_SCENE_CUT_2 = 10;
//    private final int S_AL_SHOWS_NEW_PORTAL = 11;
//    private final int S_AL_SAYS_GOODBYE = 12;
//    private final int S_RONAX_WALKS_THROUGH = 13;
//    private final int S_END_OF_STORYBOARD = 13;
    private int state = S_WAIT_FOR_ANIM_END;
    private int nextState = -1;


    @Override
    public void render(float delta) {
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.NEW_DIMENSION, false);
            game.setScreen(new TransitionScreen(game, StoryBoard_NewDimension.this, new ToBeContinued(game)));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().getCamera().update();
        switch (state){
            case S_WAIT_TIME:
                break;
            case S_WAIT_TEXT:
                waitText(delta);
                break;
            case S_WAIT_TEXT_NEXT:
                waitTextNext(delta);
                break;
            case S_WAIT_FOR_ANIM_END:
                waitForAnimEnd(delta);
                break;
            case S_FIRST_TEXT:
                firstText(delta);
                break;
            case S_FADE_OUT:
                overlayScreenWithBlack(delta);
                break;
            case S_FADE_IN:
                undoOverlay(delta);
                break;
            case S_PREPARE_SECOND_SCENE:
                cleanUpSceneAndPrepareNewScene();
                break;
            case S_AL_AND_RONAX:
                alAndRonaxText();
                break;
            case S_PARSE_ORB_TO_AL:
                parseOrb();
                break;
            case S_AL_SAYS_WHAT_THAT_ORB_IS:
                monologueAl();
                break;
            case S_AL_SAYS_WHAT_THAT_ORB_IS_2:
                monologueAl2();
                break;
            case S_AL_SAYS_WHAT_THAT_ORB_IS_3:
                monologueAl3();
                break;
            case S_SHOW_PORTAL:
                showPortal();
                break;
            case S_AL_SAYS_GOODBYE:
                alSaysGoodbye();
                break;
            case S_RONAX_GOES_INTO_PORTAL:
                ronaxGoesIntoPortal();
                break;
        }

        background.act(delta);
        background.draw();
        stage.act(delta);
        stage.draw();
        talkingStage.act(delta);
        talkingStage.draw();


    }

    private void ronaxGoesIntoPortal() {
        state = -1;
        final Vector2 vector2 = new Vector2(portal.getX() + portal.getWidth() / 2, portal.getY() + portal.getHeight() / 2);
        ronaxAlAndOrb.moveRonaxToPortal(vector2);
        blackActor.remove();
        stage.addActor(blackActor);
        blackActor.start(new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.NEW_DIMENSION, false);
                game.setScreen(new TransitionScreen(game, StoryBoard_NewDimension.this, new ToBeContinued(game)));

            }
        });
    }

    private void alSaysGoodbye() {
        talkingStage.startText(LANG.format("new_dimension_story_text_6"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT_NEXT;
                nextState = S_RONAX_GOES_INTO_PORTAL;
            }
        });
        state = -1;
    }

    private void showPortal() {
        state = -1;
        dimensionRoom.fadeToBlack();
        portal = new Portal(new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                state = S_AL_SAYS_GOODBYE;
            }
        });
        final Vector2 vector2 = new Vector2(portal.getX() + portal.getWidth() / 2, portal.getY() + portal.getHeight() / 2);
        dimensionOrb_part2.moveToPortalAndFadeOut(vector2, new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                stage.addActor(portal);
                ronaxAlAndOrb.setZIndex(50);
            }
        });
        ronaxAlAndOrb.moveAlToRight();
    }

    private void monologueAl3() {
        talkingStage.newText(LANG.format("new_dimension_story_text_5"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT;
                nextState = S_SHOW_PORTAL;
            }
        });
        state = -1;
    }

    private void monologueAl2() {
        talkingStage.newText(LANG.format("new_dimension_story_text_4"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT_NEXT;
                nextState = S_AL_SAYS_WHAT_THAT_ORB_IS_3;
            }
        });
        state = -1;
    }

    private void monologueAl() {
        talkingStage.startText(LANG.format("new_dimension_story_text_3"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT_NEXT;
                nextState = S_AL_SAYS_WHAT_THAT_ORB_IS_2;
            }
        });
        state = -1;
    }

    DimensionOrb_Part2 dimensionOrb_part2;
    private void parseOrb() {
        state = -1;
        dimensionOrb_part2 = new DimensionOrb_Part2(ronaxAlAndOrb.getPositionFromRonax(), new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                state = S_AL_SAYS_WHAT_THAT_ORB_IS;
            }
        });
        stage.addActor(dimensionOrb_part2);
    }

    private void alAndRonaxText() {
        talkingStage.startText(LANG.format("new_dimension_story_text_2"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT;
                nextState = S_PARSE_ORB_TO_AL;
            }

        });
        state = -1;
    }

    private void undoOverlay(float delta) {
        state = -1;
        blackActor.finish(new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                state = S_AL_AND_RONAX;
            }
        });
    }

    private void overlayScreenWithBlack(float delta) {
        state = -1;
        blackActor.start(new NewDimension_StoneClone.Callback() {
            @Override
            public void finishedAnim() {
                state = S_PREPARE_SECOND_SCENE;
            }
        });
    }

    private void cleanUpSceneAndPrepareNewScene() {
        state = S_FADE_IN;
        stage.clear();
        dimensionRoom = new DimensionRoom();
        stage.addActor(dimensionRoom);
        stage.addActor(ronaxAlAndOrb);

        stage.addActor(blackActor);
    }

    private void waitForAnimEnd(float delta) {
        state = -1;
    }

    private void firstText(float delta) {
        talkingStage.startText(LANG.format("new_dimension_story_text_1"), new TWListener(){
            @Override
            public void TShown() {
                state = S_WAIT_TEXT;
                nextState = S_FADE_OUT;
            }

        });
        state = -1;
    }



    ////////////////////////    UNIMPORTANT     ///////////////


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
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        talkingStage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        talkingStage.dispose();
        background.dispose();
    }

    ////////////////////////    EMPTINESS      ////////////////
    ////////////////////////    EMPTINESS      ////////////////
    ////////////////////////    EMPTINESS      ////////////////

    @Override
    public void act(float delta) {
        //nothing
    }

    @Override
    public void show() {
        //emptyy
    }


    @Override
    public void pause() {
        //nothing
    }

    @Override
    public void resume() {
        //nothing
    }

    @Override
    public void hide() {
        //nothing
    }

}
