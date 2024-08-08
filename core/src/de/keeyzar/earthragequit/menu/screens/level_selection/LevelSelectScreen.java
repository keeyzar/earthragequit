package de.keeyzar.earthragequit.menu.screens.level_selection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.custom_ui.planets.PlanetLevelSelectScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.tutorial.tools.HighlightableButton;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 13.03.2016
 */
public class LevelSelectScreen extends Transitionable{
    private static final String PLAYED_ONCE = "played_once";

    private ERQGame game;
    private Skin skin;
    private Stage stage;

    private Table tLevel;
    private ScrollPane spLevel;
    private Table tLevelInfos, tLevelInfoMain;
    private ScrollPane spLevelInfoMain;
    private int currentSelectedLevel = -1; //nothings selected

    private HighlightableButton tbPlay;

    public LevelSelectScreen(ERQGame game){
        this.game = game;
        this.skin = ERQAssets.SKIN;

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        StretchViewport stretchViewport = new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera);
        stage = new Stage();
        stage.setViewport(stretchViewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void initLevelTable() {
        tLevel = new Table(skin);
        spLevel = new ScrollPane(tLevel, skin, "def");

        ObjectMap<Integer, Boolean> levelLockMap = game.getLevelVerwalter().getLevelLockMap();
        currentSelectedLevel = levelLockMap.size;
        for(int i = levelLockMap.size; i> 0; i--){
            final LevelDescription levelDescription = game.getLevelVerwalter().getLevelDescription(i);
            if(levelLockMap.get(i)){
                currentSelectedLevel--;
                continue; //dont add locked maps
            }
            final TextButton textButton = new TextButton(levelDescription.levelName, skin);


            final int finalI = i;
            textButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    currentSelectedLevel = finalI;
                    actualizeLevelInfos();
                    unselectAllButtonsExceptThis(textButton);
                }
            });
            textButton.addAction(Actions.sequence(Actions.alpha(0), Actions.delay((10 - i) * 0.01f), Actions.fadeIn(0.5f)));
            tLevel.add(textButton).pad(PAD_TB_S).height(B_HEI).growX().row();
        }
        tLevel.add("").grow();
        TextButton button = (TextButton) tLevel.getChildren().get(0);
        UpgradeMainDialog.setButtonSelection(button, true);
    }

    private void unselectAllButtonsExceptThis(TextButton textButton) {
        for(Actor actor : tLevel.getChildren()){
            if(actor instanceof TextButton){
                UpgradeMainDialog.setButtonSelection((TextButton) actor, false);
            }
        }
        UpgradeMainDialog.setButtonSelection(textButton, true);
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

        TextButton textButtonBack = new TextButton(LANG.format("level_select_navigation_to_menu"), skin);
        textButtonBack.setPosition(PAD_TB_S, SCREEN_HEIGHT - B_HEI - PAD_TB_S);
        textButtonBack.setHeight(B_HEI);
        textButtonBack.setWidth(B_WID / 2);
        textButtonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, LevelSelectScreen.this, new MainMenuScreen(game)));

            }
        });
        initLevelTable();
        initLevelInfoTable();


        motherTable.addActor(new PlanetLevelSelectScreen());
        motherTable.add(new Label(LANG.format("level_select_title"), skin)).colspan(3).pad(ScreenVariables.PAD_TB_N).row();
        motherTable.add(spLevel).pad(0, PAD_TB_S, PAD_TB_N, PAD_TB_S).width(B_WID).expandX().left().growY();
        motherTable.add(spLevelInfoMain).pad(0, PAD_TB_S, PAD_TB_N, PAD_TB_S).width(B_WID * 1.5f).expandX().right().growY();

        stage.addActor(motherTable);
        stage.addActor(textButtonBack);
        motherTable.setFillParent(true);


    }

    /**
     * init necessary stuff to display informations for a selected level
     */
    private void initLevelInfoTable() {
        tLevelInfos = new Table(skin);
        tLevelInfoMain = new Table(skin);
        spLevelInfoMain = new ScrollPane(tLevelInfoMain, skin, "def");
        spLevelInfoMain.setScrollingDisabled(true, true);

        ScrollPane spLevelInfo = new ScrollPane(tLevelInfos, skin);


        tbPlay = new HighlightableButton(LANG.format("level_select_button_play"), skin);
        final boolean enable = !game.getGlobalPlayerInformation().loadOnOwn(getClass(), PLAYED_ONCE, false);
        tbPlay.enableHighlight(enable);

        MenuUtils.setEnabled(tbPlay, true, true);
        tbPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                if(enable){
                    game.getGlobalPlayerInformation().saveOnOwn(LevelSelectScreen.this.getClass(), PLAYED_ONCE, true);
                }
                game.getLevelVerwalter().instantiate(game, LevelSelectScreen.this, currentSelectedLevel, false);
            }
        });


        tLevelInfoMain.add(spLevelInfo).pad(PAD_TB_S).grow().row();
        tLevelInfoMain.add(tbPlay).growX().height(B_HEI).pad(PAD_TB_S);
        actualizeLevelInfos();

    }

    private void actualizeLevelInfos() {
        tLevelInfos.reset();
        final LevelDescription levelDescription = game.getLevelVerwalter().getLevelDescription(currentSelectedLevel);
        String level = currentSelectedLevel + " - " + levelDescription.levelName;
        tLevelInfos.defaults().pad(PAD_TB_S).left();
        tLevelInfos.add(new Label(level , skin)).colspan(2).center().row();

        tLevelInfos.add(new Label(LANG.format("level_select_has_boss_level"), skin)).expandX();
        String hasBossLevel = levelDescription.hasBossLevel ? LANG.format("level_select_has_boss_level_yes") : LANG.format("level_select_has_boss_level_no");
        final Label actor = new Label(hasBossLevel, skin);
        tLevelInfos.add(actor).growX().row();

        tLevelInfos.add(new Label(LANG.format("level_select_coin_multiplicator"), skin)).expandX();
        tLevelInfos.add(new Label(LANG.format("level_select_coin_multiplicator_value", levelDescription.coinValue),skin)).expandX().row();
        tLevelInfos.add("").grow().colspan(2);
    }

    @Override
    public void act(float delta) {
        stage.act(delta);
    }

}
