package de.keeyzar.earthragequit.menu.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementsScreen;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.custom_ui.planets.PlanetMainMenu;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.help.HelpDialog;
import de.keeyzar.earthragequit.menu.screens.level_selection.LevelSelectScreen;
import de.keeyzar.earthragequit.menu.screens.options.OptionsScreen;
import de.keeyzar.earthragequit.menu.screens.statistics.StatisticsScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.*;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class MainMenuScreen extends Transitionable {
    private ERQGame game;
    private Stage stage;
    private Skin skin;
    private boolean upgradeAllowed = false;
    private boolean isAlreadyInit = false;
    private UpgradeMainDialog upgradeMainDialog;

    public MainMenuScreen(ERQGame game){
        this.game = game;
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        StretchViewport stretchViewport = new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, orthographicCamera);

        stage = new Stage();
        stage.setViewport(stretchViewport);

        skin = SKIN;
    }

    public void init(){
        game.playMenuMusic();
        Button buttonPlay = new TextButton(LANG.format("main_bt_play"), skin, "special");
        Button buttonUpgrades = new TextButton(LANG.format("main_bt_upgrades"), skin, "special");
        Button buttonAchievements = new TextButton(LANG.format("main_bt_achievements"), skin);
        Button buttonOption = new TextButton(LANG.format("main_bt_options"), skin);
        Button buttonHelp = new TextButton(LANG.format("main_bt_help"), skin);
        Button buttonStatistics = new TextButton(LANG.format("main_bt_statistics"), skin);

        final Sprite on = T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_SOUND_ON);
        final Sprite off = T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_SOUND_OFF);
        on.setSize(75, 75);
        off.setSize(75, 75);
        SoundMusicButton musicButton = SoundMusicButton.getToogleButton(true, game, skin, new SpriteDrawable(on), new SpriteDrawable(off));
        SoundMusicButton soundButton = SoundMusicButton.getToogleButton(false, game, skin, new SpriteDrawable(on), new SpriteDrawable(off));
        musicButton.setBounds(ScreenVariables.SCREEN_WIDTH - 250, ScreenVariables.SCREEN_HEIGHT - 125, 100, 100);
        soundButton.setBounds(ScreenVariables.SCREEN_WIDTH - 125, ScreenVariables.SCREEN_HEIGHT - 125, 100, 100);

        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                if (game.getTutorialVerwalter().shouldShowStory()) {
                    checkWhichLevel();
                } else {
                    game.setScreen(new TransitionScreen(game, MainMenuScreen.this, new LevelSelectScreen(game)));
                }
            }
        });
        buttonUpgrades.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                upgradeMainDialog = new UpgradeMainDialog(game, stage);
                upgradeMainDialog.showDialog();


            }
    });
        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, MainMenuScreen.this, new OptionsScreen(game)));
            }
        });
        buttonAchievements.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, MainMenuScreen.this, new AchievementsScreen(game)));
            }
        });
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                new HelpDialog(stage, game, MainMenuScreen.this).createDialog();
            }
        });
        buttonStatistics.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, MainMenuScreen.this, new StatisticsScreen(game)));
            }
        });


        Table table = manageMenuButtons(buttonAchievements, buttonOption, buttonPlay, buttonUpgrades, buttonHelp, buttonStatistics);
        stage.addActor(table);
        stage.addActor(musicButton);
        stage.addActor(soundButton);
        table.setFillParent(true);
        isAlreadyInit = true;
    }

    @Override
    public void act(float delta) {
        stage.act(delta);
        if(upgradeMainDialog != null){
            upgradeMainDialog.act(delta);
        }

    }


    /**
     * If you want to use the stage outside of this screen.
     * @return the MainMenuStage
     */
    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        //some screens dont transition to mainmenu, so init is not called
        if(!isAlreadyInit) {
            init();
        }
        Gdx.input.setInputProcessor(stage);
    }

    public void setUpgradeAllowed(boolean upgradeAllowed) {
        this.upgradeAllowed = upgradeAllowed;
    }

    private Table manageMenuButtons(Button buttonAchievements, Button buttonOption, Button buttonPlay, Button buttonUpgrades,
                                    Button buttonHelp, Button buttonStatistics) {
        Table table = new SparklingTable(skin, 10, false);
        table.addActor(new PlanetMainMenu());
        int tableState = 0;
        if(!game.getTutorialVerwalter().isAbsolved(TVars.UPGRADE_INTRODUCTION) && !upgradeAllowed){
            tableState = 1;
        } else if(game.getTutorialVerwalter().shouldBeAbsolved(TVars.UPGRADE_INTRODUCTION) && upgradeAllowed){
            tableState = 2;
        }

        float b_width = ScreenVariables.B_WID;
        float b_height = ScreenVariables.B_HEI * 1.5f;
        switch (tableState){
            case 1:
                //tutorial to Play
                table.add(buttonPlay).size(b_width, b_height);
                break;
            case 2:
                table.add(buttonPlay).size(b_width, b_height).padBottom(ScreenVariables.PAD_TB_N).row();
                table.add(buttonUpgrades).size(b_width, b_height);
                break;
            case 0:
                //finished tutorial
                table.defaults().pad(ScreenVariables.PAD_TB_N);
                table.add(buttonPlay).size(b_width, b_height).padBottom(ScreenVariables.PAD_TB_S).colspan(3).row();
                table.add(buttonAchievements).spaceTop(50).size(b_width, b_height).bottom().padBottom(ScreenVariables.PAD_TB_S);
                table.add(buttonUpgrades).size(b_width, b_height).top().padBottom(ScreenVariables.PAD_TB_N);
                table.add(buttonStatistics).size(b_width, b_height).bottom().spaceTop(50).padBottom(ScreenVariables.PAD_TB_S).row();
                table.add(buttonHelp).size(b_width, b_height * 0.75f);
                table.add("").expandX();
                table.add(buttonOption).size(b_width, b_height * 0.75f);
                break;
        }
        table.setBackground(skin.get("def", ScrollPane.ScrollPaneStyle.class).background);
        table.setFillParent(true);
        return table;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if(upgradeMainDialog != null){
            upgradeMainDialog.act(delta);
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

    private void checkWhichLevel() {
        game.getTutorialVerwalter().startLevel(game, this);

    }

    @Override
    public void draw() {
        stage.draw();
    }

}

