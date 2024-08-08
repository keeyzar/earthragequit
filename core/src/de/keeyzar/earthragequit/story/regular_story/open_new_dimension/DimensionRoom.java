package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class DimensionRoom extends Actor {
    private Sprite sprite;

    public DimensionRoom() {
        setBounds(0, 0, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.DIMENSION_ROOM);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setColor(getColor());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void fadeToBlack(){
        addAction(Actions.color(Color.BLACK, 2));
    }
}
