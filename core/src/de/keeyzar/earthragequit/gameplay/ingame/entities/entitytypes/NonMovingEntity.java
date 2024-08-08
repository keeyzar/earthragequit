package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.tools.BodyEditorLoader;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;

/**
 * If you want to use do the following:
 *
 * you can init the body on two possible ways:
 * 1. initBounds()
 * 2. initBodyAsBox()
 *
 * OR
 * 1. initBodyViaBodyLoader()
 * 2. initBounds()
 *
 *
 * @author = Keeyzar on 29.03.2016
 */
public abstract class NonMovingEntity extends GameActor {
    protected final int entityId;
    public ERQUserDataEntities userData;


    /**
     * sets the entity Id(from EntityVars.__)
     * @param entityId
     */
    public NonMovingEntity(int entityId){
        this.entityId = entityId;
    }

    /**
     * if you use this method, call initBounds after this one
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
        body.getFixtureList().first().setUserData(userData);
    }

    /**
     * if you use this method, call initBounds before this one
     * @param world
     * @param category
     * @param mask
     */
    public void initBodyAsBox(World world, short category, short mask) {
        this.initBodyAsBox(world, category, mask, false);
    }

    /**
     * Whether or not should be moved top right by half of the width and height
     * @param world
     * @param category
     * @param mask
     * @param transferPosition if yes, moves body half width half height
     */
    public void initBodyAsBox(World world, short category, short mask, boolean transferPosition){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        if(transferPosition){
            bodyDef.position.set(new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2));
        } else {
            bodyDef.position.set(new Vector2(getX(), getY()));
        }
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

    public void initBodyAsCircle(World world, short category, short mask){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        body = world.createBody(bodyDef);
        body.setAwake(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth() / 2);
        Fixture fixture = body.createFixture(shape, 0);
        shape.dispose();

        userData = new ERQUserDataEntities(this, entityId);
        fixture.setUserData(userData);

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = category;
        filterData.maskBits = mask;
        fixture.setFilterData(filterData);
    }


    public void initBounds(float _x, float _y, float _width, float _height){
        setBounds(_x, _y, _width, _height);
    }

    @Override
    public void act(float delta) {
        if(userData.isEnabled() && body != null){
            super.act(delta);
            extraActStuff();
            if(userData.isCollisionWithPlayer()){
                collisionStuff();
            }
        }
        if(body != null) {
            actWhetherOrNotEnabled(delta);
        }
    }

    /**
     * is called, when the entity is colliding with the player
     */
    protected abstract void collisionStuff();

    /**
     * can be overwritten, is called directly after super.actAndDraw and before
     * collisionStuff (is called in actAndDraw(float delta)
     */
    public void extraActStuff(){

    }

    /**
     * this method is called all the time, even if it's not in the frustum culling, please be sparse with it!
     */
    public void actWhetherOrNotEnabled(float delta){

    }

}
