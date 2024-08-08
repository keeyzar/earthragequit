package de.keeyzar.earthragequit.menu.screens.elements;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;


/**
 * Simple Questioning dialog (Do you want to buy?? yes no, with callback)
 * @author = Keeyzar on 26.07.2015
 */
public class ERQDialog {

    String button1;
    String button2;
    String question;

    private final Skin skin;
    private final Stage stage;

    private DialogAnswer dialogAnswerListener;

    /**
     * Erstelle Dialog
     * @param button1 true
     * @param button2 false
     */
    public ERQDialog(Stage stage, String question, String button1, String button2, DialogAnswer dialogAnswerListener){
        this.skin = ERQAssets.SKIN;
        this.stage = stage;
        this.question = question;
        this.button1 = button1;
        this.button2 = button2;
        this.dialogAnswerListener = dialogAnswerListener;
    }

    public void createDialog(){
        createDialogCritical(false);
    }

    public void setDialogAnswerListener(DialogAnswer dialogAnswerListener) {
        this.dialogAnswerListener = dialogAnswerListener;
    }

    /**
     * If you want to ensure that the user reads the text, and cant confirm immidiately, than
     * create a critical dialog(confirm is visible if 5 seconds have passed)
     * @param critical
     */
    public void createDialogCritical(boolean critical){
        final Label label = new Label(question, skin);
        label.setWrap(true);
        label.setAlignment(Align.center);

        final com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog =
                new com.badlogic.gdx.scenes.scene2d.ui.Dialog("", skin) {
                    protected void result (Object object) {
                        if(object.toString().equals("true")) {
                            dialogAnswerListener.confirmed();
                        } else {
                            MenuUtils.playClickSound();
                        }
                    }

                    @Override
                    public float getPrefHeight() {
                        return 400;
                    }

                    @Override
                    public float getPrefWidth() {
                        return label.getWidth() + ScreenVariables.PAD_TB_N;
                    }
                };

        dialog.padTop(50).padBottom(50);
        dialog.getContentTable().add(label).width(850).row();
        dialog.getButtonTable().padTop(50);
        dialog.getButtonTable().defaults().width(ScreenVariables.B_WID);
        dialog.getButtonTable().defaults().height(ScreenVariables.B_HEI);


        TextButton dbutton = new TextButton(button2, skin);
        dialog.button(dbutton, false);

        dbutton = new TextButton(button1, skin);
        dialog.button(dbutton, true);

        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        if(critical) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.getButtonTable().getCells().get(1).getActor().setTouchable(Touchable.disabled);
                    dialog.getButtonTable().getCells().get(1).getActor().setVisible(false);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.getButtonTable().getCells().get(1).getActor().setTouchable(Touchable.enabled);
                    dialog.getButtonTable().getCells().get(1).getActor().setVisible(true);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        dialog.show(stage);
    }
}
