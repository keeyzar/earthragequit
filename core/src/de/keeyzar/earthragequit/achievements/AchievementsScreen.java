package de.keeyzar.earthragequit.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.SingleButtonDialog;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * Created by Keeyzar on 08.02.2016.
 */
public class AchievementsScreen extends Transitionable {
    private ERQGame game;
    private Stage stage;
    private Skin skin;

    private ScrollPane spAchievement;

    private int achievementsGathered;


    public AchievementsScreen(ERQGame game){
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

    private void initImageButtons() {
        AchievementVerwalter achievementVerwalter = game.getAchievementVerwalter();
        ObjectMap<Integer, Achievement> achievementMap = achievementVerwalter.getAchievementMap();
        ObjectMap.Entries<Integer, Achievement> iterator = achievementMap.iterator();
        int counter = 1;
        Table tAchievement = new SparklingTable(skin);
        while(iterator.hasNext()){
            ObjectMap.Entry<Integer, Achievement> next = iterator.next();
            final Achievement achiev = next.value;
            boolean finished = achiev.isFinished();

            String theSkin = finished ? "finnish" : "disabled";

            String text = achiev.getTitle();
            text += "\n";
            text += achiev.getProgress() + "%";

            if(achiev.shouldHide() && !finished){
                text = "?!?";
            }

            TextButton textButton = new TextButton(text, skin, theSkin);
            textButton.getLabel().setWrap(true);
            if(!finished){
                textButton.setTouchable(Touchable.disabled);
            }
            textButton.addAction(new SequenceAction(Actions.alpha(0, 0), Actions.delay((counter - 1) * 0.05f), Actions.fadeIn(1)));
            if (achiev.isFinished()) {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        MenuUtils.playClickSound();
                        SingleButtonDialog.createDialog(stage, achiev.getAchievementText(),
                                LANG.format("achievements_info_dialog_close"), 800, 300);
                    }
                });
            }

            Cell<TextButton> cell = tAchievement.add(textButton).size(190, 190).pad(PAD_TB_S).expand();

            if (counter % 4 == 0) {
                cell.row();
            }
            if (achiev.isFinished()) {
                achievementsGathered++;
            }
            counter++;
            spAchievement = new ScrollPane(tAchievement, skin);
            spAchievement.setScrollingDisabled(true, false);
            spAchievement.setFadeScrollBars(true);
        }

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

        TextButton textButtonBack = new TextButton(LANG.format("achievements_navigation_to_menu"), skin);
        textButtonBack.setPosition(PAD_TB_S, stage.getHeight() - B_HEI - PAD_TB_S);
        textButtonBack.setHeight(B_HEI);
        textButtonBack.setWidth(B_WID / 2);
        textButtonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, AchievementsScreen.this, new MainMenuScreen(game)));

            }
        });

        initImageButtons();
        Label topLabel = new Label(LANG.format("achievements_title", achievementsGathered, game.getAchievementVerwalter().getAchievementMap().size), skin);

        motherTable.add(topLabel).pad(PAD_TB_N).row();
        motherTable.add(spAchievement).pad(PAD_TB_N).padTop(0).grow();

        stage.addActor(motherTable);
        stage.addActor(textButtonBack);
        motherTable.setFillParent(true);
    }

    @Override
    public void act(float delta) {
        stage.act(delta);
    }
}
