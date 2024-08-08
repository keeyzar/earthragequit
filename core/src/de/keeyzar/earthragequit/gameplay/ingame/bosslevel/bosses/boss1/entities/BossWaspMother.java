package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class BossWaspMother extends BossEnemy implements HitableByArrow{
    private final World world;
    private OrthographicCamera camera;
    private Vector2 vel;
    private long movementTimer;
    private long minTimer = 1000;

    private Box2DSprite sprite;

    private int life = 5;
    private int maxLife;

    public BossWaspMother(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        setWidth(2.4f);
        setHeight(2.4f);
        setX(5);
        setY(11);
        movementTimer = TimeUtils.millis() + 1000;
        vel = new Vector2();
        initBody();
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BOSSWASP));
        sprite.setSize(getWidth(), getHeight());
        sprite.setOriginCenter();
        maxLife = life;
    }

    public void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        body = world.createBody(bodyDef);
        float density = 0.5f;
        Fixture fixture = body.createFixture(shape, density);
        float friction = 0.5f;
        fixture.setFriction(friction);
        body.resetMassData();
        shape.dispose();

        ERQUserDataEntities userData = new ERQUserDataEntities(this, EntityVars.BOSSWASP);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_ENTITY;
        filterData.maskBits = EntityVars.MASK_ENTITY;
        fixture.setFilterData(filterData);
        body.setLinearVelocity(getRandomSpeedVector(true));
    }

    @Override
    public void act(float delta) {
        move();
        sprite.setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    private void move() {
        //random Movement setting
        if (movementTimer < TimeUtils.millis()) {
            minTimer = life * 500;
            movementTimer = TimeUtils.millis() + MathUtils.random(minTimer, minTimer + 1000);
            getRandomSpeedVector(false);
            body.setLinearVelocity(vel);
        }
        if (body.getPosition().x > camera.position.x + camera.viewportWidth / 2 - getWidth()) {
            body.setLinearVelocity(new Vector2(-Math.abs(body.getLinearVelocity().x), body.getLinearVelocity().y));
        } else if (body.getPosition().x < camera.position.x - camera.viewportWidth / 2) {
            body.setLinearVelocity(new Vector2(Math.abs(body.getLinearVelocity().x), body.getLinearVelocity().y));
        }
        if (body.getPosition().y > camera.position.y + camera.viewportHeight / 2 - getHeight()) {
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, -Math.abs(body.getLinearVelocity().y)));
        } else if (body.getPosition().y < camera.position.y + getHeight()) {
            body.setLinearVelocity((new Vector2(body.getLinearVelocity().x, Math.abs(body.getLinearVelocity().y))));
        }
    }


    private Vector2 getRandomSpeedVector(boolean highVelo) {
        int x1, y1;
        if(!highVelo) {
            x1 = MathUtils.random(10 - Math.min(9, life), 10 - Math.min(life, 9) + 3);
            y1 = MathUtils.random(5 - Math.min(9, life) / 2, 5 - Math.min(9, life) / 2 + 2);
        } else {
            x1 = MathUtils.random(20 - Math.min(19, life), 20);
            y1 = MathUtils.random(5, 7);
        }
        if (!MathUtils.randomBoolean()) {
            x1 *= -1;
        }
        if (!MathUtils.randomBoolean()){
            y1 *= -1;
        }
        vel.x = x1;
        vel.y = y1;
        return vel;
    }

    @Override
    public void gotHitByArrow() {
        life -= 1;
        movementTimer = TimeUtils.millis() + 1000;
        body.setLinearVelocity(getRandomSpeedVector(true));
    }

    @Override
    public int getLife() {
        return life;
    }

    @Override
    public int getMaxLife() {
        return maxLife;
    }

    public Sprite getCurrentSprite() {
        return sprite;
    }
}
