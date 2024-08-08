package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.custom_ui.planets.PlanetSkillsDialog;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.CoinAnimActor;
import de.keeyzar.earthragequit.menu.screens.elements.DialogAnswer;
import de.keeyzar.earthragequit.menu.screens.elements.ERQDialog;
import de.keeyzar.earthragequit.menu.screens.elements.SkillUsed;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.Def_PlayerSkill;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;
import de.keeyzar.earthragequit.tutorial.tools.HighlightableButton;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * The content for the SkillsDialog
 * Created by Keeyzar on 10.05.2016.
 */
public class SkillsDialogContent {
    private final ERQGame game;
    private SkillUsed skillUsed;
    private SkillSiteListener skl;
    private Skin skin;
    private ERQDialog erqDialog;


    //content
    private Label lCoins;
    private Table tSkills, tSkillsInfo;
    private ScrollPane spSkills, spSkillsInfo;
    private HighlightableButton tbUse, tbUpgrade;
    private TextButton tbInfo;

    private int place;
    private String currentItem;
    private Dialog dialog;
    private String defaultSkill = SkillsVars.MAGNET;
    private int coinsFromLastUpdate;

    //info
    SkillsInfoDialog skillsInfoDialog;
    private SkillsDialogContent.BuyListener buyListener;

    SkillsDialogContent(ERQGame game, final SkillUsed skillUsed, SkillSiteListener skillSiteListener, Stage stage){
        this.game = game;
        this.skillUsed = skillUsed;
        this.skl = skillSiteListener;
        this.skin = ERQAssets.SKIN;
        erqDialog = new ERQDialog(stage, LANG.format("upgrade_dialog_button_buy_upgrade_dialog_text"), LANG.format("upgrade_dialog_button_buy_upgrade_dialog_text_yes"), LANG.format("upgrade_dialog_button_buy_upgrade_dialog_text_no"), new DialogAnswer() {
            @Override
            public void confirmed() {
                bought();
                skillUsed.skillBought();
            }
        });

        skillsInfoDialog = new SkillsInfoDialog(stage);
    }


