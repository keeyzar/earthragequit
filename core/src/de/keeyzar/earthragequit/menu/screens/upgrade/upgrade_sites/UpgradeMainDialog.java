package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.custom_ui.planets.PlanetUpgradeSite;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.Gameoverview;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.CoinAnimActor;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.SkillSiteListener;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.UpgradeSiteSkills;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.UpgradeSiteSkins;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.UpgradeSiteStats;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.tutorial.skills.SkillsTutorial;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 *
 * $author Keeyzar on 26.04.2016
 */
public class UpgradeMainDialog {
    private Sound buySound;
    private Sound equipSound;
    public static final int SITE_SKINS = 2;
    public static final int SITE_STATS = 3;
    public static final int SITE_SKILLS = 4;
    public static final int DIALOG_SKILLS_IF_FREE = 5;
    private int firstSite = SITE_STATS; //default site


    private ERQGame game;
    private Skin skin;
    private Stage stage;
    private Stage oldStage;

    private Table motherTable, siteContainer;
    private Label titelLabel, coinLabel;
    private TextButton tbMenu, tbSkins, tbStats, tbSkills;

    private UpgradeSite usSkins, usStats, usSkills;


    //tutorial specific
    private IntroductionHook introductionHook;
    private int whichSite = 0;
    private boolean tutorialMode = false;

    private TextButton.TextButtonStyle tbSelected;
    private TextButton.TextButtonStyle defSkin;
    private Gameoverview.ActionListener closeListener;
    private int actuallyDisplayedCoins;

