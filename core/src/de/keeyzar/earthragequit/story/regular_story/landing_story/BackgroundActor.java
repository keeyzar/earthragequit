package de.keeyzar.earthragequit.story.regular_story.landing_story;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 17.03.2016
 */
public class BackgroundActor extends Actor {
    Sprite sprite;

    public BackgroundActor(){
        sprite = new Sprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_EARTH));
        setSize(ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        setPosition(0, 0);
        sprite.setBounds(getX(), getY(), getWidth(),getHeight());

        getColor().a = 0;
        sprite.setColor(getColor());

        addAction(Actions.fadeIn(1));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setColor(getColor());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
