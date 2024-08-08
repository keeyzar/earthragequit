package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class DimensionOrb extends Actor{
    private Sprite sprite;

    public DimensionOrb() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.DIMENSION_ORB);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setRotation(getRotation());
        sprite.setColor(getColor());
    }

    @Override
    public void setBounds(float x, float y, float width, float height){
        super.setBounds(x, y, width, height);
        setOrigin(width / 2, height / 2);
        sprite.setBounds(x, y, width, height);
        sprite.setOriginCenter();
    }
}
