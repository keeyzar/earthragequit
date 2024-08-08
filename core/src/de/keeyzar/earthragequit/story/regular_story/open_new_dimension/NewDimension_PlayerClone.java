package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class NewDimension_PlayerClone extends Actor {
    private Sprite sprite;

    public NewDimension_PlayerClone(ERQGame game){
        //get actual shipSkin;
        final SpriteDrawable skinImageFromActualSkin = game.getSkinVerwalter().getSkinImageFromActualSkin();
        sprite = skinImageFromActualSkin.getSprite();
        sprite.setOriginCenter();

        setBounds(100, 0, 100, 175);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public void start(){
        addAction(Actions.moveTo( ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 - getHeight() / 2, 1));
    }
}