    /**
     * creates the upgradeDialog
     * @param game gameParam
     * @param oldStage the old stage, so we can set the input processor again on this one, so we
     *                 are able to perform normal input processing again
     */
    public UpgradeMainDialog(ERQGame game, Stage oldStage){
        this.game = game;
        this.stage = new Stage();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage.setViewport(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera()));
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.oldStage = oldStage;
        skin = ERQAssets.SKIN;
        init();
    }


    /**
     * Tweeked UpgradePage for Introductions.
     * @param game
     * @param introductionHook to include talkingstage etc.
     */
    public UpgradeMainDialog(ERQGame game, Stage oldStage, IntroductionHook introductionHook){
        this.game = game;
        stage = new Stage();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage.setViewport(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera()));
        this.oldStage = oldStage;
        skin = ERQAssets.SKIN;

        tutorialMode = true;
        this.introductionHook = introductionHook;
        this.whichSite = introductionHook.getWhichSite();

        //manually resize, because it doesnt get called from the first resize, but later it should be called
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //inject reference from a transitionable into introductionHook (to switch screens or more)
        introductionHook.setDialog(this);
        init();
    }

    public void setSiteToOpen(int whichSite){
        if(whichSite == DIALOG_SKILLS_IF_FREE){
            setPage(SITE_SKILLS);
            ((UpgradeSiteSkills)usSkills).openDialogIfEmptySkill();
        } else {
            setPage(whichSite);
        }
    }



    public void init() {
        Gdx.input.setInputProcessor(stage);
        if(tbSelected == null){
            tbSelected = skin.get("selected", TextButton.TextButtonStyle.class);
            defSkin = skin.get(TextButton.TextButtonStyle.class);
        }

        UpgradeMainDialog.Listener listener = new UpgradeMainDialog.Listener() {
            @Override
            public void updateAll() {
                actualiseAll();
            }

            @Override
            public void playBuySound() {
                UpgradeMainDialog.this.playBuySound();
            }

            @Override
            public void playEquipSound() {
                UpgradeMainDialog.this.playEquipSound();
            }
        };

        usSkins = new UpgradeSiteSkins(game, stage, listener);
        usStats = new UpgradeSiteStats(game, stage, listener);
        SkillSiteListener skl = null;
        if(tutorialMode && introductionHook instanceof SkillsTutorial){
            skl = new SkillSiteListener() {
                @Override
                public void windowClosed() {
                    ((SkillsTutorial) introductionHook).closed();
                }

                @Override
                public void newSkillChosen() {
                    ((SkillsTutorial) introductionHook).closed();
                }

                @Override
                public void openedDialog() {
                    ((SkillsTutorial) introductionHook).opened();
                }
            };
        }
        usSkills = new UpgradeSiteSkills(game, stage, listener, skl);


        motherTable = new SparklingTable(skin);
        motherTable.setBackground(new ScrollPane(null, skin).getStyle().background);
        siteContainer = new SparklingTable(skin);
        siteContainer.setBackground(new ScrollPane(null, skin, "divider").getStyle().background);

        tbMenu = new TextButton(LANG.format("upgrades_navigation_to_menu"), skin);
        tbMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                UpgradeMainDialog.this.finish();
            }
        });

        titelLabel = new Label("Titel", skin);
        coinLabel = new Label(LANG.format("upgrades_actual_coins", 0), skin){
            private int counter = 0;
            @Override
            public void act(float delta) {
                counter++;
                if(counter > 5){
                    counter = 0;
                    if(game.getCoins() != actuallyDisplayedCoins){
                        actualiseAll();
                    }
                }
                super.act(delta);
            }
        };

        addFancyStuff();
        Table titleTable = new Table(skin);
        titleTable.add(tbMenu).padTop(PAD_TB_S).padBottom(PAD_TB_S).padLeft(PAD_LR_S).size(B_WID / 2, B_HEI);
        titleTable.add(titelLabel).minWidth(300).padTop(PAD_TB_S).padBottom(PAD_TB_S).expandX().padRight(PAD_LR_S);
        titleTable.add(new CoinAnimActor()).size(50, 50).padRight(PAD_TB_S).right().expandX();
        titleTable.add(coinLabel).padTop(PAD_TB_S).padBottom(PAD_TB_S).padRight(PAD_LR_S).left().expandX().row();

        Table tempTable = new Table(skin);

        Table buttonTable = new Table(skin);
        addButtons(buttonTable);


        siteContainer.add("").fill();
        tempTable.add(siteContainer).uniformY().grow().padRight(PAD_LR_S);
        tempTable.add(buttonTable).uniformY().growY().width(B_WID / 2);


        motherTable.add(titleTable).colspan(4).width(ScreenVariables.SCREEN_WIDTH).row();
        motherTable.add(tempTable).fill().expand().colspan(4).padBottom(PAD_TB_S).padLeft(PAD_LR_S).padRight(PAD_LR_S);
        motherTable.setFillParent(true);
        stage.addActor(motherTable);

        if(tutorialMode){
            prepareTutorial();
        }

        //add fancy stuff


        actualiseAll();
        setPage(firstSite);
    }


    private void addFancyStuff() {
        motherTable.addActor(new PlanetUpgradeSite());
    }

    /**
     * does necessary stuff for tutorialmode
     */
    private void prepareTutorial() {
        MenuUtils.setEnabled(tbSkins, false);
        MenuUtils.setEnabled(tbStats, false);
        MenuUtils.setEnabled(tbSkills, false);
        //only activate the right screen
        switch (whichSite){
            case SITE_SKINS:
                MenuUtils.setEnabled(tbSkins, true);
                firstSite = SITE_SKINS;
                break;
            case SITE_STATS:
                MenuUtils.setEnabled(tbStats, true);
                firstSite = SITE_STATS;
                break;
            case SITE_SKILLS:
                MenuUtils.setEnabled(tbSkills, true);
                firstSite = SITE_SKILLS;
                break;
        }
        //disable menu, player can exit this screen in tutorialmode only via
        //fulfilled Quests
        MenuUtils.setEnabled(tbMenu, false);
    }

    private void checkButtonVisibility() {
        MenuUtils.setEnabled(tbSkills, !game.getSkillsVerwalter().isLocked(1));
    }

    private void actualiseAll() {
        actuallyDisplayedCoins = game.getCoins();
        coinLabel.setText(LANG.format("upgrades_actual_coins", actuallyDisplayedCoins));
        usSkins.updateAll();
        usStats.updateAll();
    }

    /**
     * calls setSite in the upgradeSite
     * @param whichSite which upgradeSite ({@link UpgradeMainDialog} constants)
     */
    private void setPage(int whichSite) {
        siteContainer.reset();
        unselectAll();
        switch (whichSite){
            case SITE_SKINS:
                usSkins.setSite(siteContainer, titelLabel);
                setButtonSelection(tbSkins, true);
                break;
            case SITE_STATS:
                usStats.setSite(siteContainer, titelLabel);
                setButtonSelection(tbStats, true);
                break;
            case SITE_SKILLS:
                usSkills.setSite(siteContainer, titelLabel);
                setButtonSelection(tbSkills, true);
                break;
        }
        siteContainer.getCells().first().pad(PAD_TB_S);
    }

    private void addButtons(Table buttonTable) {
        tbSkins = new TextButton(LANG.format("upgrades_navigation_to_skins"), skin);
        tbStats = new TextButton(LANG.format("upgrades_navigation_to_stats"), skin);
        tbSkills = new TextButton(LANG.format("upgrades_navigation_to_skills"), skin);

        tbSkins.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                setPage(SITE_SKINS);

            }
        });

        tbStats.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                setPage(SITE_STATS);
            }
        });

        tbSkills.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                setPage(SITE_SKILLS);
            }
        });

        buttonTable.add(tbSkins).expandY().size(B_WID / 2, B_HEI).row();
        buttonTable.add(tbStats).expandY().size(B_WID / 2, B_HEI).row();
        buttonTable.add(tbSkills).expandY().size(B_WID / 2, B_HEI).row();
        checkButtonVisibility();
    }

    public void showDialog() {
        Gdx.input.setInputProcessor(stage);
        if(!tutorialMode) {
            stage.getRoot().setPosition(0, stage.getViewport().getScreenHeight());
            stage.addAction(Actions.sequence(Actions.alpha(1),Actions.moveBy(0, -stage.getViewport().getScreenHeight(), 0.5f, Interpolation.bounceOut)));
        } else {
            stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
        }
    }

    public void finish() {
        if(!tutorialMode) {
            stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.5f, Interpolation.swing), Actions.run(new Runnable() {
                @Override
                public void run() {
                    Gdx.input.setInputProcessor(oldStage);
                    motherTable.remove();
                }
            })));
        } else {
            stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    Gdx.input.setInputProcessor(oldStage);
                    motherTable.remove();
                    if(closeListener != null){
                        closeListener.actionPerformed();
                    }
                }
            })));
        }
    }

    public void dispose() {
        stage.dispose();
        if(tutorialMode){
            introductionHook.dispose();
        }
    }



    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
