package de.keeyzar.earthragequit.loading;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 20.03.2016
 */
public class LoadingBalken extends Actor {
    private AssetManager assetManager;

    private NinePatchDrawable loadingBarBackground;
    private NinePatchDrawable loadingBar;
    private Color foreground;
    private Color background;

    public LoadingBalken(AssetManager assetManager){
        this.assetManager = assetManager;

        foreground = Color.RED;
        background = Color.GRAY;

        TextureAtlas txA = assetManager.get(AssetVariables.UI_SKIN_ATLAS, TextureAtlas.class);
        NinePatch loadingBarBackgroundPatch = new NinePatch(txA.findRegion("default-scroll"), 5, 5, 4, 4);
        NinePatch loadingBarPatch = new NinePatch(txA.findRegion("default-round-large"), 5, 5, 4, 4);
        loadingBarPatch.setColor(foreground);
        loadingBarBackgroundPatch.setColor(background);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);

        setSize(ScreenVariables.SCREEN_WIDTH / 2, 75);
        setPosition(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float progress = assetManager.getProgress();
        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
        loadingBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
    }
}
