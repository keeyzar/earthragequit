package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 02.02.2017.
 */
public class ParticleWall extends Actor{
    private ParticleEffect particleEffect;

    public ParticleWall(){
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.PARTICLE_WALL, ParticleEffect.class));
        particleEffect.start();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        particleEffect.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        particleEffect.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        particleEffect.draw(batch);
    }
}
