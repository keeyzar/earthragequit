package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.GameOverviewContentFillClass;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.ERQStats;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.tutorial.skills.SkillsTutorial;
import de.keeyzar.earthragequit.tutorial.upgrades.UpgradeTutorial;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 20.04.2016
 */
public class Gameoverview {
    private final ERQGame game;
    private Transitionable actualScreen;
    private Transitionable bossLevel = null;
    private Array<PlayerHasUnlocked> unlocks;
    private Player player;

    //hint, that player can upgrade, should be showed only four times
    private final String COUNTER_SHOW_UPGRADE = "counterShowUpgrade";
    private final int counterShowUpgrade;

    private boolean wonLevel;


    private Skin skin;
    private Stage stage;
    private Table motherTable, titleTable, contentTable, navigationTable;


    private TextButton tbRetry, tbNext, tbMenu, tbUpgrades; //navigation through this level

    private UpgradeMainDialog upgradeMainDialog;
    //optical helperclass
    private GameOverviewContentFillClass gameOverviewContentFillClass;
    private ScrollPane contentScrollPane;


    public Gameoverview(ERQGame game, Player player, Transitionable actualScreen, Array<PlayerHasUnlocked> unlocks) {
        this.game = game;
        this.player = player;
        this.actualScreen = actualScreen;

        gameOverviewContentFillClass = new GameOverviewContentFillClass(unlocks, game, player.getPlayerStatistic());

        this.unlocks = unlocks;
        this.skin = ERQAssets.SKIN;

        counterShowUpgrade = game.getGlobalPlayerInformation().loadOnOwn(Gameoverview.class, COUNTER_SHOW_UPGRADE, 0);

        initRef();
    }

    private void initRef(){
        float _width = 800;
        float _height = HudVars.HUD_HEIGHT;

        stage = new Stage(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT));

        //init OverallTable
        motherTable = new SparklingTable(skin);
        motherTable.setBackground(new ScrollPane(null, skin).getStyle().background);

        motherTable.setSize(_width, _height);
        motherTable.setPosition((HudVars.HUD_WIDTH - _width) / 2, (HudVars.HUD_HEIGHT - _height) / 2);

        titleTable = new Table();

        //init contentScrollpane & table
        contentTable = new SparklingTable(skin);
        contentScrollPane = new ScrollPane(contentTable, skin, "def");
        contentScrollPane.setOverscroll(false, false);

        //init navigationTable
        initNavigationTable();

        //add stuff to table
        motherTable.add(titleTable).pad(PAD_TB_S).growX().row();
        motherTable.add(contentScrollPane).pad(PAD_TB_S * 2).padTop(0).grow().row();
        motherTable.add(navigationTable).pad(PAD_TB_S * 2).growX().padTop(0).row();

        //add table to stage

