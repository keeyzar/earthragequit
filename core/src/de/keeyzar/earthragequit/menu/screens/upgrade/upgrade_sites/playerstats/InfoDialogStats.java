package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * A Dialog which fills the entire Screen
 * Created by Keeyzar on 27.04.2016.
 */
public class InfoDialogStats {
    private Stage stage;

    public InfoDialogStats(Stage stage){
        this.stage = stage;
    }

    void createDialog(String text, String title){
        Dialog dialog = new Dialog("", ERQAssets.SKIN);
        addContent(dialog, text, title);
        dialog.setSize(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        dialog.setFillParent(true);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);
    }

    Table motherTable;
    private void addContent(final Dialog dialog, String text, String title) {
        Skin skin = ERQAssets.SKIN;

        Table contentTable = dialog.getContentTable();
        contentTable.setSkin(skin);

        motherTable = new Table(skin);

        Label label = new Label(text, skin);
        label.setWrap(true);
        motherTable.add(label).pad(ScreenVariables.PAD_TB_S, ScreenVariables.PAD_TB_N, ScreenVariables.PAD_TB_N, ScreenVariables.PAD_TB_N).grow();

        contentTable.add(new Label(LANG.format("upgrades_stats_info_dialog_title", title), skin)).pad(ScreenVariables.PAD_TB_S).row();
        contentTable.add(motherTable).grow().row();

        contentTable.add("").grow().row();


        TextButton tb = new TextButton(LANG.format("upgrades_stats_info_dialog_close"), skin);
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
