package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.AdjustHudDialog;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.04.2016
 */
public class PositionComponent {
    /**
     * adds position changing
     * @param table
     */
    public void addChangePositionComponent(Table table, HudActor hudActor){
        Skin skin = ERQAssets.SKIN;
        float bWidth = AdjustHudDialog.bWidth;
        float bHeight = AdjustHudDialog.bHeight;

        //West east.. NorthEast etc..
        ImageButton ibW = new ImageButton(skin);
        ImageButton ibE = new ImageButton(skin);
        ImageButton ibS = new ImageButton(skin);
        ImageButton ibN = new ImageButton(skin);

        ImageButton ibNE = new ImageButton(skin);
        ImageButton ibNW = new ImageButton(skin);
        ImageButton ibSE = new ImageButton(skin);
        ImageButton ibSW = new ImageButton(skin);

        Table smallTable = new Table(skin);
        Table labeltable = new Table();

        labeltable.add(new Label(LANG.format("adjust_hud_position"), skin));

        smallTable.add(labeltable).colspan(3).padBottom(ScreenVariables.PAD_TB_S).row();
        //row ONE
        smallTable.add(ibNW).size(bWidth, bHeight)
                .padBottom(ScreenVariables.PAD_TB_N)
                .padRight(ScreenVariables.PAD_LR_N);
        smallTable.add(ibN).size(bWidth, bHeight)
                .padBottom(ScreenVariables.PAD_TB_N)
                .padRight(ScreenVariables.PAD_LR_N);
        smallTable.add(ibNE).size(bWidth, bHeight).padBottom(ScreenVariables.PAD_TB_N).row();

        //row TWO
        smallTable.add(ibW).size(bWidth, bHeight).padRight(ScreenVariables.PAD_LR_N)
                .padBottom(ScreenVariables.PAD_TB_N);
        smallTable.add("").padBottom(ScreenVariables.PAD_TB_N).grow();
        smallTable.add(ibE).size(bWidth).padBottom(ScreenVariables.PAD_TB_N).row();

        //row THREE
        smallTable.add(ibSW).size(bWidth, bHeight)
                .padRight(ScreenVariables.PAD_LR_N);
        smallTable.add(ibS).size(bWidth, bHeight)
                .padRight(ScreenVariables.PAD_LR_N);
        smallTable.add(ibSE).size(bWidth, bHeight);
        table.add(smallTable).colspan(3).padTop(ScreenVariables.PAD_TB_N).row();

        //addListener
        float movePlus = 5;
        float moveMinus = -5;
        ibNW.addListener(new ModifiedClickListener(moveMinus, movePlus, hudActor));
        ibN.addListener(new ModifiedClickListener(0, movePlus, hudActor));
        ibNE.addListener(new ModifiedClickListener(movePlus, movePlus, hudActor));
        ibW.addListener(new ModifiedClickListener(moveMinus, 0, hudActor));
        ibE.addListener(new ModifiedClickListener(movePlus, 0, hudActor));
        ibSW.addListener(new ModifiedClickListener(moveMinus, moveMinus, hudActor));
        ibS.addListener(new ModifiedClickListener(0, moveMinus, hudActor));
        ibSE.addListener(new ModifiedClickListener(movePlus, moveMinus, hudActor));

        setImages(ibNW, ibN, ibNE, ibW, ibE, ibSW, ibS, ibSE);

    }

    /**
     * sets all image
     * @param ibNW
     * @param ibN
     * @param ibNE
     * @param ibW
     * @param ibE
     * @param ibSW
     * @param ibS
     * @param ibSE
     */
    private void setImages(ImageButton ibNW, ImageButton ibN, ImageButton ibNE, ImageButton ibW, ImageButton ibE,
                           ImageButton ibSW, ImageButton ibS, ImageButton ibSE) {
        Sprite sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ADJUST_HUD_ARROW);
        //1. create multiple drawabales
        SpriteDrawable spN = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spNW = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spW = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spSW = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spS = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spSE = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spE = new SpriteDrawable(new Sprite(sprite));
        SpriteDrawable spNE = new SpriteDrawable(new Sprite(sprite));


        //2. create multiple styles, with the in step 1 created drawables
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spN;
        ibN.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spNW;
        ibNW.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spW;
        ibW.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spSW;
        ibSW.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spS;
        ibS.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spSE;
        ibSE.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spE;
        ibE.setStyle(ibs);

        ibs = new ImageButton.ImageButtonStyle(ibNW.getStyle());
        ibs.imageUp = spNE;
        ibNE.setStyle(ibs);

        //adjust design
        ibNW.getImage().rotateBy(45);
        ibNW.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibW.getImage().rotateBy(90);
        ibW.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibSW.getImage().rotateBy(135);
        ibSW.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibS.getImage().rotateBy(180);
        ibS.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibSE.getImage().rotateBy(225);
        ibSE.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibE.getImage().rotateBy(270);
        ibE.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        ibNE.getImage().rotateBy(315);
        ibNE.getImage().setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);



    }


    /**
     * faster way to add Listener to movePositionButtons
     */
    private class ModifiedClickListener extends ClickListener {

        private final float widthAmount;
        private final float heightAmount;
        HudActor hudActor;

        ModifiedClickListener(float widthAmount, float heightAmount, HudActor hudActor){
            this.widthAmount = widthAmount;
            this.heightAmount = heightAmount;
            this.hudActor = hudActor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            hudActor.moveBy(widthAmount, heightAmount);
            Camera camera = hudActor.getStage().getCamera();
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

