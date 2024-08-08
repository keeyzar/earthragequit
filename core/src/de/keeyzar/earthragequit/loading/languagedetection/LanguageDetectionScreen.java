package de.keeyzar.earthragequit.loading.languagedetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 06.03.2017.
 */
public class LanguageDetectionScreen extends Transitionable {
    private ERQGame game;
    private Stage stage;
    private Label label;
    private TextButton buttonRdy;
    private Skin skin;

    public LanguageDetectionScreen(ERQGame game){
        this.game = game;
        stage = new Stage(new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT));
        skin = ERQAssets.SKIN;
    }

    private void generateStage(){
        Gdx.input.setInputProcessor(stage);

        SparklingTable table = new SparklingTable(skin);
        table.setBackground(new ScrollPane(null, skin).getStyle().background);
        table.setFillParent(true);

        label = new Label("", skin);
        TextButton buttonGerman = new TextButton("German", skin);
        TextButton buttonEnglish = new TextButton("English", skin);
        buttonRdy = new TextButton("", skin);

        addListeners(buttonGerman, buttonEnglish);

        table.add(label).padBottom(ScreenVariables.PAD_TB_N).row();
        table.add(buttonEnglish).width(ScreenVariables.B_WID).height(ScreenVariables.B_HEI).padBottom(ScreenVariables.PAD_TB_S).row();
        table.add(buttonGerman).width(ScreenVariables.B_WID).height(ScreenVariables.B_HEI).padBottom(ScreenVariables.PAD_TB_N).row();
        table.add(buttonRdy).width(ScreenVariables.B_WID).height(ScreenVariables.B_HEI);

        setTexts();

        stage.addActor(table);
    }

    private void addListeners(TextButton buttonGerman, TextButton buttonEnglish) {
        buttonRdy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TransitionScreen(game, LanguageDetectionScreen.this, new MainMenuScreen(game)));
            }
        });

        buttonEnglish.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLanguage(ERQAssets.COUNTRY_CODE_EN);
            }
        });

        buttonGerman.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLanguage(ERQAssets.COUNTRY_CODE_DE);
            }
        });
    }

    private void setLanguage(String languageCode) {
        ERQAssets.loadLocalization(languageCode);
        game.getGlobalPlayerInformation().setDefaultLang(languageCode);
        setTexts();
    }

    private void setTexts(){
        label.setText(ERQAssets.LANG.format("misc_translation_title"));
        buttonRdy.setText(ERQAssets.LANG.format("misc_translation_button"));
    }

    @Override
    public void init() {
        generateStage();
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void show() {
        generateStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(0.025f, Gdx.graphics.getDeltaTime()));
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
        render(Gdx.graphics.getDeltaTime());
    }
}
