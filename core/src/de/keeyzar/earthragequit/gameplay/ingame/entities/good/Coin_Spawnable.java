package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.MagneticComponent;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Coin_Spawnable extends Coin {

    private Body playerBody;
    private float movingSpeed = 3;
    ParticleEffect particleEffect;

    public Coin_Spawnable(World world, int value, float x, float y, Body playerBody){
        super(world, value, x, y);
        this.playerBody = playerBody;
        userData.setEnabled(true);
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.SPAWNABLE_SPARKLE, ParticleEffect.class));
        particleEffect.setPosition(x, y);
        particleEffect.start();
    }

    @Override
    public void act(float delta) {
        //should always be enabled, no matter WHAT!
        userData.setEnabled(true);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        particleEffect.draw(batch);
    }

    @Override
    public void extraActStuff() {
        super.extraActStuff();

        movingSpeed += MagneticComponent.applyForce(body, playerBody, movingSpeed);
        if(movingSpeed > 50) movingSpeed+= 100;
        particleEffect.update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void applyForce(Body whereToMove) {
        //do nothing, because we already do a magnetic behaviour
    }
}
