package de.keeyzar.earthragequit.menu.screens.help;

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
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.help.sites.HELP_;
import de.keeyzar.earthragequit.menu.screens.help.sites.HELP_1;
import de.keeyzar.earthragequit.menu.screens.help.sites.HELP_2;
import de.keeyzar.earthragequit.menu.screens.help.sites.HELP_3;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 08.04.2016
 */
public class HelpDialog {
    private final Skin skin;
    private final Stage stage;
    private final ERQGame game;
    private Array<HELP_> allHelpSites;

    private final float maxWidth;
    private final float maxHeight;
    private TextButton tbPrev, tbNext;

    /**
     * Erstelle Dialog
     * @param stage
     * @param game
     */
    public HelpDialog(Stage stage, ERQGame game, Transitionable transitionable){
        this.game = game;
        this.stage = stage;
        this.skin = ERQAssets.SKIN;

        maxWidth = stage.getCamera().viewportWidth - stage.getCamera().viewportWidth/10;
        maxHeight = 500;


        allHelpSites = new Array<HELP_>();
        allHelpSites.add(new HELP_1(game, transitionable));
        allHelpSites.add(new HELP_2());
        allHelpSites.add(new HELP_3());
    }

    public void createDialog(){
        final Dialog dialog = new Dialog("", skin);


        createContent(dialog);


        TextButton tb = new TextButton(LANG.format("help_page_navigation_close"), skin);
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                dialog.hide(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade)));
            }
        });
        dialog.getButtonTable().add(tb).padTop(PAD_TB_N).size(SCREEN_WIDTH - PAD_TB_S * 4, B_HEI).pad(PAD_TB_S);
        dialog.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        dialog.setFillParent(true);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage, Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.4f, Interpolation.fade)));
        updateSiteLabel();
        checkButtons();
    }


    private Table motherTable;
    private Table siteContainer;
    private Label currSite;
    private Label maxSite;
    private int currentSite = 1;
    /**
     * creates dialogContent
     * @param dialog
     */
    private void createContent(Dialog dialog) {
        motherTable = new SparklingTable(ERQAssets.SKIN);
        Table contentTable = dialog.getContentTable();
        contentTable.add(motherTable);

//        sitecontainer contains all Sites.
        siteContainer = new Table();
        siteContainer.setSkin(skin);
        siteContainer.add(allHelpSites.get(0).getSite()).grow();


        motherTable.add(new Label(LANG.format("help_titel"), skin)).padBottom(PAD_TB_S).padTop(PAD_TB_N).row();
        motherTable.add(siteContainer).size(maxWidth, maxHeight).padBottom(PAD_TB_S).row();

//
        Table btTableTemp = new Table();
//
        tbPrev = new TextButton(LANG.format("help_page_navigation_previous"), skin);
        tbNext = new TextButton(LANG.format("help_page_navigation_next"), skin);
        tbPrev.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                changeSite(true);
            }
        });
        tbNext.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                changeSite(false);
            }
        });

        currSite = new Label("", skin);
        maxSite = new Label("", skin);


        btTableTemp.add(tbPrev).padRight(PAD_LR_N).size(B_WID, B_HEI);
        btTableTemp.add(currSite);
        btTableTemp.add(maxSite).padRight(PAD_LR_N);
        btTableTemp.add(tbNext).size(B_WID, B_HEI);
        motherTable.add(btTableTemp).padBottom(PAD_TB_N);
    }

    /**
     * updates the site label
     */
    private void updateSiteLabel() {
        currSite.setText(currentSite + "");
        maxSite.setText("/" + allHelpSites.size);
    }

    /**
     * changes Site by one Page
     */
    private void changeSite(boolean toPrev) {
        //implemented because of a bug. It would scroll 6 px to much, that looks ugly..
        if(toPrev){
            currentSite--;
        } else {
            currentSite++;
        }
        updateSiteLabel();
        checkButtons();
        siteContainer.reset();
        siteContainer.add(allHelpSites.get(currentSite - 1).getSite()).grow();
    }

    private void checkButtons() {
        MenuUtils.setEnabled(tbPrev, currentSite != 1);
        MenuUtils.setEnabled(tbNext, currentSite != allHelpSites.size);
    }


}
