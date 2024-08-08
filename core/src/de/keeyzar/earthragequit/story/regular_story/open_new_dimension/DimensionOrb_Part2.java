package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class DimensionOrb_Part2 extends DimensionOrb {
    ParticleEffect particleEffect;
    private boolean drawParticle = false;

    public DimensionOrb_Part2(Vector2 playerPos, final NewDimension_StoneClone.Callback callback){
        super();
        setBounds(playerPos.x - 25, playerPos.y, 50, 50);
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.DIMENSION_ORB_TRAIL, ParticleEffect.class));
        particleEffect.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
        particleEffect.start();
        getColor().a = 0;

        addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        drawParticle = true;
                    }
                }), Actions.moveTo(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 + 100, 2),
                Actions.delay(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        callback.finishedAnim();
                    }
                })));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(drawParticle){
            particleEffect.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(drawParticle) {
            particleEffect.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
            particleEffect.update(delta);
        }
    }

    public void moveToPortalAndFadeOut(Vector2 pos, final NewDimension_StoneClone.Callback callback){
        addAction(Actions.sequence(
                Actions.moveTo(pos.x - getWidth() / 2, pos.y - getHeight() / 2, 2),
                Actions.fadeOut(2),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        callback.finishedAnim();
                        remove();
                    }
                })));
    }
}
