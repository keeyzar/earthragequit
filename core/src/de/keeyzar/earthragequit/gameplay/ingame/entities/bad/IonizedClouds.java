package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarEntity;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class IonizedClouds extends MovingRadarEntity {
    ParticleEffect particleEffect;
    private static float shieldTime = 1.5f;

    public IonizedClouds(World world, float x, float y) {
        super(EntityVars.IONIZED_CLOUD, EntityVars.GROUP_BAD);
        initBounds(x, y, 2, 2);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        initMovement(2f, DO_NOT_MOVE);
        init();
    }

    public void init() {
        int number = MathUtils.random(1,3);
        particleEffect = new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.CLOUD_PARTICLEEFFECT + number, ParticleEffect.class));
        particleEffect.setPosition(position.x, position.y);
        particleEffect.scaleEffect(0.02f);
    }

    Color white = Color.WHITE;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            batch.setColor(getColor());
            particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
            batch.setColor(white);
        }
    }

    @Override
    public void extraActStuff() {
        particleEffect.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public static float getShieldTime() {
        return shieldTime;
    }

    @Override
    protected void collisionStuff() {
        userData.getPlayer().getPlayerCollision().ionizedCloudCollision();
    }

    @Override
    protected void randomHorMovement() {
        body.setLinearVelocity(MathUtils.random(-2, 2), 0);
    }
}

