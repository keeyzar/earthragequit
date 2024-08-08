package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementVerwalter;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.menu.screens.elements.DialogAnswer;
import de.keeyzar.earthragequit.menu.screens.elements.ERQDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeSite;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.ERQStats;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;
import de.keeyzar.earthragequit.tutorial.tools.HighlightableButton;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 28.04.2016.
 */
public class UpgradeSiteStats implements UpgradeSite {
    private ERQGame game;
    private Stage stage;
    private UpgradeMainDialog.Listener listener;
    InfoDialogStats infoDialogStats;
    ERQDialog erqDialog;

    private Skin skin;
    private Table motherTable;

    private TextButton tbInfo;
    private HighlightableButton tbUpgrade;
    private TextButton tbUpgradeForFree;
    private Table tStatInfos;
    private String currentStat = STAT_VARS.STAT_FUEL;
    private Table btTableInfoAndBuy;
    private Array<TextButton> allStats;
    private SparklingTable tAllStats;


    /**
     *
     */
    public UpgradeSiteStats(ERQGame game, Stage stage, UpgradeMainDialog.Listener listener) {
        this.game = game;
        this.stage = stage;
        this.listener = listener;
        infoDialogStats = new InfoDialogStats(stage);
        erqDialog = new ERQDialog(stage, LANG.format("upgrades_stats_buy_upgrad_dialog_text"), LANG.format("upgrades_stats_buy_upgrad_dialog_text_yes"), LANG.format("upgrades_stats_buy_upgrad_dialog_text_no"), null);
        allStats = new Array<TextButton>();
        arrangeItems();
    }

