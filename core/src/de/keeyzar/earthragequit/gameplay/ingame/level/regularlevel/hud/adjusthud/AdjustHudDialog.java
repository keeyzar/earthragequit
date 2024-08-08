package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components.PositionComponent;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components.SizeComponent;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components.TransparencyComponent;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class AdjustHudDialog {

    public static final int bWidth = 50;
    public static final int bHeight = 50;
    public static final int lWidth = 50;
    public static final int lHeight = 50;

    private final Skin skin;
    private final Stage stage;

    private HudActor hudActor;
    /**
     * Erstelle Dialog
     * @param stage
     */
    public AdjustHudDialog(Stage stage, HudActor hudActor){
        this.hudActor = hudActor;
        this.skin = ERQAssets.SKIN;
        this.stage = stage;

    }

    public void createDialog(String title){
        final float _beforeSaveX = hudActor.getX();
        final float _beforeSaveY = hudActor.getY();
        final float _beforeSafeWidth = hudActor.getWidth();
        final float _beforeSafeHeight = hudActor.getHeight();
        final Dialog dialog = new Dialog("", skin) {
            protected void result (Object object) {
                if((Integer)object == 1){
                    hudActor.safeXYWH();
                } else {
                    hudActor.setX(_beforeSaveX);
                    hudActor.setY(_beforeSaveY);
                    hudActor.setWidth(_beforeSafeWidth);
                    hudActor.setHeight(_beforeSafeHeight);
                }
            }

            @Override
            public float getPrefWidth() {
                return 500;
            }

            @Override
            public float getPrefHeight() {
                return 600;
            }
        };

        //dialog specific
        dialog.getTitleTable().remove();


        //buttopnspecific
        TextButton save = new TextButton(LANG.format("adjust_hud_save"), skin);
        dialog.button(save, 1);

        TextButton restoreToDefault = new TextButton(LANG.format("adjust_hud_undo"), skin);
        dialog.button(restoreToDefault, 2);

        dialog.getButtonTable().padTop(PAD_TB_S).padBottom(PAD_TB_S);
        Array<Cell> cells = dialog.getButtonTable().getCells();
        cells.get(0).size(200, B_HEI);
        cells.get(1).size(200, B_HEI);

        //add all stuff
        addTable(dialog, title);


        //now show it
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);

    }


    private void addTable(Dialog dialog, String title) {
        Table contentTable = dialog.getContentTable();
        contentTable.add(new Label(title, skin)).padBottom(PAD_TB_S).row();

        Table ownTable = new Table();

        ScrollPane spSteuerung = new ScrollPane(ownTable, skin, "def");
        spSteuerung.setScrollingDisabled(true, false);
        contentTable.add(spSteuerung).width(400).growY().pad(0);

        placeAllStuff(ownTable);
    }

    private void placeAllStuff(Table contentTable) {
        new SizeComponent().addChangeSizeComponent(contentTable, hudActor.isFixedSizeRatio(), -1, -1, hudActor);
        new PositionComponent().addChangePositionComponent(contentTable, hudActor);
        if(!hudActor.isFixedTransparency()){
            TransparencyComponent.addTransparencyComponent(hudActor, contentTable);
        }

        //add Restore
        TextButton tbRestore = new TextButton(LANG.format("adjust_hud_restore_to_default"), skin);
        tbRestore.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                hudActor.restoreToDefault();
            }
        });

        contentTable.add(new Label(LANG.format("adjust_hud_restore_to_default_title"), skin)).colspan(3).padTop(PAD_TB_N).padBottom(PAD_TB_S).row();
        contentTable.add(tbRestore).colspan(3).height(B_HEI).padBottom(PAD_TB_S).row();
    }
}
