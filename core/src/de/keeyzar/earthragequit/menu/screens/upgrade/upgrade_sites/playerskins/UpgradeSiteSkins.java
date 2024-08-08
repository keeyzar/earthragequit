package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.custom_ui.SparklingTable;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils.CoinAnimActor;
import de.keeyzar.earthragequit.menu.screens.elements.DialogAnswer;
import de.keeyzar.earthragequit.menu.screens.elements.ERQDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeSite;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins.ShipSkin;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.B_HEI;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.PAD_TB_S;

/**
 * UpgradeScreen for skins
 * Created by Keeyzar on 27.04.2016.
 */
public class UpgradeSiteSkins implements UpgradeSite {
    private ERQGame game;
    private Stage stage;
    private UpgradeMainDialog.Listener listener;
    private Skin skin;
    private Table motherTable;

    private Table tSkins;
    private ScrollPane spSkins;

    private Table tableImage;
    private Label labelCosts;
    private Image biggerSkinImage;

    private ShipSkin currentlyWatchingSkin;
    private ShipSkin currentlyInUseShipSkin;

    private ERQDialog erqDialog;

    private TextButton tbInfo, tbUseSkin, tbBuySkin;
    private InfoDialogSkins infoDialogSkins;

    private ImageButton.ImageButtonStyle defaultStyle;
    private ImageButton.ImageButtonStyle selectedStyle;

    public UpgradeSiteSkins(ERQGame game, Stage stage, UpgradeMainDialog.Listener listener){
        this.game = game;
        this.stage = stage;
        this.listener = listener;
        skin = ERQAssets.SKIN;
        initStuff();
    }

    private void initStuff() {
        defaultStyle = new ImageButton(skin, "skins_norm").getStyle();
        selectedStyle = new ImageButton(skin, "skins_selected").getStyle();

        erqDialog = new ERQDialog(stage, LANG.format("upgrades_skins_button_buy_skin_dialog_text"), LANG.format("upgrades_skins_button_buy_skin_dialog_text_yes"), LANG.format("upgrades_skins_button_buy_skin_dialog_text_no"), null);
        infoDialogSkins = new InfoDialogSkins(stage, LANG.format("upgrades_skins_info_dialog_title"));


        currentlyInUseShipSkin = game.getSkinVerwalter().getActualSkin();

        motherTable = new Table(skin);

        tSkins = new SparklingTable(skin);
        spSkins = new ScrollPane(tSkins, skin);
        spSkins.setScrollingDisabled(true, false);
        Table skinListTable = new Table();
        fillSkinListTable(skinListTable);
        fillTableImageWithContent();

//        motherTable.add(tableImage).growY().pad(PAD_TB_S);
        motherTable.add(tableImage).growY().width(300).pad(PAD_TB_S);
        motherTable.add(skinListTable).grow().pad(PAD_TB_S).row();

        Table buttonTempTable = new Table();
        fillButtonTableWithContent(buttonTempTable);

        motherTable.add(buttonTempTable).colspan(2).height(100).growX().pad(PAD_TB_S);
    }