    void createContent(final Dialog dialog, int place) {
        this.dialog = dialog;
        this.place = place;
        if(game.getSkillsVerwalter().getAct_PlayerskillsMap().get(place) != null) {
            defaultSkill = game.getSkillsVerwalter().getAct_PlayerskillsMap().get(place).getIdentifier_name();
        } else {
            getDefaultSkill();
        }
        currentItem = defaultSkill;


        //add Header
        Table motherTable = dialog.getContentTable();
        motherTable.addActor(new PlanetSkillsDialog());

        Label titelLabel = new Label(LANG.format("upgrade_dialog_title", place), skin);
        lCoins = new Label("", skin){
            private int counter = 0;
            @Override
            public void act(float delta) {
                counter++;
                if(counter > 5){
                    counter = 0;
                    if(game.getCoins() != coinsFromLastUpdate){
                        updateStuff(currentItem);
                    }
                }
                super.act(delta);
            }
        };


//        motherTable.add(tbMenu).padTop(PAD_TB_S).padBottom(PAD_TB_S).padLeft(PAD_LR_S).size(B_WID / 2, B_HEI);
//        motherTable.add(titelLabel).padTop(PAD_TB_S).padBottom(PAD_TB_S).expandX().padRight(PAD_LR_S);
//        motherTable.add(lCoins).padTop(PAD_TB_S).padBottom(PAD_TB_S).padRight(PAD_LR_S).expandX().row();

        Table titleTable = new Table(skin);
        titleTable.add("").padTop(PAD_TB_S).padBottom(PAD_TB_S).padLeft(PAD_LR_S).size(B_WID / 2, B_HEI);
        titleTable.add(titelLabel).minWidth(300).padTop(PAD_TB_S).padBottom(PAD_TB_S).expandX().padRight(PAD_LR_S);
        titleTable.add(new CoinAnimActor()).size(50, 50).padRight(PAD_TB_S).right().expandX();
        titleTable.add(lCoins).padTop(PAD_TB_S).padBottom(PAD_TB_S).padRight(PAD_LR_S).left().expandX();

        motherTable.add(titleTable).colspan(3).width(ScreenVariables.SCREEN_WIDTH).row();

        //add Content
        Table tempTable = new SparklingTable(skin);
        motherTable.add(tempTable).colspan(3).pad(PAD_TB_N / 2).grow().row();

        initializeStuff();
        tempTable.add(spSkills).pad(PAD_TB_S).width(B_WID * 1.5f).growY();
        tempTable.add(spSkillsInfo).pad(PAD_TB_S).grow().row();

        Table bTable = new Table(skin);
        tempTable.add(bTable).colspan(3).growX();

        bTable.add(tbInfo).pad(PAD_TB_S).left().size(B_WID, B_HEI).expandX();
        bTable.add(tbUse).pad(PAD_TB_S).size(B_WID, B_HEI).expandX();
        bTable.add(tbUpgrade).pad(PAD_TB_S).right().size(B_WID, B_HEI).expandX();




        //add bottom
        TextButton tb = new TextButton(LANG.format("upgrade_dialog_button_do_not_use_new_skill"), skin);
        if(skl != null){
            MenuUtils.setEnabled(tb, false);
        }
        tb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                dialog.hide(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        if(skl != null) {
                            skl.windowClosed();
                        }
                        skillUsed.closed();
                    }
                })));
            }
        });

        motherTable.add(tb).colspan(3).height(B_HEI).pad(PAD_TB_S).growX();

        updateStuff(defaultSkill);
    }

    private void getDefaultSkill() {
        Array<PlayerSkills> playerSkillsArray = game.getSkillsVerwalter().getPlayerSkillsArray();
        for(PlayerSkills playerSkills : playerSkillsArray){
            if(!playerSkills.isLocked()){
                defaultSkill = playerSkills.getIdentifier_name();
                break;
            }
        }
    }

    private void initializeStuff() {
        tSkills = new SparklingTable(skin);
        tSkillsInfo = new SparklingTable(skin);
        spSkills = new ScrollPane(tSkills, skin, "def");
        spSkillsInfo = new ScrollPane(tSkillsInfo, skin, "def");
        fillTables();

        tbInfo = new TextButton(LANG.format("upgrade_dialog_button_info"), skin);
        tbUse = new HighlightableButton(LANG.format("upgrade_dialog_button_place_in_rocket"), skin);
        tbUpgrade = new HighlightableButton(LANG.format("upgrade_dialog_button_buy_upgrade"), skin);

        tbInfo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                openInfo();
            }
        });

        tbUse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                useSkill(dialog);
            }
        });

        tbUpgrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                erqDialog.createDialog();
            }
        });
    }

    private void openInfo() {
        PlayerSkills playerSkill = game.getSkillsVerwalter().getSkillByName(currentItem);
        if (playerSkill == null) {
            playerSkill = game.getSkillsVerwalter().getSkillByName(defaultSkill);
        }

        skillsInfoDialog.createDialog(playerSkill.getSkillDescribingText(), playerSkill.getDisplayName());
    }

    /**
     * fill skillstable
     */
    private void fillTables() {
        Array<PlayerSkills> playerSkillsArray = game.getSkillsVerwalter().getPlayerSkillsArray();
        for(PlayerSkills playerSkills : playerSkillsArray){
            if(!playerSkills.isLocked()) {
                TextButton tb = new TextButton(playerSkills.getDisplayName(), skin);
                final String name = playerSkills.getIdentifier_name();
                tb.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MenuUtils.playClickSound();
                        currentItem = name;
                        updateStuff(name);
                    }


                });
                tSkills.add(tb).growX().height(B_HEI).pad(PAD_TB_S).row();
            }
        }
    }

    private void updateStuff(String name) {
        SkillsVerwalter skillsVerwalter = game.getSkillsVerwalter();
        PlayerSkills playerSkills = skillsVerwalter.getSkillByName(name);

        if(playerSkills.getCurrentLevel()<11){
            Def_PlayerSkill.createSkillsInfos(playerSkills, game.getCoins(), tSkillsInfo);
        }
        updateButtons(playerSkills);
        updateCoinsLabel();

        //setSelection
        for(Cell cell : tSkills.getCells()){
            TextButton textButton = (TextButton) cell.getActor();
            UpgradeMainDialog.setButtonSelection(textButton, textButton.getText().toString().equals(playerSkills.getDisplayName()));
        }
    }

    private void updateButtons(PlayerSkills playerSkills) {
        MenuUtils.setEnabled(tbUpgrade, playerSkills.getCostsForNextLevel() <= game.getCoins() && !playerSkills.isMaxLevel());
        MenuUtils.setEnabled(tbUse, playerSkills.getCurrentLevel() > 0);
    }

    private void updateCoinsLabel() {
        coinsFromLastUpdate = game.getCoins();
        lCoins.setText(LANG.format("upgrades_actual_coins", coinsFromLastUpdate));
    }

    private void bought() {
        if(buyListener != null){
            buyListener.bought();
        }
        PlayerSkills skillByName = game.getSkillsVerwalter().getSkillByName(currentItem);
        game.getGlobalPlayerInformation().addCoins(-(skillByName.getCostsForNextLevel()));
        skillByName.setCurrentLevel(skillByName.getCurrentLevel() + 1);
        updateCoinsLabel();
        updateStuff(currentItem);
        game.save();
    }


    private void useSkill(Dialog dialog) {
        game.getSkillsVerwalter().setPlayerSkills(place, currentItem);
        game.save();
        dialog.hide(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.run(new Runnable() {
            @Override
            public void run() {
                if(skl != null){
                    skl.newSkillChosen();
                }
            }
        })));
        skillUsed.newSkillChosen();
    }

    void highlightProcedure() {
        tbUpgrade.enableHighlight(true);
        buyListener = new BuyListener() {
            @Override
            public void bought() {
                tbUpgrade.enableHighlight(false);
                tbUse.enableHighlight(true);
            }
        };
    }

    private interface BuyListener{
        void bought();
    }
}
