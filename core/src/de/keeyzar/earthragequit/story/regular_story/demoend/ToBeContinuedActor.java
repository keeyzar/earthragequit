package de.keeyzar.earthragequit.story.regular_story.demoend;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 14.01.2017.
 */
public class ToBeContinuedActor extends Actor {
    private Sprite sprite;

    ToBeContinuedActor() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.TO_BE_CONTINUED);
        setBounds(100, 300, 1000, 200);
//        setBounds(1, 1, 100, 200);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        getColor().a = 0;

        final DelayAction delayAction = Actions.delay(MathUtils.random(0.02f, 1f));
        addAction(Actions.sequence(Actions.fadeIn(2)
            ,Actions.forever(Actions.sequence(Actions.fadeOut(0.002f), Actions.delay(0.02f), Actions.fadeIn(0.002f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        delayAction.setDuration(MathUtils.random(0.02f, 1f));
                    }
                }), delayAction)))
                );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setColor(getColor());
    }
}
