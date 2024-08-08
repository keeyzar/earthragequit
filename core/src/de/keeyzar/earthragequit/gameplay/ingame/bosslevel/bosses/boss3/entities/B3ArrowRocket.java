package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.ERQArrowUD;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.DynamicActor;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.InterpolatedBodySprite;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class B3ArrowRocket extends DynamicActor{
    private final World world;
    private ERQArrowUD userData;

    private static Sound collectSound;
    private static Sound hitRockSound;


    private int state = 1;
    private static final int STATE_SPAWNED = 1; //is spawned
    private static final int STATE_PICKED_UP = 2; // is Picked Up by Fixture
    private static final int STATE_SHOT = 3; //is Shot after a give amount of time
    private static final int STATE_DESTROY = 4; // must be destroyed(out of frame / hit enemy)

    private Joint shooterArrowJoint;
    private final float timeToCatch = 7f;
    private final float timeToAim = 3;
    private long timeUntilDeath = 4;
    private float myTimer;
    private Vector2 moveTo;


    //Graphics
    private ParticleEffect sparkle;
    private InterpolatedBodySprite sprite;


    public B3ArrowRocket(World world){
        this.world = world;
        setWidth(1);
        setHeight(1.2f);
        setX(MathUtils.random(5, 15));
        setY(MathUtils.random(1, 2));
        initBody();
        initSprite();
        myTimer = 0;
        moveTo = new Vector2();
    }

    private void initSprite() {
        sparkle = ERQAssets.GLITTER_PARTICLE_EFFECT;
        sparkle.setPosition(getX(), getY());
        sparkle.start();
        sprite = new InterpolatedBodySprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ROCKET_FIREABLE));
        sprite.setSize(getWidth(), getHeight());
        sprite.setOriginCenter();
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
        massData.mass = 0.0000001f;
        body.setMassData(massData);
        body.setGravityScale(0);
        shape.dispose();

        userData = new ERQArrowUD(EntityVars.ROCKET_FIREABLE, this);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
    }

    @Override
    public void act(float delta) {
        myTimer += Math.min(delta, 0.25f);
        switch (state){
            case STATE_SPAWNED:
                actSpawned(delta);
                break;
            case STATE_PICKED_UP:
                actPickedUp();
                break;
            case STATE_SHOT:
                actShot();
                break;
            case STATE_DESTROY:
                actDestroyed();
                break;
        }

    }

    private void actDestroyed() {

    }

    private void actShot() {
        if(myTimer < timeUntilDeath){
            if(!userData.isAlive){
                playHitSound();
                destroy();
            }
        } else {
            destroy();
        }
    }

    private void actPickedUp() {
        if(myTimer < timeToAim) {
            body.setAngularVelocity(2);
        } else {
            state = STATE_SHOT;
            myTimer = 0;
            destroyJoint();
            int angle =  calcAngle();
            body.setAngularVelocity(0);
            body.setTransform(body.getPosition(), angle * MathUtils.degreesToRadians);
            moveTo.set(calc_x_impulse(angle), calc_y_impulse(angle)).nor().scl(15);
            body.setLinearVelocity(moveTo);
            myTimer = 0;

            //reset alive state, because user picked it up, and couldnt even shoot.
            userData.isAlive = true;
        }
    }

    private void actSpawned(float delta) {
        if(myTimer < timeToCatch){
            if(userData.fixThatPickedArrowUp != null){
                //shooter shooterBody
                Body shooterBody = userData.fixThatPickedArrowUp.getBody();
                this.body.setTransform(shooterBody.getPosition(), angle);

                playCollectSound();
                //createJoint
                RevoluteJointDef shooterArrowJointDef = new RevoluteJointDef();
                shooterArrowJointDef.type = JointDef.JointType.RevoluteJoint;
                shooterArrowJointDef.bodyA = shooterBody;
                shooterArrowJointDef.bodyB = this.body;
                shooterArrowJointDef.localAnchorA.set(0,0);
                shooterArrowJointDef.localAnchorB.set(0,0);
                shooterArrowJoint = world.createJoint(shooterArrowJointDef);
                myTimer = 0;
                state = STATE_PICKED_UP;
                return;
            }
            body.setAngularVelocity(2);
        } else {
            //time is overdue, player had not catched it..
            destroy();
            //sprite == null;
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sparkle.setPosition(position.x, position.y);
        sparkle.draw(batch, Gdx.graphics.getDeltaTime());
        sprite.draw(batch, body, position, angle);
    }

    public void destroy(){
        //destroy body out of world
        destroyJoint();
        if(body != null) {
            world.destroyBody(body);
        }
        //set body null(if not, really scary behaviour can happen.
        body = null;
        //remove from stage
        remove();

    }

    private void destroyJoint(){
        if(shooterArrowJoint != null){
            world.destroyJoint(shooterArrowJoint);
            shooterArrowJoint = null;
        }
    }

    private float calc_x_impulse(int angle) {
        return MathUtils.sinDeg(angle) * -1;
    }


    //berechnet den y impulse anhand der derzeitigen drehung
    private float calc_y_impulse(int angle) {
        return MathUtils.cosDeg(angle);
    }

    private int calcAngle(){
        float angle = userData.fixThatPickedArrowUp.getBody().getAngle() * MathUtils.radiansToDegrees;
        while (angle <= 0){
            angle += 360;
        }
        while (angle > 360) {
            angle -= 360;
        }
        angle = MathUtils.round(angle);
        return (int)angle;
    }


    private static void playCollectSound() {
        if(collectSound == null){
            collectSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_PICK_UP_SOUND);
        }
        collectSound.play(MusicHandler.getSoundVolume());
    }

    private static void playHitSound() {
        if(hitRockSound == null){
            hitRockSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_HIT_ENEMY_EXPLO);
        }
        hitRockSound.play(MusicHandler.getSoundVolume());
    }

}
