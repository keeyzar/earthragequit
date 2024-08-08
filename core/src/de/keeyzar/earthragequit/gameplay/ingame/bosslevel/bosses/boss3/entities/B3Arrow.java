package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.ERQArrowUD;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.DynamicActor;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.InterpolatedBodySprite;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * Arrow for Boss3 only
 * @author = Keeyzar on 26.11.2016.
 */
public class B3Arrow extends DynamicActor{

    private int lastHit = 0;
    private World world;
    private InterpolatedBodySprite sprite;
    private ERQArrowUD userData;
    private boolean gotHitByFlame1 = false;
    private boolean gotHitByFlame2 = false;
    private boolean gotHitByFlame3 = false;

    public B3Arrow(World world){
        this.world = world;
        setBounds(-5, 5, 0.5f, 2);
        oldPosition.set(getX(), getY());
        position.set(getX(), getY());
        initBody();

        sprite = new InterpolatedBodySprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_FIREABLE));
    }


    @Override
    public void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0.5f);
        MassData massData = body.getMassData();
        body.setMassData(massData);
        body.setGravityScale(0);
        body.setTransform(body.getPosition(), MathUtils.degreesToRadians * -90);
        shape.dispose();

        userData = new ERQArrowUD(EntityVars.B3_ARROW, this);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
        body.setLinearVelocity(6, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkCollision();
    }

    private Object userDataHolder;
    private void checkCollision() {
        if(userData.actualCollisioningUserData == null) return;

        userDataHolder = userData.actualCollisioningUserData.getUserDataHolder();
        if(userDataHolder instanceof TriggeredFlame
                && lastHit != userData.actualCollisioningUserData.getId()
                && ((TriggeredFlame) userDataHolder).isEnabled()){
            switch (userData.actualCollisioningUserData.getId()){
                case EntityVars.TRIGGER_FIRE_1:
                    gotHitByFlame1 = true;
                    break;
                case EntityVars.TRIGGER_FIRE_2:
                    gotHitByFlame2 = true;
                    break;
                case EntityVars.TRIGGER_FIRE_3:
                    gotHitByFlame3 = true;
                    break;
            }
            body.setLinearVelocity(2, 0);
            updateColor();
            userData.actualCollisioningUserData = null;
        } else if(userData.actualCollisioningUserData.getId() == EntityVars.BULLSEYE){
            Bullseye bullseye = (Bullseye) userDataHolder;
            if (getColor().equals(bullseye.getColor())) {
                goodAttempt();
                bullseye.changeColor();
            } else {
                failedAttempt();
            }
            userData.actualCollisioningUserData = null;
        }
    }

    /**
     * bullseyeColor is equal to arrow color
     */
    private void goodAttempt() {
        destroy();
    }

    /**
     * bullseyeColor is not equal to arrow color
     */
    private void failedAttempt() {
        destroy();
    }

    public void destroy() {
        if(body != null) {
            world.destroyBody(body);
            body = null;
        }
        remove();
    }

    private void updateColor() {
        setColor(gotHitByFlame1 ? 1 : 0, gotHitByFlame2 ? 1 : 0, gotHitByFlame3 ? 1 : 0,1);
        if(getColor().equals(Color.WHITE)){
            setColor(Color.BLACK.cpy());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch, body, position, angle);
    }






}
