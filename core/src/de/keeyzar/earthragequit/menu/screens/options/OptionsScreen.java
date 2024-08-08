package de.keeyzar.earthragequit.menu.screens.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.loading.languagedetection.LanguageDetectionScreen;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.credits.CreditsScreen;
import de.keeyzar.earthragequit.menu.screens.elements.DialogAnswer;
import de.keeyzar.earthragequit.menu.screens.elements.ERQDialog;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 08.02.2016.
 */
public class OptionsScreen extends Transitionable{
    private ERQGame game;
    private Stage stage;
    private Skin skin;

    private Table tOptions;
    private ScrollPane spOptions;
    private final int B_WIDTH = 300;


    public OptionsScreen(ERQGame game){
        this.game = game;

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        StretchViewport stretchViewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, orthographicCamera);
        stage = new Stage();
        stage.setViewport(stretchViewport);
        skin = ERQAssets.SKIN;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void initOptions() {
        //init table
        tOptions = new SparklingTable(skin);
        //init scrollpane
        spOptions = new ScrollPane(tOptions, skin);

        final TextButton textButton = new TextButton(LANG.format("options_change_language"), skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TransitionScreen(game, OptionsScreen.this, new LanguageDetectionScreen(game)));
            }
        });

        tOptions.add(textButton).size(B_WIDTH, B_HEI).padBottom(PAD_TB_N).row();

        //init Credits
        initCredits();

        //init Reset
        initReset();


    }

    private void initCredits() {
        TextButton tbCredits = new TextButton(LANG.format("options_credits_button"), skin);
        tbCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, OptionsScreen.this, new CreditsScreen(game)));
            }
        });
        tOptions.add(tbCredits).size(B_WIDTH, 75).padBottom(ScreenVariables.PAD_TB_N).row();
    }

    private void initReset() {
        TextButton textButton = new TextButton(LANG.format("options_reset_button"), skin);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                ERQDialog erqDialog = new ERQDialog(stage, LANG.format("options_reset_dialog_text"),
                        LANG.format("options_reset_dialog_ok"),
                        LANG.format("options_reset_dialog_canel"),
                        new DialogAnswer() {
                            @Override
                            public void confirmed() {
                                game.resetPlayerData();
                            }
                        });
                erqDialog.createDialogCritical(true);
            }
        });
        tOptions.add(textButton).size(B_WIDTH, 75);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

    @Override
    public void draw() {
        stage.draw();
    }

    @Override
    public void init() {
        Table motherTable = new SparklingTable(skin);
        motherTable.setBackground(new ScrollPane(null, skin).getStyle().background);

        TextButton textButtonBack = new TextButton(LANG.format("options_navigation_to_menu"), skin);
        textButtonBack.setPosition(PAD_TB_S, SCREEN_HEIGHT - B_HEI - PAD_TB_S);
        textButtonBack.setHeight(B_HEI);
        textButtonBack.setWidth(B_WID / 2);
        textButtonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, OptionsScreen.this, new MainMenuScreen(game)));

            }
        });

        initOptions();



        motherTable.add(new Label(LANG.format("options_titel"), skin)).pad(ScreenVariables.PAD_TB_N).row();
        motherTable.add(spOptions).pad(PAD_TB_N).padTop(0).grow();

        stage.addActor(motherTable);
        stage.addActor(textButtonBack);
        motherTable.setFillParent(true);
    }

    @Override
    public void act(float delta) {
        stage.act(delta);
    }
}
