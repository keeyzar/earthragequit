package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;


/**
 * Simple Dialog with only one Buttongit c "close"
 * @author Keeyzar on 26.07.2015
 */
public class SingleButtonDialog {

    /**
     * creates a dialog on the stage, with the given text. and only one button.
     * @param stage on which to show
     * @param text which text to show
     * @param button1 buttontext
     * @param width from the dialog
     * @param height from the dialog
     */
    public static void  createDialog(Stage stage, String text, String button1, final int width, final int height){
        Skin skin = ERQAssets.SKIN;

        final Label label = new Label(text, skin);
        label.setWrap(true);
        label.setAlignment(Align.center);

        final Dialog dialog = new Dialog("", skin){
            @Override
            public float getPrefWidth() {
                return label.getWidth() + ScreenVariables.PAD_TB_N;
            }

            @Override
            public float getPrefHeight() {
                return height;
            }
        };

        dialog.padTop(50).padBottom(50);
        dialog.getContentTable().add(label).size(width).row();
        dialog.getButtonTable().padTop(50);
        dialog.getButtonTable().defaults().width(ScreenVariables.B_WID);
        dialog.getButtonTable().defaults().height(ScreenVariables.B_HEI);

        TextButton dbutton = new TextButton(button1, skin);
        dialog.button(dbutton, true);

        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);
    }
}
