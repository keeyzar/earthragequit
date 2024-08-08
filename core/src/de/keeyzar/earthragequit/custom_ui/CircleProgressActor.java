package de.keeyzar.earthragequit.custom_ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 27.01.2017.
 */
public class CircleProgressActor extends Actor {
    private RadialProgress radialProgress;
    private Sprite topSprite;
    private CircleProgressListener progressListener;

    public CircleProgressActor(CircleProgressListener progressListener){
        this.progressListener = progressListener;
        radialProgress = new RadialProgress(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.CIRCLE_PROGRESS_BOTTOM));
        topSprite = new Sprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.CIRCLE_PROGRESS_TOP));
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        topSprite.setBounds(x, y, width, height);
        radialProgress.setBounds(x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        radialProgress.draw((SpriteBatch) batch);
        topSprite.draw(batch, parentAlpha);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        radialProgress.setPercent(progressListener.getProgress());
    }

    public interface CircleProgressListener{
        float getProgress();
    }
}
