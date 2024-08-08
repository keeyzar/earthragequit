package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.tools.BodyEditorLoader;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;

/**
 * @author = Keeyzar on 29.03.2016
 */
public abstract class MovingEntity extends GameActor {
    protected final int entityId;
    public static float DO_NOT_MOVE = -1;

    private float spawnX;
    private float spawnY;
    public ERQUserDataEntities userData;

    float timeTilHorNew;
    float timeTilVerNew;
    float timerHor;
    float timerVer;
    protected Vector2 origin;

    /**
     * sets the entity Id(from EntityVars.__)
     * @param entityId
     */
    public MovingEntity(int entityId){
        this.entityId = entityId;
        //default zero movement;
        timeTilHorNew = -1;
        timeTilVerNew = -1;
    }

    /**
     * call Setbounds after
     * @param world
     * @param category
     * @param mask
     * @param loader
     * @param bodyName
     * @param x
     * @param y
     * @param scale
     */
    public void initBodyViaLoader(World world, short category, short mask, BodyEditorLoader loader, String bodyName, float x, float y, float scale) {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = category;
        fd.filter.maskBits = mask;

        body = world.createBody(bd);
        body.setAwake(false);
        loader.attachFixture(body, bodyName, fd, scale);
        userData = new ERQUserDataEntities(this, entityId);
        for(int i = 0; i<body.getFixtureList().size; i++){
            body.getFixtureList().get(i).setUserData(userData);
        }
        origin = loader.getOrigin(bodyName, scale);
    }

    /**
     * Call setBounds first
     * @param world
     * @param category
     * @param mask
     */
    public void initBodyAsBox(World world, short category, short mask) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        body = world.createBody(bodyDef);
        body.setAwake(false);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        Fixture fixture = body.createFixture(shape, 0);
        shape.dispose();

        userData = new ERQUserDataEntities(this, entityId);
        fixture.setUserData(userData);

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = category;
        filterData.maskBits = mask;
        fixture.setFilterData(filterData);
    }

    /**
     * in which TimeScales a new RandomVec should be called.
     * if any is -1, no new random movement is generated for this direction
     */
    public void initMovement(float timeTilHorNew, float timeTilVerNew){
        this.timeTilHorNew = timeTilHorNew;
        this.timeTilVerNew = timeTilVerNew;
    }

    public void initBounds(float _x, float _y, float _width, float _height){
        spawnX = _x;
        spawnY = _y;
        setBounds(_x, _y, _width, _height);
    }

    @Override
    public void act(float delta) {
        if(userData.isEnabled() && body != null){
            super.act(delta);
            //+= timer
            horMovement(delta);
            verMovement(delta);
            extraActStuff();
            if(userData.isCollisionWithPlayer()){
                collisionStuff();
            }
        }
    }

    /**
     * When the entity has hit the player, that this method is called
     */
    protected abstract void collisionStuff();

    public void verMovement(float delta) {
        if(timeTilVerNew != -1){
            timerVer+= delta;
            if(timerVer > timeTilVerNew){
                timerVer = 0;
                randomVerMovement();
            }
        }
    }

    /**
     * Is called when the entity is enabled, once every loop.
     * @param delta
     */
    public void horMovement(float delta) {
        if(timeTilHorNew != -1){
            timerHor+= delta;
            if(timerHor > timeTilHorNew){
                timerHor = 0;
                randomHorMovement();
            }
        }
    }

    /**
     * disables any movement
     */
    @Override
    public void disableActor() {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    /**
     * inits randomHorMovement and
     * randomVerMovement
     */
    @Override
    public void activateActor() {
        randomVerMovement();
        randomHorMovement();
        randomAngularVelocity();
    }

    /**
     * inits random angular velocity
     */
    protected void randomAngularVelocity(){

    }

    /**
     * inits random Vertical Movement
     */
    protected void randomVerMovement(){

    }

    /**
     * inits random Horizontal movement
     */
    protected void randomHorMovement(){

    }

    /**
     * can be overwritten
     */
    public void extraActStuff(){

    }

}
