package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.AdjustHudDialog;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.04.2016
 */
public class SizeComponent {


    /**
     * adds Width Height change size component
     * @param table which to add
     * @param fixedRatio whether or not width height ratio should be fixed
     * @param minSize if the size is capped at a maximum (-1 does cap)
     * @param maxSize if the size is capped at a minimum (-1 does cap)
     */
    public void addChangeSizeComponent(Table table, final boolean fixedRatio, float minSize, float maxSize, final HudActor hudActor){
        Skin skin = ERQAssets.SKIN;
        float lWidth = AdjustHudDialog.lWidth;
        float lHeight = AdjustHudDialog.lHeight;
        float bWidth = AdjustHudDialog.bWidth;
        float bHeight = AdjustHudDialog.bHeight;


        TextButton ibWidthBigger = new TextButton("+", skin);
        TextButton ibWidthSmaller = new TextButton("-", skin);
        TextButton ibHeightBigger = null;
        TextButton ibHeightSmaller = null;
        TextButton ibBothBigger = new TextButton("+", skin);
        TextButton ibBothSmaller = new TextButton("-", skin);



        if(!fixedRatio) {
            ibHeightBigger = new TextButton("+",skin);
            ibHeightSmaller = new TextButton("-",skin);
        }

        table.setDebug(true);
        table.add(new Label(LANG.format("adjust_hud_size"), skin)).colspan(3).padBottom(ScreenVariables.PAD_TB_S).row();

        Table bothTable = new Table();
        bothTable.add(new Label(LANG.format("adjust_hud_size_width_height"), skin)).colspan(2).center().padBottom(ScreenVariables.PAD_TB_S).row();
        bothTable.add(ibBothSmaller).size(bWidth, bHeight).padRight(ScreenVariables.PAD_LR_N);
        bothTable.add(ibBothBigger).size(bWidth, bHeight).row();

        if(!fixedRatio){
            bothTable.add(new Label(LANG.format("adjust_hud_size_only_width"), skin)).padTop(ScreenVariables.PAD_TB_N).colspan(2).padBottom(ScreenVariables.PAD_TB_S).row();
            bothTable.add(ibWidthSmaller).size(bWidth, bHeight).padRight(ScreenVariables.PAD_LR_N);
            bothTable.add(ibWidthBigger).size(bWidth, bHeight).row();

            bothTable.add(new Label(LANG.format("adjust_hud_size_only_height"), skin)).padTop(ScreenVariables.PAD_TB_N).colspan(2).padBottom(ScreenVariables.PAD_TB_S).row();
            bothTable.add(ibHeightSmaller).size(bWidth, bHeight).padRight(ScreenVariables.PAD_LR_N);
            bothTable.add(ibHeightBigger).size(bWidth, bHeight).row();
        }
        table.add(bothTable).colspan(3).row();


        ibBothSmaller.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeSize(true, true, false, hudActor);
            }
        });

        ibBothBigger.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeSize(true, true, true, hudActor);
            }
        });

        if(!fixedRatio) {
            ibWidthSmaller.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    changeSize(false, true, false, hudActor);
                }
            });

            ibWidthBigger.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    changeSize(false, true, true, hudActor);
                }
            });
            ibHeightSmaller.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    changeSize(false, false, false, hudActor);
                }
            });

            ibHeightBigger.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    changeSize(false, false, true, hudActor);
                }
            });
        }
    }

    private void changeSize(boolean fixedRatio, boolean width, boolean plus, HudActor hudActor){
        float amount = plus ? 5 : -5;
        if(fixedRatio){
            hudActor.setWidth(hudActor.getWidth() + amount);
            hudActor.setHeight(hudActor.getHeight() + amount);
        } else {
            if(width){
                hudActor.sizeBy(amount, 0);
            } else {
                hudActor.sizeBy(0, amount);
            }
        }
        hudActor.ensureSize();

        //finally check, if the actor is within the bounds
        Camera camera = hudActor.getStage().getCamera();
        if(plus){
            if(hudActor.getRight() > camera.position.x + camera.viewportWidth / 2){
                hudActor.setX(camera.position.x + camera.viewportWidth / 2 - hudActor.getWidth());
            }

            if(hudActor.getX() < camera.position.x - camera.viewportWidth / 2){
                hudActor.setX(camera.position.x - camera.viewportWidth / 2);
            }

            if(hudActor.getTop() > camera.position.y + camera.viewportHeight / 2){
                hudActor.setY(camera.position.y + camera.viewportHeight / 2 - hudActor.getHeight());
            }

            if(hudActor.getY() < camera.position.y - camera.viewportHeight / 2){
                hudActor.setY(camera.position.y - camera.viewportHeight / 2);
            }

        }
    }
}
