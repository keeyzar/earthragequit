package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * A Dialog which fills the entire Screen
 * Created by Keeyzar on 27.04.2016.
 */
public class InfoDialogSkins {
    private String title;
    private Stage stage;

    public InfoDialogSkins(Stage stage, String title){
        this.stage = stage;
        this.title = title;
    }

    void createDialog(String text){
        Dialog dialog = new Dialog("", ERQAssets.SKIN);
        dialog.getTitleTable().reset(); //try to remove the padding on top
        addContent(dialog, text);
        dialog.setSize(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        dialog.setFillParent(true);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);
    }

    Table motherTable;
    private void addContent(final Dialog dialog, String text) {
        Skin skin = ERQAssets.SKIN;

        Table contentTable = dialog.getContentTable();
        contentTable.setSkin(skin);

        motherTable = new Table(skin);

        Label label = new Label(text, skin);
        label.setWrap(true);
        motherTable.add(label).pad(ScreenVariables.PAD_TB_S, ScreenVariables.PAD_TB_N, ScreenVariables.PAD_TB_N, ScreenVariables.PAD_TB_N).grow();

        contentTable.add(new Label(title, skin)).pad(ScreenVariables.PAD_TB_S).row();
        contentTable.add(motherTable).grow().row();

        contentTable.add("").grow().row();


        TextButton tb = new TextButton("close", skin);
        tb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                dialog.hide();
            }
        });

        contentTable.add(tb).height(ScreenVariables.B_HEI).pad(ScreenVariables.PAD_TB_S).growX();

    }
}
