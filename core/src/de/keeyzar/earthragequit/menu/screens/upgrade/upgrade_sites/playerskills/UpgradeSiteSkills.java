package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.menu.screens.elements.SkillUsed;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeSite;
import de.keeyzar.earthragequit.tutorial.tools.HighlightableImageButton;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 10.05.2016.
 */
public class UpgradeSiteSkills implements UpgradeSite {
    private ERQGame game;
    private Stage stage;
    private final SkillSiteListener skl;
    private UpgradeMainDialog.Listener listener;
    private Skin skin;
    private Table motherTable;



    //actualSkills
    private HighlightableImageButton ibAct_1;
    private ImageButton ibAct_2;
    private ImageButton ibAct_3;
    private ImageButton ibAct_4;
    private SkillsDialog skillsDialog;

    /**
     * initializes a table with the given attributes.
     * @param erqGame the game
     * @param listener to call update may be null
     * @param stage the stage, on which dialogs are opened
     * @param skl a callback for other elements. may be null
     */
    public UpgradeSiteSkills(ERQGame erqGame, Stage stage, UpgradeMainDialog.Listener listener, SkillSiteListener skl){
        game = erqGame;
        this.listener = listener;
        this.stage = stage;
        this.skl = skl;
        skin = ERQAssets.SKIN;
        initStuff();
    }

    private void initStuff() {
        motherTable = new SparklingTable(skin);
        initButtons();
        int bWid = 225;
        int bHei = 225;
        Table table1 = new Table();
        Table table2 = new Table();
        Table table3 = new Table();
        Table table4 = new Table();

        table1.add(ibAct_1).size(bWid, bHei).padBottom(ScreenVariables.PAD_TB_S).row();
        table1.add(new Label(LANG.format("upgrades_skills_text_1"), skin));

        table2.add(ibAct_2).size(bWid, bHei).padBottom(ScreenVariables.PAD_TB_S).row();
        table2.add(new Label(LANG.format("upgrades_skills_text_2"), skin));

        table3.add(ibAct_3).size(bWid, bHei).padBottom(ScreenVariables.PAD_TB_S).row();
        table3.add(new Label(LANG.format("upgrades_skills_text_3"), skin));

        table4.add(ibAct_4).size(bWid, bHei).padBottom(ScreenVariables.PAD_TB_S).row();
        table4.add(new Label(LANG.format("upgrades_skills_text_4"), skin));


        int sizePlus = 25;
        motherTable.add(table1).size(bWid, bHei + sizePlus).pad(ScreenVariables.PAD_TB_S).expand();
        motherTable.add(table2).size(bWid, bHei + sizePlus).pad(ScreenVariables.PAD_TB_S).expand().row();
        motherTable.add(table3).size(bWid, bHei + sizePlus).pad(ScreenVariables.PAD_TB_S).expand();
        motherTable.add(table4).size(bWid, bHei + sizePlus).pad(ScreenVariables.PAD_TB_S).expand();
    }

    private void initButtons() {
        SkillsVerwalter skillsVerwalter = game.getSkillsVerwalter();
        skillsDialog = new SkillsDialog(stage, game, new SkillUsed() {
            @Override
            public void newSkillChosen() {
                actualiseButton();
                listener.playEquipSound();
            }

            @Override
            public void closed() {

            }

            @Override
            public void skillBought() {
                listener.updateAll();
                listener.playBuySound();
            }
        }, skl);
        ibAct_1 = new HighlightableImageButton(skin);
        if(!skillsVerwalter.lockedStatus.get(1)){
            ibAct_1.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    skillsDialog.createDialog(1);

                }
            });
        }
        ibAct_2 = new ImageButton(skin);
        if(!skillsVerwalter.lockedStatus.get(2)){
            ibAct_2.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    if(skl != null){
                        skl.openedDialog();
                    }
                    skillsDialog.createDialog(2);

                }
            });
        }
        ibAct_3 = new ImageButton(skin);
        if(!skillsVerwalter.lockedStatus.get(3)){
            ibAct_3.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    if(skl != null){
                        skl.openedDialog();
                    }
                    skillsDialog.createDialog(3);

                }
            });
        }
        ibAct_4 = new ImageButton(skin);
        if(!skillsVerwalter.lockedStatus.get(4)){
            ibAct_4.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    if(skl != null) {
                        skl.openedDialog();
                    }
                    skillsDialog.createDialog(4);

                }
            });
        }

        ibAct_1.getImageCell().pad(ScreenVariables.PAD_TB_S*2);
        ibAct_2.getImageCell().pad(ScreenVariables.PAD_TB_S*2);
        ibAct_3.getImageCell().pad(ScreenVariables.PAD_TB_S*2);
        ibAct_4.getImageCell().pad(ScreenVariables.PAD_TB_S*2);
        actualiseButton();
    }

    public boolean openDialogIfEmptySkill(){
        final int freePlace = game.getSkillsVerwalter().getFreePlace();
        if(freePlace != -1) {
            skillsDialog.createDialog(freePlace);
            return true;
        }
        return false;
    }

    /**
     * actualizes all skins for the buttons
     */
    private void actualiseButton() {
        SkillsVerwalter skillsVerwalter = game.getSkillsVerwalter();
        ImageButton.ImageButtonStyle ibStyle_1 = new ImageButton.ImageButtonStyle(ibAct_1.getStyle());
        ibStyle_1.imageUp = skillsVerwalter.getSpriteDrawableFromActSkill(1);
        ibAct_1.setStyle(ibStyle_1);
        ImageButton.ImageButtonStyle ibStyle_2 = new ImageButton.ImageButtonStyle(ibAct_2.getStyle());
        ibStyle_2.imageUp = skillsVerwalter.getSpriteDrawableFromActSkill(2);
        ibAct_2.setStyle(ibStyle_2);
        ImageButton.ImageButtonStyle ibStyle_3 = new ImageButton.ImageButtonStyle(ibAct_3.getStyle());
        ibStyle_3.imageUp = skillsVerwalter.getSpriteDrawableFromActSkill(3);
        ibAct_3.setStyle(ibStyle_3);
        ImageButton.ImageButtonStyle ibStyle_4 = new ImageButton.ImageButtonStyle(ibAct_4.getStyle());
        ibStyle_4.imageUp = skillsVerwalter.getSpriteDrawableFromActSkill(4);
        ibAct_4.setStyle(ibStyle_4);
    }

    public HighlightableImageButton getFirstSkillButton(){
        return ibAct_1;
    }


    @Override
    public void setSite(Table siteContainer, Label title) {
        title.setText(LANG.format("upgrades_skills_title"));
        siteContainer.add(motherTable).grow();
    }

    @Override
    public void updateAll() {
        //do nothing special
    }

    public void startHighlightProcedure() {
        if(skillsDialog != null){
            skillsDialog.highlightProcedure();
        }
    }
}
