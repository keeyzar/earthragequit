package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 05.02.2017.
 */
public class RocketParticleFire extends Actor {
    private final ParticleEffect particleEffect;
    private final Player player;

    private boolean isEnabled = true;
    private boolean shouldReset;

    public RocketParticleFire(Player player){
        this.player = player;
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.ROCKET_FIRE, ParticleEffect.class));
        particleEffect.setPosition(Player.WIDTH / 2, 0);
        enable(false);
    }

    public void enable(boolean shouldBeEnabled){
        if(isEnabled == shouldBeEnabled) return;
        if(shouldBeEnabled){
            particleEffect.start();
        } else {
            particleEffect.allowCompletion();
        }
        isEnabled = shouldBeEnabled;
    }

    @Override
    public void scaleBy(float scale) {
        super.scaleBy(scale);
        particleEffect.scaleEffect(scale);
    }

    @Override
    public void act(float delta){
        particleEffect.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!particleEffect.isComplete()) particleEffect.draw(batch);
    }

    private Vector2 stageCoords = new Vector2();

    public Vector2 getStageCoords(){
        return localToStageCoordinates(stageCoords.set(getParent().getWidth() / 2, -0.5f));
    }

    public void reset() {
        particleEffect.reset();
    }
}
