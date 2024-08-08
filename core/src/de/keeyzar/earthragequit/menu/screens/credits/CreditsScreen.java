package de.keeyzar.earthragequit.menu.screens.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import java.util.ArrayList;
import java.util.Collections;

import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 18.02.2017.
 */
public class CreditsScreen extends Transitionable {
    private ERQGame game;
    private Stage stage;
    private Skin skin;
    private ScrollPane creditsScrollPane;
    private boolean doubleScrollSpeed;
    private Array<SpriteDrawable> sprites;
    private Image imageActor;
    private ArrayList<Integer> list;

    public CreditsScreen(ERQGame game){
        this.game = game;
        game.getMusicHandler().playMusic(MusicHandler.CREDITS_MUSIC);
        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        skin = ERQAssets.SKIN;
    }


    private boolean down = true;
    private float percent = 1.2f;
    private float speed = 0.0005f;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (down) {
            percent -= speed * (doubleScrollSpeed ? 3 : 1);
            creditsScrollPane.scrollTo(0, percent * creditsScrollPane.getMaxY(), 0, 0);

        } else {
            percent += speed * (doubleScrollSpeed ? 3 : 1);
            creditsScrollPane.scrollTo(0, percent * creditsScrollPane.getMaxY(), 0, 0);
        }
        if (percent >= 1.2 || percent <= 0) {
            down = !down;
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

    @Override
    public void draw() {
        stage.draw();
    }

    @Override
    public void init() {
        Table motherTable = new SparklingTable(skin);
        motherTable.setBackground(new ScrollPane(null, skin).getStyle().background);

        TextButton textButtonBack = new TextButton("MENU", skin);
        textButtonBack.setPosition(PAD_TB_S, stage.getHeight() - B_HEI - PAD_TB_S);
        textButtonBack.setHeight(B_HEI);
        textButtonBack.setWidth(B_WID / 2);
        textButtonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                game.setScreen(new TransitionScreen(game, CreditsScreen.this, new MainMenuScreen(game)));

            }
        });

        Label topLabel = new Label("Credits", skin);



        motherTable.add(topLabel).pad(PAD_TB_N).row();
        initContent(motherTable);

        stage.addActor(motherTable);
        stage.addActor(textButtonBack);
        motherTable.setFillParent(true);
    }

    private void initContent(Table motherTable) {

        Table creditsTable = new SparklingTable(skin);
        creditsScrollPane = new ScrollPane(creditsTable, skin);
        creditsScrollPane.setScrollingDisabled(true, false);
        creditsScrollPane.clearListeners();
        creditsScrollPane.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                doubleScrollSpeed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                doubleScrollSpeed = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                //do Nothing
            }
        });
        addCreditsContent(creditsTable);

        Table imageTable = new SparklingTable(skin);
        addImageContent(imageTable);

        Table contentTable = new Table(skin);
        contentTable.add(creditsScrollPane).pad(PAD_TB_N).grow();
        contentTable.add(imageTable).pad(PAD_TB_N).width(300).growY();
        motherTable.add(contentTable).width(SCREEN_WIDTH - 100).height(SCREEN_HEIGHT - 200);

        motherTable.layout();
    }

    private void addImageContent(Table imageTable) {
        sprites = new Array<SpriteDrawable>();
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_ALIEN)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_AL)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_SKINS, 0)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_SKINS, 1)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_SKINS, 2)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BALTHAZAR)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BEE)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.INVISIBLE_CREEP)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BOSSWASP)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.TREASURE_CHEST)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_MAINMENU)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_SKILLSDIALOG)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_UPGRADESITE)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.FUELSTATION)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.SCHROTT)));
        sprites.add(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.SKILL_PLACE)));

        imageActor = new Image();

        createNewList();
        list.remove(Integer.valueOf(0));
        imageActor.addAction(Actions.forever(Actions.sequence(Actions.delay(5),Actions.fadeOut(0.4f), Actions.run(new Runnable() {
            @Override
            public void run() {
                imageActor.setDrawable(sprites.get(list.get(0)));
                list.remove(0);
                if(list.isEmpty()){
                    createNewList();
                }
            }
        }), Actions.fadeIn(0.4f))));
        imageActor.setDrawable(sprites.first());
        imageTable.add(imageActor);
    }

    private void createNewList() {
        list = new ArrayList<Integer>();
        for(int i = 0; i<sprites.size; i++){
            list.add(i);
        }
        Collections.shuffle(list);
    }

    private void addCreditsContent(Table creditsTable) {
        Array<String> allCredits = new Array<String>();
        allCredits.add("KEEYZAR presents");
        allCredits.add("");
        allCredits.add("Earth Rage Quit");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("This game was");
        allCredits.add("created with love");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("Developer:");
        allCredits.add("aka Keeyzar");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("Music & Sound:");
        allCredits.add("http://www.freesfx.co.uk");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("Beta Tester:");
        allCredits.add("Thank you for your effort");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("Special Thanks to:");
        allCredits.add("");
        allCredits.add("Software-engine");
        allCredits.add("LibGDX");
        allCredits.add("which is great(!)");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("What the heck does");
        allCredits.add("someone write");
        allCredits.add("into the credits?");
        allCredits.add("I'm done");
        allCredits.add("");
        allCredits.add("Na. Joke.");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("Last but not least");
        allCredits.add("thank YOU");
        allCredits.add("for playing my game.");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("");
        allCredits.add("COPYRIGHT (c) 2017");
        allCredits.add("BY Keeyzar.");
        allCredits.add("All rights reserved");

        for(String string : allCredits){
            creditsTable.add(new Label(string, skin)).expandX().pad(PAD_TB_S).row();
        }
    }

    @Override
    public void act(float delta) {
        stage.act(delta);


    }
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
}
