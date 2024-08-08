package de.keeyzar.earthragequit.menu.screens.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.github.czyzby.kiwi.util.tuple.mutable.MutablePair;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.Statistic;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.Time_Played;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_coll.Coins_Coll;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_coll.Coins_Val_Coll;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_coll.Fuel_coll;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_hit.Hit_Bees;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_lostGame.Death_Statistic;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_lostGame.Fuel_Statistic;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class StatisticsScreen extends Transitionable {
    private final ERQGame game;
    private Skin skin;

    private Stage stage;

    //which category, mutable Pair: string = name of category array & all Statistics
    private ObjectMap<Integer, MutablePair<String, Array<Statistic>>> categoryMap;

    public StatisticsScreen(ERQGame game){
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        StretchViewport stretchViewport = new StretchViewport(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT, camera);
        stage = new Stage();
        stage.setViewport(stretchViewport);

        skin = ERQAssets.SKIN;
        categoryMap = new ObjectMap<Integer, MutablePair<String, Array<Statistic>>>();

    }


    @Override
    public void draw() {
        stage.draw();
    }

    @Override
    public void act(float delta) {
        stage.act(delta);
    }

    @Override
    public void init() {
        Table motherTable = new SparklingTable(skin);
        TextButton textButtonBack = new TextButton(LANG.format("statistic_navigation_to_menu"), skin);
        textButtonBack.setPosition(PAD_TB_S, stage.getHeight() - B_HEI - PAD_TB_S);
        textButtonBack.setHeight(B_HEI);
        textButtonBack.setWidth(B_WID / 2);
        textButtonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, StatisticsScreen.this, new MainMenuScreen(game)));

            }
        });



        motherTable.setFillParent(true);
        motherTable.add(new Label(LANG.format("statistic_title"), skin)).pad(PAD_TB_N).row();

        Table cTable = new SparklingTable(skin);

        ScrollPane scrollPane = new ScrollPane(cTable, skin);
        motherTable.setBackground(scrollPane.getStyle().background);
        scrollPane.setScrollingDisabled(true, false);
        motherTable.add(scrollPane).pad(PAD_TB_N).padTop(0).grow();


        //initCategoryMap
        initCategoryMap();

        //add Content to contentTable
        addContent(cTable);

        stage.addActor(motherTable);
        stage.addActor(textButtonBack);
    }

    private void initCategoryMap() {
        Array<Statistic> cat_time = new Array<Statistic>();
        cat_time.add(new Time_Played());

        categoryMap.put(0, new MutablePair<String, Array<Statistic>>(LANG.format("statistic_time_title"), cat_time));

        Array<Statistic> cat_death = new Array<Statistic>();
        cat_death.add(new Death_Statistic(game));
        cat_death.add(new Fuel_Statistic(game));

        categoryMap.put(1, new MutablePair<String, Array<Statistic>>(LANG.format("statistic_lost_title"), cat_death));

        Array<Statistic> cat_Coins = new Array<Statistic>();
        cat_Coins.add(new Coins_Coll(game));
        cat_Coins.add(new Coins_Val_Coll(game));
        cat_Coins.add(new Fuel_coll(game));

        categoryMap.put(2, new MutablePair<String, Array<Statistic>>(LANG.format("statistic_collected_title"), cat_Coins));

        Array<Statistic> cat_hit = new Array<Statistic>();
        cat_hit.add(new Hit_Bees(game));

        categoryMap.put(3, new MutablePair<String, Array<Statistic>>(LANG.format("statistic_hitted_stuff_title"), cat_hit));
    }

    /**
     * should add all Content
     * @param cTable
     */
    private void addContent(Table cTable) {

        float timeFactor = 0.15f;
        for(int i = 0; i<categoryMap.size; i++){
            MutablePair<String, Array<Statistic>> objects = categoryMap.get(i);

            Label label = new Label(objects.getFirst(), skin);
            label.getColor().a = 0;
            label.addAction(Actions.sequence(Actions.delay(i * timeFactor), Actions.fadeIn(0.25f)));

            Cell<Label> cell = cTable.add(label).padBottom(ScreenVariables.PAD_TB_S).colspan(2);
            if(i != 0){
                cell.padTop(ScreenVariables.PAD_TB_N);
            }
            cell.row();
            int counter = 0;
            for(Statistic statistic : objects.getSecond()){
                Label label_1 = new Label(statistic.getStatName(), skin);
                label_1.getColor().a = 0;
                label_1.addAction(Actions.sequence(Actions.delay(i*timeFactor + counter * 0.1f), Actions.fadeIn(0.3f)));
                cTable.add(label_1)
                        .padBottom(ScreenVariables.PAD_TB_S)
                        .left()
                        .padRight(ScreenVariables.PAD_LR_N * 8);


                Label label_2 = new Label(statistic.getStatValue(), skin);
                label_2.getColor().a = 0;
                label_2.addAction(Actions.sequence(Actions.delay(i*timeFactor + counter * 0.1f), Actions.fadeIn(0.3f)));
                cTable.add(label_2)
                        .left()
                        .padBottom(ScreenVariables.PAD_TB_S).row();
            }

        }
//        cTable.add(new Label("Test", skin)).right().padRight(ScreenVariables.PAD_LR_N * 5);
//        cTable.add(new Label("2", skin)).right().row();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
}