    /**
     * fills the table, which is passed as a param, with the three Buttons info, use skin and buy skin
     * @param buttonTempTable in which the Buttons should be created
     */
    private void fillButtonTableWithContent(Table buttonTempTable) {
        tbInfo = new TextButton(LANG.format("upgrades_skins_button_info"), skin);
        tbInfo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                showInfo();
            }
        });

        tbUseSkin = new TextButton(LANG.format("upgrades_skins_button_use_skin"), skin);
        tbUseSkin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                useSkin();
            }
        });

        tbBuySkin = new TextButton(LANG.format("upgrades_skins_button_buy_skin"), skin);
        tbBuySkin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buySkin();
            }
        });

        buttonTempTable.add(tbInfo).pad(PAD_TB_S).left().growX().height(B_HEI);
        buttonTempTable.add(tbUseSkin).pad(PAD_TB_S).growX().height(B_HEI);
        buttonTempTable.add(tbBuySkin).pad(PAD_TB_S).right().growX().height(B_HEI);

        updateButtonsAndText();

    }

    private void updateButtonsAndText() {
        ShipSkin toCheck;
        if (currentlyWatchingSkin != null) {
            toCheck = currentlyWatchingSkin;
        } else {
            toCheck = currentlyInUseShipSkin;
        }
        int costs = toCheck.getSkinCosts();
        labelCosts.setText("" + costs);
        MenuUtils.colorLabelDependingOnCosts(costs, game.getCoins(), labelCosts);

        if(toCheck.isBought()){
            MenuUtils.setEnabled(tbUseSkin, true);
            MenuUtils.setEnabled(tbBuySkin, false);
        } else {
            MenuUtils.setEnabled(tbUseSkin, false);
            MenuUtils.setEnabled(tbBuySkin, toCheck.getSkinCosts() <= game.getCoins());
        }

    }


    /**
     * buy skin was pressed
     */
    private void buySkin() {
        erqDialog.setDialogAnswerListener(new DialogAnswer() {
            @Override
            public void confirmed() {
                game.addCoins(-currentlyWatchingSkin.getSkinCosts());
                currentlyWatchingSkin.setBought(true);
                game.save();
                listener.playBuySound();
                listener.updateAll();
            }
        });
        erqDialog.createDialog();
    }

    /**
     * use skin was pressed
     */
    private void useSkin() {
        if (currentlyWatchingSkin == null) {
            currentlyWatchingSkin = currentlyInUseShipSkin;
        }
        currentlyInUseShipSkin = currentlyWatchingSkin;
        game.getSkinVerwalter().setActualSkin(currentlyWatchingSkin.getSkinId());
        listener.updateAll();
        listener.playEquipSound();
        checkForActualSkin();
    }

    /**
     * show Info was pressed
     */
    private void showInfo() {
        String x = LANG.format("upgrades_skins_info_dialog_text");
        infoDialogSkins.createDialog(x);
    }


    /**
     * Fills the tableImage with content.
     */
    private void fillTableImageWithContent() {
        tableImage = new Table(skin);
        labelCosts = new Label("", skin);
        biggerSkinImage = new Image();
        setShipViewImage();

        tableImage.add(LANG.format("upgrades_skins_costs")).left();
        tableImage.add(new CoinAnimActor()).pad(PAD_TB_S).size(30, 30);
        tableImage.add(labelCosts).pad(PAD_TB_S).padLeft(0).left().expandX().row();
        tableImage.add(biggerSkinImage).colspan(3).pad(PAD_TB_S).grow();
    }

    /**
     * fill skin list table with content
     * @param skinListTable the table which should be filled
     */
    private void fillSkinListTable(Table skinListTable) {
        skinListTable.add(new Label(LANG.format("upgrades_skins_description"), skin)).pad(PAD_TB_S).row();
        skinListTable.add(spSkins).pad(PAD_TB_S).grow();
        addAllSkinsToSkinTable();
    }

    private void addAllSkinsToSkinTable() {
        SkinVerwalter skinVerwalter = game.getSkinVerwalter();
        ShipSkin actualSkin = skinVerwalter.getActualSkin();
        float s_Wid = 100, s_Hei = 175;

        Array<ShipSkin> skinArray = skinVerwalter.getSkinArray();
        int counter = 1;
        for (final ShipSkin shipSkin : skinArray) {
            if(!shipSkin.isUnlocked()) continue;
            String which_skin;
            if(shipSkin.getSkinId() == actualSkin.getSkinId()){
                which_skin = "skins_selected";
            } else {
                which_skin = "skins_norm";
            }

            final ImageButton imageButton = new ImageButton(skin, which_skin);

            ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle(imageButton.getStyle());
            imageButtonStyle.imageUp = skinVerwalter.getSkinImage(shipSkin);
            imageButtonStyle.imageDown = imageButtonStyle.imageUp;
            imageButton.setStyle(imageButtonStyle);
            imageButton.setUserObject(shipSkin.getSkinId());


            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuUtils.playClickSound();
                    viewSkin(shipSkin);
                }
            });

            Cell<ImageButton> cell = tSkins.add(imageButton).size(s_Wid * 1.5f, s_Hei).expandX().pad(PAD_TB_S);
            if (counter > 1 && counter % 5 == 0) {
                cell.row();
            }
            counter++;
        }
        checkForActualSkin();
    }

    /**
     * actualizes the actual skin (which needs another layout)
     */
    private void checkForActualSkin() {
        Array<Cell> cells = tSkins.getCells();
        for(Cell cell : cells){
            Actor actor = cell.getActor();
            if(actor instanceof ImageButton){
                Drawable imageUp = ((ImageButton) actor).getStyle().imageUp;
                if(currentlyInUseShipSkin.getSkinId() == (Integer) actor.getUserObject()){
                    ((ImageButton) actor).setStyle(new ImageButton.ImageButtonStyle(selectedStyle));
                } else {
                    ((ImageButton) actor).setStyle(new ImageButton.ImageButtonStyle(defaultStyle));
                }
                ((ImageButton) actor).getStyle().imageUp = imageUp;
                ((ImageButton) actor).getStyle().imageDown = imageUp;
            }
        }
    }

    /**
     * player had pressed a skin
     */
    private void viewSkin(ShipSkin shipSkin) {
        currentlyWatchingSkin = shipSkin;
        setShipViewImage();
        updateButtonsAndText();
    }

    private void setShipViewImage() {
        if (currentlyWatchingSkin != null) {
            biggerSkinImage.setDrawable(game.getSkinVerwalter().getSkinImage(currentlyWatchingSkin));
        } else {
            biggerSkinImage.setDrawable(game.getSkinVerwalter().getSkinImage(currentlyInUseShipSkin));
        }
    }

    @Override
    public void setSite(Table siteContainer, Label title) {
        title.setText(LANG.format("upgrades_skins_title"));
        siteContainer.add(motherTable).grow();
    }

    @Override
    public void updateAll() {
        updateButtonsAndText();
    }
}
