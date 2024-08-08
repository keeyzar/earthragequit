package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * @author = Keeyzar on 25.04.2016
 */
public class InfoDialog {

    private Stage stage;
    private Skin skin;

    public InfoDialog(Stage stage){
        this.stage = stage;
        skin = ERQAssets.SKIN;
    }

    public void createDialog(String titel, String textToDisplay){

        final Dialog dialog = new Dialog("", skin);
//        dialog.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        dialog.setFillParent(true);

        dialog.getContentTable().add(new Label("Information for " + titel, skin)).padTop(PAD_TB_N).padBottom(PAD_TB_N).row();
        TextArea textArea = new TextArea(textToDisplay, skin);
        textArea.setDisabled(true);
        dialog.getContentTable().add(textArea).padLeft(PAD_TB_N).padRight(PAD_TB_N).grow().row();

        final TextButton dbutton = new TextButton("close", skin);
        dbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                dialog.hide();
            }
        });
        dialog.getButtonTable().add(dbutton).width(SCREEN_WIDTH).padTop(PAD_TB_N).height(B_HEI).padBottom(PAD_TB_S).row();

        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);
    }
}
