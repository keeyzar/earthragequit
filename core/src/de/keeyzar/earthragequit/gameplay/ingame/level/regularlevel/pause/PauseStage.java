package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.pause;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.sound.MusicHandler;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class PauseStage extends Stage{
    private final ERQGame game;
    private final Player player;
    private PauseStageListener pauseStageListener;

    OrthographicCamera camera;

    private Table tableMotherTable;
    private Skin skin;

    //BUTTONS

    TextButton tbToggleMusic;
    TextButton tbToggleSound;
    TextButton tbAdjustHud;
    TextButton tbQuit;
    TextButton tbResume;

    public PauseStage(ERQGame game, Player player, PauseStageListener pauseStageListener) {
        this.game = game;
        this.player = player;
        this.pauseStageListener = pauseStageListener;
        this.skin = ERQAssets.SKIN;
        init();
    }

    private void init() {
        setDebugAll(false);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);
        setViewport(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, camera));
        createTable();
    }

    private void createTable() {
        tbToggleMusic = new TextButton("", skin);
        setMusicText();
        tbToggleSound = new TextButton("", skin);
        setSoundText();
        tbAdjustHud = new TextButton(LANG.format("pause_adjust_hud"), skin);
        tbResume = new TextButton(LANG.format("pause_resume"), skin);
        tbQuit = new TextButton(LANG.format("pause_quit"), skin);



        createListenerForButtons();

        //table which fills whole screen
        tableMotherTable = new Table(skin);
        tableMotherTable.setFillParent(true);

        //table which is colored
        Table colTable = new Table(skin);
        colTable.setBackground(new ScrollPane(null, skin).getStyle().background);

        colTable.add(tbToggleMusic).size(B_WID * 1.5f, B_HEI).colspan(2).padBottom(PAD_TB_N).row();
        colTable.add(tbToggleSound).size(B_WID * 1.5f, B_HEI).colspan(2).padBottom(PAD_TB_N).row();
        colTable.add(tbAdjustHud).size(B_WID * 1.5f, B_HEI).colspan(2).padBottom(PAD_TB_N).row();

        Table tempTable = new Table();
        tempTable.add(tbResume).growX().uniformX().height(B_HEI).padRight(PAD_TB_S);
        tempTable.add(tbQuit).growX().uniformX().height(B_HEI).padLeft(PAD_TB_S);
        colTable.add(tempTable).size(B_WID * 1.5f, B_HEI);

        tableMotherTable.add(colTable).size(B_WID * 2, 600);
        getRoot().getColor().a = 0;
        addActor(tableMotherTable);

    }


    private void createListenerForButtons() {
        
        tbToggleMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                toggleMusic();
            }
        });

        tbToggleSound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSound();
                MenuUtils.playClickSound();
            }
        });
        
        tbAdjustHud.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                startAdjustHud();
            }
        });

        tbResume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                pauseStageListener.endPause();
            }
        });


        tbQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                pauseStageListener.toMenu();
            }
        });
    }

    private void toggleMusic() {
        setMusicAllowed(!isMusicAllowed());
        setMusicText();
    }

    private void setMusicText() {
        String state = isMusicAllowed() ? LANG.format("pause_music_is_on") : LANG.format("pause_music_is_off");
        tbToggleMusic.setText(state);
    }

    private boolean isMusicAllowed(){
        MusicHandler musicHandler = game.getMusicHandler();
        return musicHandler.isMusicAllowed();
    }

    private void setMusicAllowed(boolean musicAllowed){
        MusicHandler musicHandler = game.getMusicHandler();
        musicHandler.setMusicAllowed(musicAllowed);
        musicHandler.actualiseMusicState();
    }

    private void toggleSound() {
        MusicHandler musicHandler = game.getMusicHandler();
        musicHandler.setSoundAllowed(!musicHandler.isSoundAllowed());
        setSoundText();
    }

    private void setSoundText() {
        String state = game.getMusicHandler().isSoundAllowed() ? LANG.format("pause_sound_is_on") : LANG.format("pause_sound_is_off");
        tbToggleSound.setText(state);
    }

    private void startAdjustHud() {
        addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                pauseStageListener.startAdjustHud();
            }
        })));
    }

    /**
     * hide this stage(fadeout)
     * @param finishAnimation
     */
    public void hide(final GameScreen.FinishAnimation finishAnimation) {
        addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                finishAnimation.finishedHiding();
            }
        })));
    }

    /**
     * show this stage (fadein)
     */
    public void show(){
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }

    @Override
    public void dispose() {
        super.dispose();
        pauseStageListener = null;
    }
}
