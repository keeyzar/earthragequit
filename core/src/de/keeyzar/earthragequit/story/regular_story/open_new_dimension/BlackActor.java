package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

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
public class BlackActor extends Actor {
    private Sprite sprite;

    public BlackActor() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BLACK_DOT);
        sprite.setBounds(0, 0, ScreenVariables.SCREEN_WIDTH, ScreenVariables.SCREEN_HEIGHT);
        getColor().a = 0;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch, parentAlpha);
    }

    public void start(final NewDimension_StoneClone.Callback callback){
        addAction(Actions.sequence(Actions.fadeIn(2), Actions.run(new Runnable() {
            @Override
            public void run() {
                callback.finishedAnim();
            }
        })));
    }

    public void finish(final NewDimension_StoneClone.Callback callback){
        addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                callback.finishedAnim();
            }
        })));
    }

}
