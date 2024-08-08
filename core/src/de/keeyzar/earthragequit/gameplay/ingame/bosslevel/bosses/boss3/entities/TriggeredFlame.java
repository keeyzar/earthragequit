package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.HitableByArrow;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 26.11.2016.
 */
public class TriggeredFlame extends GameActor implements HitableByArrow{

    ERQUserDataEntities userData;
    private World world;
    private boolean enabled;
    private Body body;
    private ParticleEffect sparkle;



    /**
     * @param positon 1, 2 or 3
     */
    public TriggeredFlame(int positon, World world){
        if(positon < 1 || positon > 3){
            throw new RuntimeException("I SAID ONE, TWO, OR THREE!(please use in "+ getClass().getName() +" as param" +
                    " position only 1 or 2 or 3)");
        }
        this.world = world;

        int relevantId = 0;
        setWidth(1);
        setHeight(3);
        setY(4.5f);

        String whichEmitter = "";

        switch (positon){
            case 1:
                relevantId = EntityVars.TRIGGER_FIRE_1;
                setX(5);
                whichEmitter = AssetVariables.BOSS3_EMITTER_RED;
                break;
            case 2:
                relevantId = EntityVars.TRIGGER_FIRE_2;
                setX(10);
                whichEmitter = AssetVariables.BOSS3_EMITTER_GREEN;
                break;
            case 3:
                relevantId = EntityVars.TRIGGER_FIRE_3;
                setX(15);
                whichEmitter = AssetVariables.BOSS3_EMITTER_BLUE;
                break;
        }

        sparkle = new ParticleEffect(ERQAssets.MANAGER.get(whichEmitter, ParticleEffect.class));
        sparkle.setPosition(getX(), getY() - getHeight() / 2);
        sparkle.setDuration(-1);

        initBody(relevantId);
    }

    public void initBody(int relevantId) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0.5f);
        shape.dispose();
        userData = new ERQUserDataEntities(this, relevantId);
        fixture.setUserData(userData);
        setEnabled(false);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(enabled || !sparkle.isComplete()) {
            sparkle.update(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(enabled || !sparkle.isComplete()){
            sparkle.draw(batch);
        }
    }

    public void setEnabled(boolean enabled){
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        userData.setEnabled(enabled);
        if(enabled) {
            sparkle.setDuration(400);
            sparkle.reset();
            sparkle.setPosition(getX(), getY() - getHeight() / 2);
            for(ParticleEmitter particleEmitter : sparkle.getEmitters()){
                particleEmitter.setContinuous(true);
            }
        } else {
            sparkle.setDuration(1);
        }

    }

    @Override
    public void gotHitByArrow() {
        //do noddinHill
    }

    public void destroy() {
        if(body != null) {
            world.destroyBody(body);
        }
        remove();
    }

    @Override
    public void activateActor() {
        //do nothing
    }

    @Override
    public void disableActor() {
        //do nothing
    }

    public boolean isEnabled() {
        return enabled;
    }
}