    private void arrangeItems() {
        skin = ERQAssets.SKIN;
        motherTable = new SparklingTable(skin);

        tAllStats = new SparklingTable(skin);
        ScrollPane spAllStats = new ScrollPane(tAllStats, skin, "def");
        fillTableWithAllStats(tAllStats);

        tStatInfos = new SparklingTable(skin);
        ScrollPane scrollPane = new ScrollPane(tStatInfos, skin, "def");
        scrollPane.setScrollingDisabled(true, true);

        tbInfo = new TextButton("?", skin);
        tbInfo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                final ERQStats statByName = game.getStatsVerwalter().getStatByName(currentStat);
                infoDialogStats.createDialog(statByName.getLongDescription(), statByName.getDisplayName());
            }
        });

        tbUpgrade = new HighlightableButton(LANG.format("upgrades_stats_buy_upgrade"), skin, "special");
        tbUpgrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                buyUpgrade();
            }
        });


        //create the buttontable, but add it elsewhere DO NOT ADD THE BUTTON TABLE
        btTableInfoAndBuy = new Table(skin);
        tbUpgradeForFree = new TextButton(LANG.format("upgrades_stats_button_free_upgrade"), skin);
        tbUpgradeForFree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                final int freeUpgrades = game.getGlobalPlayerInformation().getFreeUpgrades();

                final ERQDialog erqDialog = new ERQDialog(stage, LANG.format("upgrades_stats_free_upgrade_text", freeUpgrades), LANG.format("upgrades_stats_free_upgrade_text_yes"), LANG.format("upgrades_stats_free_upgrade_text_no"), null);
                erqDialog.setDialogAnswerListener(new DialogAnswer() {
                    @Override
                    public void confirmed() {
                        ERQStats statByName = game.getStatsVerwalter().getStatByName(currentStat);
                        game.getGlobalPlayerInformation().removeOneFreeUpgrade();
                        statByName.setCurrentLevel(statByName.getCurrentLevel() + 1);
                        listener.playBuySound();
                        game.save();
                        listener.updateAll();
                        updateInfosForStat(currentStat, false);
                        checkForAchievements();
                    }
                });
                erqDialog.createDialog();
            }
        });

        if(game.getGlobalPlayerInformation().getFreeUpgrades() > 0){
            btTableInfoAndBuy.add(tbUpgradeForFree).height(B_HEI).growX().pad(PAD_TB_S).colspan(2).row();
        }
        btTableInfoAndBuy.add(tbInfo).size(B_HEI, B_HEI).pad(PAD_TB_S);
        btTableInfoAndBuy.add(tbUpgrade).growX().right().height(B_HEI).pad(PAD_TB_S);

        motherTable.add(spAllStats).minWidth(B_WID * 1.5f).grow().pad(PAD_TB_S);
        motherTable.add(scrollPane).grow().pad(PAD_TB_S).row();
        updateInfosForStat(currentStat, true);

        //the following two lines are for the final selection, because updateInfosForStat unselects all buttons.
        TextButton firstTextButtonInTable = (TextButton) tAllStats.getCells().first().getActor();
        UpgradeMainDialog.setButtonSelection(firstTextButtonInTable, true);
    }

    /**
     * buy the upgrade
     */
    private void buyUpgrade() {
        erqDialog.setDialogAnswerListener(new DialogAnswer() {
            @Override
            public void confirmed() {
                ERQStats statByName = game.getStatsVerwalter().getStatByName(currentStat);
                game.addCoins(-game.getStatsVerwalter().getStatByName(currentStat).getCostsForNextLevel());
                statByName.setCurrentLevel(statByName.getCurrentLevel() + 1);
                listener.playBuySound();
                game.save();
                listener.updateAll();
                updateInfosForStat(currentStat, false);
                checkForAchievements();
            }
        });
        erqDialog.createDialog();
    }


    /**
     * checks for Achievements
     */
    private void checkForAchievements() {
        AchievementVerwalter achievementVerwalter = game.getAchievementVerwalter();
        ObjectMap.Entries<Integer, Achievement> achievementMap = achievementVerwalter.getAchievementMap().iterator();
        for(ObjectMap.Entry<Integer, Achievement> entry : achievementMap){
            if (entry.key >= 500 && entry.key < 1000){
                entry.value.checkConditionsAndApplyIfTrue();
            }
        }
    }

    private void fillTableWithAllStats(Table tAllStats) {
        tAllStats.clearChildren();
        Array<ERQStats> statsArray = game.getStatsVerwalter().getStatsArray();
        for (ERQStats stats : statsArray) {
            if (stats.isLocked()) continue;
            final TextButton textButton = new TextButton(stats.getDisplayName(), skin);
            final String name = stats.getIdentifierName();

            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    updateInfosForStat(name, true);
                    UpgradeMainDialog.setButtonSelection(textButton, true);
                }
            });

            tAllStats.add(textButton).growX().pad(PAD_TB_S * 2).height(B_HEI).row();
            allStats.add(textButton);
        }
        tAllStats.add("").grow();
    }

    private void updateInfosForStat(String name, boolean unselectAllButtons) {
        currentStat = name;
        ERQStats stats = game.getStatsVerwalter().getStatByName(name);
        tStatInfos.reset();
        tStatInfos.add(stats.addInfoText(game)).pad(PAD_TB_S * 2).grow().row();//set Short info
        tStatInfos.add(btTableInfoAndBuy).pad(PAD_TB_S * 2).growX();
        updateButtons(unselectAllButtons);
    }

    private void updateButtons(boolean shouldUnselectAllButtons) {
        ERQStats statByName = game.getStatsVerwalter().getStatByName(currentStat);
        MenuUtils.setEnabled(tbUpgrade, !statByName.isMaxLevel() && game.hasEnoughCoins(statByName.getCostsForNextLevel()), true);
        if(game.getGlobalPlayerInformation().getFreeUpgrades() <= 0){
            tbUpgradeForFree.remove();
        }
        MenuUtils.setEnabled(tbUpgradeForFree, !statByName.isMaxLevel());
        if(shouldUnselectAllButtons){
            unselectAll();
        }

    }

    private void unselectAll() {
        for (TextButton tb : allStats) {
            tb.setStyle(ERQAssets.TB_ENABLED);
        }
    }

    @Override
    public void setSite(Table siteContainer, Label title) {
        title.setText("Stats");
        fillTableWithAllStats(tAllStats);
        siteContainer.add(motherTable).grow();
    }

    /**
     * wrapper method, cause updateAll must be called with true within this class, but false from outer
     */
    private void updateAll(boolean shouldUnselectAllButtons){
        updateButtons(shouldUnselectAllButtons);
    }


    @Override
    public void updateAll() {
        updateAll(false);
        updateInfosForStat(currentStat, false);
    }

    public HighlightableButton getUpgradeButton() {
        return tbUpgrade;
    }
}
