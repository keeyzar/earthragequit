package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 05.02.2017.
 */
public class RocketParticleEffect {
    private final ParticleEffect particleEffect;
    private final Player player;

    private boolean isEmitting = true;
    private boolean hasStarted = false;
    public RocketParticleEffect(Player player){
        this.player = player;
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.ROCKET_PARTICLE, ParticleEffect.class));
    }

    public void enableEmitting(boolean shouldEmmit){
        if(isEmitting == shouldEmmit) return;
        if(!hasStarted && shouldEmmit){
            particleEffect.start();
            hasStarted = true;
        }
        isEmitting = !isEmitting;
        int newValue = shouldEmmit ? 25 : 0;
        final Array<ParticleEmitter> emitters = particleEffect.getEmitters();
        for(ParticleEmitter emitter : emitters){
            emitter.getEmission().setHigh(0, newValue);
        }
    }

    public void act(float delta, Vector2 firePosition){
        if(hasStarted) {
            particleEffect.setPosition(firePosition.x, firePosition.y);
            particleEffect.update(delta);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        if(hasStarted) {
            particleEffect.draw(batch);
        }
    }


}
