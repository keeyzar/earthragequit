package de.keeyzar.earthragequit.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.loading.languagedetection.LanguageDetectionScreen;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * Created by Keeyzar on 08.02.2016.
 */
public class LoadingScreen implements Screen {
    private final Tap tap;
    private ERQGame game;
    private Stage stage;
    private Sprite introLogoSprite;
    private Actor loadingBalken;
    private AssetManager assetManager;

    private int state = 1;
    private static final int S_LOADING = 2;
    private boolean started = false;

    public LoadingScreen (ERQGame game) {
        this.game = game;
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        StretchViewport stretchViewport = new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, orthographicCamera);
        stage = new Stage();
        stage.setViewport(stretchViewport);
        Gdx.input.setInputProcessor(stage);

        loadBeforeLoading();

        Texture introLogoTexture = assetManager.get(AssetVariables.STORY_MyStory, Texture.class);
        Texture tapTexture = assetManager.get(AssetVariables.TAP, Texture.class);


        tap = new Tap(tapTexture);
        introLogoSprite = new Sprite(introLogoTexture);
        loadingBalken = new LoadingBalken(assetManager);
    }


    @Override
    public void show() {
        Table table = new Table();
        MyStory loadingActor = new MyStory(introLogoSprite, this);
        table.add(loadingActor);
        table.setFillParent(true);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        ERQAssets.startLoading();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (state){
            case S_LOADING:
                if(ERQAssets.MANAGER.update()) {
                        startTapInfo();
                    if (Gdx.input.isTouched()) {
                        ERQAssets.done();
                        dispose();
                        if(!game.getGlobalPlayerInformation().getDefaultLang().equals("")) {
                            game.setScreen(new MainMenuScreen(game));
                        } else {
                            game.setScreen(new LanguageDetectionScreen(game));
                        }
                    }
                }
                break;
            default: ERQAssets.MANAGER.update();
        }
        stage.act(Math.min(0.025f, delta));
        stage.draw();
    }

    private void startTapInfo() {
        if(!started){
            started = true;
            stage.addActor(tap);
            tap.start();
        }
    }

    public void startLoading(){
        stage.clear();
        state = S_LOADING;
        Table table = new Table();
        table.add(loadingBalken);
        table.setFillParent(true);
        stage.addActor(table);
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


    private void loadBeforeLoading() {
        assetManager = ERQAssets.MANAGER;
        assetManager.load(AssetVariables.STORY_MyStory, Texture.class);
        assetManager.load(AssetVariables.TAP, Texture.class);
        assetManager.load(AssetVariables.UI_SKIN_ATLAS, TextureAtlas.class);
        assetManager.load(AssetVariables.UI_SKIN_JSON, Skin.class, new SkinLoader.SkinParameter(AssetVariables.UI_SKIN_ATLAS));
        assetManager.finishLoading();
    }
}
