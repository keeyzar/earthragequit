package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class DimensionOrb_Part1 extends DimensionOrb {
    private final SequenceAction movement;
    ParticleEffect particleEffect;
    private boolean drawParticle = false;

    public DimensionOrb_Part1(NewDimension_StoneClone stoneClone, final Vector2 position, final NewDimension_StoneClone.Callback callback){
        super();
        setBounds(stoneClone.getCenterX() - 25, stoneClone.getCenterY(), 50, 50);

        final ParallelAction parallel1 = Actions.parallel(
                Actions.rotateBy(3600, 4),
                Actions.moveBy(0, 200, 4),
                Actions.sequence(Actions.delay(1), Actions.moveBy(-100, 0, 1, Interpolation.smoother),
                        Actions.moveBy(150, 0, 2, Interpolation.smoother))
        );

        final ParallelAction parallel2 = Actions.parallel(Actions.rotateBy(3600, 4),
                Actions.moveBy(position.x - getX() - getWidth() / 2, 0, 4),
                Actions.sequence(
                        Actions.moveBy(0, 100, 1, Interpolation.smoother),
                        Actions.moveBy(0, -300, 1, Interpolation.smoother),
                        Actions.moveBy(0, 300, 1, Interpolation.smoother),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                addAction(Actions.moveBy(0, position.y - getY() - getHeight() / 2, 1, Interpolation.smoother));
                            }
                        })

                )
        );
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.DIMENSION_ORB_TRAIL, ParticleEffect.class));

        movement = Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                drawParticle = true;
            }
        }),parallel1, parallel2, Actions.run(new Runnable() {
            @Override
            public void run() {
                callback.finishedAnim();
                addAction(Actions.moveTo(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 + 100, 1));
            }
        }));
        particleEffect.start();
    }

    public void start(){
        addAction(movement);
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
}
