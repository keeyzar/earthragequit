package de.keeyzar.earthragequit.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 21.03.2016
 */
public class TransitionScreen implements Screen {
    private final ERQGame game;
    private Transitionable oldScreen;
    private Transitionable nextScreen;

    private Stage stage;

    private TransActor lAct, rAct;
    boolean loaded = true;
    private boolean drewNextScreen = false;
    private boolean startOpening = false;

    public TransitionScreen(ERQGame game, Transitionable oldScreen, Transitionable nextScreen){
        this.game = game;
        this.oldScreen = oldScreen;
        this.nextScreen = nextScreen;

        stage = new Stage();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        stage.setViewport(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        initActors();

    }




    private void initActors() {
        lAct = new TransActor(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.LOADING_TRANS, 1), TransActor.DIR_LEFT);
        rAct = new TransActor(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.LOADING_TRANS, 2), TransActor.DIR_RIGHT);

        stage.addActor(lAct);
        stage.addActor(rAct);
        //start it
        lAct.closeScreen();
        rAct.closeScreen();
    }


    @Override
    public void show() {

    }


    private int state = 1;
    private final int S_CLOSE_FIRST = 1;
    private final int S_LOAD = 2;
    private final int S_OPEN_NEXT = 3;
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        nextScreen.act(delta);
        switch (state){
            case S_CLOSE_FIRST:
                oldScreen.draw();
                checkForClose();
                break;
            case S_LOAD:
                load();
                break;
            case S_OPEN_NEXT:
                drewNextScreen = true;
                nextScreen.draw();
                checkForOpen();
                break;
        }

        if(drewNextScreen && !startOpening){
            lAct.openScreen();
            rAct.openScreen();
            startOpening = true;
        }

        //initializing often causes a big increase in delta.
        stage.act(Math.min(delta, 0.020f));
        stage.draw();

    }

    private void checkForOpen() {
        if(lAct.isOpened() && rAct.isOpened()){
            this.dispose();
            game.setScreen(nextScreen);

        }
    }

    private void load() {
        if(loaded){
            loaded = false;
            state = S_OPEN_NEXT;
            nextScreen.init();
            nextScreen.resize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());

        }
    }

    private void checkForClose() {
        if(lAct.isClosed() && rAct.isClosed()){
            state = S_LOAD;
            oldScreen.dispose();
        }
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