//        stage.getViewport().getCamera().position.set(width / 2, height / 2, 0);
//        stage.getViewport().getCamera().update();
        if(tutorialMode){
            introductionHook.resize(width, height);
        }
    }

    public void act(float delta) {
        if(tutorialMode)
        introductionHook.renderBeforeRegularStage();
        stage.act();
        stage.draw();
        if(tutorialMode)
        introductionHook.renderAfterRegularStage();
    }

    public void draw() {

    }

    public UpgradeSiteStats getStatsMenu() {
        return (UpgradeSiteStats) usStats;
    }

    public UpgradeSiteSkills getSkillsMenu() {
        return (UpgradeSiteSkills) usSkills;
    }

    public interface Listener{
        void updateAll();
        void playBuySound();
        void playEquipSound();
    }

    /**
     * implement to hook into renderMethod
     */
    public interface IntroductionHook {
        void renderBeforeRegularStage();
        void renderAfterRegularStage();
        void resize(int width, int height);
        void dispose();
        int getWhichSite();
        void setDialog(UpgradeMainDialog upgradeMainDialog);
    }


    /**
     * Sets the buttonstyle as SELECTED / ENABLED
     * if the button is not selected, he should be enabled by default. (normal looking button)
     * @param tb which button to set the style
     * @param selected if the button is selected or not
     */
    public static void setButtonSelection(TextButton tb, boolean selected){
        if(selected){
            tb.setStyle(ERQAssets.TB_SELECTED);
        } else {
            tb.setStyle(ERQAssets.TB_ENABLED);
        }
    }

    private void unselectAll(){
        if(tbSkills.isTouchable()){
            tbSkills.setStyle(defSkin);
        }
        if(tbStats.isTouchable()){
            tbStats.setStyle(defSkin);
        }
        if(tbSkins.isTouchable()){
            tbSkins.setStyle(defSkin);
        }
    }


    public Gameoverview.ActionListener getCloseListener() {
        return closeListener;
    }

    public void setCloseListener(Gameoverview.ActionListener closeListener) {
        this.closeListener = closeListener;
    }

    private void playBuySound(){
        if(buySound == null) {
            buySound = ERQAssets.MANAGER.get(AssetVariables.SOUND_BUY, Sound.class);
        }
        buySound.play(MusicHandler.getSoundVolume());
    }

    private void playEquipSound(){
        if(equipSound == null){
            equipSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_EQUIP, Sound.class);
        }
        equipSound.play(MusicHandler.getSoundVolume());
    }
}