        stage.addActor(motherTable);
    }

    private void initNavigationTable() {
        tbRetry = new TextButton(LANG.format("game_overview_retry"), skin);
        tbNext = new TextButton(LANG.format("game_overview_next_level"), skin);
        tbMenu = new TextButton(LANG.format("game_overview_menu"), skin);
        tbUpgrades = new TextButton(LANG.format("game_overview_upgrades"), skin);

        navigationTable = new Table(skin);

        navigationTable.add(tbMenu).size(B_WID, B_HEI).pad(PAD_TB_S).expandX().left();
        navigationTable.add(tbNext).size(B_WID, B_HEI).pad(PAD_TB_S).expandX().right().row();
        navigationTable.add(tbRetry).size(B_WID, B_HEI).pad(PAD_TB_S).expandX().left();
        navigationTable.add(tbUpgrades).size(B_WID, B_HEI).pad(PAD_TB_S).expandX().right();


        //add all listener
        tbRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                if (checkIfTutorialIsThere()) return;
                if(actualScreen instanceof GameScreen) {
                    game.setScreen(new TransitionScreen(game, actualScreen, new GameScreen(game, ((GameScreen) actualScreen).getLevelDefining())));
                }
            }
        });

        tbNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                if (checkIfTutorialIsThere()) return;
                startNextLevelRoutine();

            }
            private void startNextLevelRoutine() {
                boolean startMenu = false;
                boolean isBossLevel = false;
                int nextLevel = -1;
                if(actualScreen instanceof GameScreen){
                    nextLevel = ((GameScreen) actualScreen).getLevelDefining().getLevel();
                    if (bossLevel != null) {
                        isBossLevel = true;
                    } else {
                        nextLevel += 1;
                    }

                } else if(actualScreen instanceof BossScreen) {
                    nextLevel = ((BossScreen) actualScreen).getBossLevelDefining().getNextLevel();
                    if(nextLevel == -1){
                        startMenu = true;
                    }
                }

                if (startMenu) {
                    game.setScreen(new TransitionScreen(game, actualScreen, new MainMenuScreen(game)));
                } else {
                    game.getLevelVerwalter().instantiate(game, actualScreen, nextLevel, isBossLevel);
                }
            }
        });

        tbMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                if (checkIfTutorialIsThere()) return;
                game.setScreen(new TransitionScreen(game, actualScreen, new MainMenuScreen(game)));
            }
        });

        tbUpgrades.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                Gdx.input.setInputProcessor(null);
                upgradeMainDialog = new UpgradeMainDialog(game, stage);
                upgradeMainDialog.showDialog();
            }
        });
    }

    private boolean checkIfTutorialIsThere() {
        if(game.getTutorialVerwalter().shouldShowStory()){
            game.getTutorialVerwalter().startLevel(game, actualScreen);
            return true;
        }
        return false;
    }

    /**
     * @param wonLevel whether or not the player has finished the level(won)
     */
    public void startGameEND(boolean wonLevel) {
        modifyTitleTable(wonLevel);
        Gdx.input.setInputProcessor(stage);

        GameScreen gameScreen = null;
        this.wonLevel = wonLevel;

        //set Bosslevel
        if(actualScreen instanceof GameScreen) {
            gameScreen = (GameScreen) actualScreen;
            bossLevel = gameScreen.getLevelDefining().getBossLevel();
        }

        //Hint for player, that he should upgrade
        if(gameScreen != null && game.getTutorialVerwalter().isAbsolved(TVars.UPGRADE_INTRODUCTION) && counterShowUpgrade <= 3 && canUpgrade() && (bossLevel == null || !wonLevel)){
            showUpgradeInfo();
            game.getGlobalPlayerInformation().saveOnOwn(this.getClass(), COUNTER_SHOW_UPGRADE, counterShowUpgrade+1);
        }

        fadeInAction();
        colorButtons();
        gameOverviewContentFillClass.fillContentTable(contentTable, contentScrollPane, wonLevel, new GameOverviewContentFillClass.Listener(){
            @Override
            public void clicked(int whatType) {
                MenuUtils.playClickSound();
                Gdx.input.setInputProcessor(null);
                upgradeMainDialog = new UpgradeMainDialog(game, stage);
                upgradeMainDialog.setSiteToOpen(whatType);
                upgradeMainDialog.showDialog();
            }
        });

        /* when bossLevel != null disable other buttons);*/
        if(gameScreen != null && bossLevel != null){
            int levToCheck = gameScreen.getLevelDefining().getLevel() + 1;

            //if player has unlocked the next level, than he can retry this one etc.
            //if not, forbid it, and only allow bossfight
            //set default locked = true.
            if(game.getLevelVerwalter().getLevelLockMap().get(levToCheck, true) && wonLevel){
                MenuUtils.setEnabled(tbMenu, false);
                MenuUtils.setEnabled(tbRetry, false);
            }
        }

        if(actualScreen instanceof BossScreen){
            MenuUtils.setEnabled(tbRetry, false);
        }

        checkForUpgradeTutorial();
    }

    private void modifyTitleTable(boolean wonLevel) {
        titleTable.clear();
        Label label;
        if(wonLevel){
            label = new Label(LANG.format("game_overview_bossfight_title_won"), skin);
            if(actualScreen instanceof GameScreen){
                final String levelName = ((GameScreen) actualScreen).getLevelDefining().getLevelName();
                label.setText(LANG.format("game_overview_title_won", levelName.toUpperCase()));

            }
            label.setColor(Color.GREEN.cpy());
        } else {
            label = new Label("You lost", skin);
            label.setColor(Color.RED.cpy());

            if(actualScreen instanceof GameScreen){
                final String levelName = ((GameScreen) actualScreen).getLevelDefining().getLevelName();
                final int currLevelPercentage = ((GameScreen) actualScreen).getPlayer().getPlayerStatistic().getCurrLevelPercentage();
                label.setText(LANG.format("game_overview_title_lost", levelName.toUpperCase(), currLevelPercentage));
            }
        }
        titleTable.add(label);
    }


    private void fadeInAction() {
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }

    /**
     * shows an TextActor which signalizes that the user can upgrade and should
     * and additionally starts the tutorial, if is given
     */
    private void showUpgradeInfo() {
        stage.clear();

        Label label = new Label(LANG.format("game_overview_upgrade_reminder"), skin);
        Label.LabelStyle style = new Label.LabelStyle(label.getStyle());
        style.background = new ScrollPane(null, ERQAssets.SKIN).getStyle().background;
        label.layout();
        label.setStyle(style);
        label.setSize(label.getWidth() + 30, label.getHeight() + 30);
        /*hint the player, that he can upgrade (because of enough coins)*/
        Table tableCanUpgrade = new Table(skin);
        tableCanUpgrade.setFillParent(true);
        tableCanUpgrade.add(label);
        stage.addActor(tableCanUpgrade);
        motherTable.getColor().a = 0;

        //wait 3 sec fade label out, fade screen in, if tutorial, than show tutorial
        label.addAction(new SequenceAction(Actions.delay(1.5f), Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                stage.clear();
                stage.addActor(motherTable);
                motherTable.addAction(Actions.fadeIn(0.5f));
            }
        })));
    }


    /**
     * checks whetehr or not an tutorial is given for upgrades. and if yes, displays it immediately
     * that's recursive for every introduction, that should be displayed here.
     */
    private void checkForUpgradeTutorial() {
        UpgradeMainDialog.IntroductionHook introductionHook = null;
        if(game.getTutorialVerwalter().shouldBeAbsolved(TVars.UPGRADE_INTRODUCTION)){
            introductionHook = new UpgradeTutorial(game);

        } else if(game.getTutorialVerwalter().shouldBeAbsolved(TVars.SKILLS_INTRODUCTION)){
            introductionHook = new SkillsTutorial(game);
        }

        if(introductionHook != null){
            upgradeMainDialog = new UpgradeMainDialog(game, stage, introductionHook);
            upgradeMainDialog.setCloseListener(new ActionListener() {
                @Override
                public void actionPerformed() {
                    checkForUpgradeTutorial();
                }
            });
            upgradeMainDialog.showDialog();
        }
    }

    /** checks game stats and, when some stats are reached, than buttons are colored
     * example: enough coins to buy an upgrade -> upgrade gets Colored
     *
     */
    private void colorButtons() {
        if(game.getTutorialVerwalter().isAbsolved(TVars.UPGRADE_INTRODUCTION) && canUpgrade()){
            MenuUtils.setEnabled(tbUpgrades, true, true);
        } else if(!game.getTutorialVerwalter().isAbsolved(TVars.UPGRADE_INTRODUCTION)){
            MenuUtils.setEnabled(tbUpgrades, false);
        }

        if(actualScreen instanceof GameScreen) {
            if (wonLevel) {
                tbNext.setVisible(true);

                if (bossLevel != null) {
                    tbNext.setText(LANG.format("game_overview_bossfight"));
                }
                MenuUtils.setEnabled(tbNext, true, true);
            } else {
                //still allow next level, if the next level is already unlocked
                boolean nextLevelUnlocked = game.getLevelVerwalter().getLevelLockMap()
                        .get(((GameScreen) actualScreen).getLevelDefining().getLevel(), false);
                MenuUtils.setEnabled(tbNext, nextLevelUnlocked);
            }
        }
    }

    private boolean canUpgrade() {
        int coins = game.getCoins();
        boolean shouldColorUpgradeButton = false;
        Array<ERQStats> statsArray = game.getStatsVerwalter().getStatsArray();
        for(ERQStats stats : statsArray){
            if(!stats.isLocked() && !stats.isMaxLevel() && coins >= stats.getCostsForNextLevel()){
                shouldColorUpgradeButton = true;
                break;
            }
        }

        //if no stat is upgradable, check if a skill is upgradable
        if(!shouldColorUpgradeButton){
            Array<PlayerSkills> playerSkillsArray = game.getSkillsVerwalter().getPlayerSkillsArray();
            for(PlayerSkills playerSkills : playerSkillsArray){
                if(!playerSkills.isLocked() && !playerSkills.isMaxLevel() && coins >= playerSkills.getCostsForNextLevel()){
                    shouldColorUpgradeButton = true;
                    break;
                }
            }
        }
        return shouldColorUpgradeButton;
    }

    public void act(float delta){
        stage.act(delta);
        stage.draw();
        if(upgradeMainDialog != null){
            upgradeMainDialog.act(delta);
            upgradeMainDialog.draw();
        }
    }



    //NEEDED
    public void updateAll(int width, int height) {
        stage.getViewport().update(width, height);
        if (upgradeMainDialog != null) {
            upgradeMainDialog.resize(width, height);
        }
    }

    public void dispose(){
        stage.dispose();
    }

    /**
     * simple interface, to notify.
     */
    public interface ActionListener{
        void actionPerformed();
    }

    public boolean hasActions(){
        return stage.getRoot().hasActions();
    }

    public void addAction(Action action) {
        stage.getRoot().addAction(action);
    }

}
