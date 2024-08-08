package de.keeyzar.earthragequit.story.regular_story.landing_story;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * Created by Keeyzar on 06.04.2016.
 */
public class ERQPicActor extends Actor {
    Sprite sprite;

    public ERQPicActor(float width, float height){
        setWidth(width);
        setHeight(height);
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ERQ_SCREEN);
        sprite.setSize(getWidth(), getHeight());
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha * getColor().a);
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        sprite.setPosition(getX(), getY());
    }
}
