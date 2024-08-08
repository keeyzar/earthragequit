package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class Portal extends Actor {
    private ParticleEffect particleEffect;
    private Sprite sprite;
    private boolean particleDrawing = false;

    public Portal(final NewDimension_StoneClone.Callback callback) {
        setBounds(600, 150, 500, 180);
        setPosition(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, 200);

        particleEffect = ERQAssets.MANAGER.get(AssetVariables.PORTAL_EMITTER, ParticleEffect.class);
        particleEffect.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
        particleEffect.start();

        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.DIMENSION_PORTAL);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        getColor().a = 0;
        sprite.setColor(getColor());
        addAction(Actions.sequence(Actions.fadeIn(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                particleDrawing = true;
            }
        }), Actions.delay(3), Actions.run(new Runnable() {
            @Override
            public void run() {
                callback.finishedAnim();
            }
        })));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setColor(getColor());
        if(particleDrawing) particleEffect.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);

        if(particleDrawing) particleEffect.draw(batch);
    }
}
