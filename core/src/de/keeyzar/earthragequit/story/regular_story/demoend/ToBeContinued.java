package de.keeyzar.earthragequit.story.regular_story.demoend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 14.01.2017.
 */
public class ToBeContinued extends Transitionable {
    private ERQGame game;

    private Stage stage;
    private ToBeContinuedActor toBeContinuedActor;
    private Actor actor;
    private boolean touchAllowed = false;

    public ToBeContinued(final ERQGame game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        stage = new Stage(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera));

        toBeContinuedActor = new ToBeContinuedActor();
        stage.addActor(toBeContinuedActor);

        actor = new Actor();
        actor.addAction(Actions.sequence(Actions.delay(16), Actions.run(new Runnable() {
            @Override
            public void run() {
                touchAllowed = true;
                game.setScreen(new TransitionScreen(game, ToBeContinued.this, new MainMenuScreen(game)));
                game.getAchievementVerwalter().getAchievementMap().get(AVars.DEMO_FINISHED).checkConditionsAndApplyIfTrue();
            }
        })));
        stage.addActor(actor);
        game.getMusicHandler().playMusic(MusicHandler.TO_BE_CONTINUED_MUSIC);
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
    }
}
