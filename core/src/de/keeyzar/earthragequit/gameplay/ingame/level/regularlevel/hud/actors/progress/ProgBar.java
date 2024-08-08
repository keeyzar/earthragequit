package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 30.03.2016
 */
public abstract class ProgBar extends HudActor {
    private NinePatchDrawable loadingBarBackground;
    private NinePatchDrawable loadingBarForeground;

    Color foreground;
    public Color background;
    private boolean fromLeftToRight;

    private ProgressListener progressListener;
    public float progress;

    private Image image;

    public ProgBar(String name) {
        super(name);
    }

    public void init(ProgressListener progressListener, Color foreground, Color background, boolean fromLeftToRight){
        this.progressListener = progressListener;
        this.foreground = foreground.cpy();
        this.background = background.cpy();
        this.fromLeftToRight = fromLeftToRight;
        foreground.a = getColor().a;
        background.a = getColor().a;

        TextureAtlas txA = ERQAssets.MANAGER.get(AssetVariables.UI_SKIN_ATLAS, TextureAtlas.class);
        loadingBarForeground = new NinePatchDrawable(txA.createPatch("default-round-large"));
        loadingBarBackground = new NinePatchDrawable(txA.createPatch("default-scroll"));
    }

    Color oldColor;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        oldColor = batch.getColor();
        progress = progressListener.getProgress();
        batch.setColor(background);
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        if(progress > 0.005) {
            batch.setColor(foreground);
            if (fromLeftToRight) {
                loadingBarForeground.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
            } else {
                loadingBarForeground.draw(batch, getX(), getY(), getWidth() * getScaleX(), progress * getHeight() * getScaleY());
            }
        }
        batch.setColor(oldColor);
        if(image != null){
            image.draw(batch, parentAlpha);
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        //foreground
        modifyForeground();
        foreground.a = getColor().a;

        //background
        background.a = getColor().a;
        if(image != null) {
            if (fromLeftToRight) {
                final float size = getHeight() + 20;
                image.setBounds(getX() - size - 5, getY() + getHeight() / 2 - size / 2, size, size);
            } else {
                final float size = getWidth() + 20;
                image.setBounds(getX() + getWidth() / 2 - size / 2, getY() - size - 5, size, size);
            }
            image.getColor().a = getColor().a;
        }
    }

    //called right before the patch color is set.
    public void modifyForeground(){

    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void ensureSize() {
        if(fromLeftToRight){
            if(getWidth() < 100) setWidth(100);
            if(getHeight() < 20) setHeight(20);
            if(getHeight() > 40) setHeight(40);
        } else {
            if (getWidth() < 15) setWidth(15);
            if (getHeight() < 100) setHeight(100);
            if (getWidth() > 40) setWidth(40);
        }
    }
}
