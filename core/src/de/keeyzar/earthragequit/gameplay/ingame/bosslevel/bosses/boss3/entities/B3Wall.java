package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.HitableByArrow;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 12.01.2017.
 */
public class B3Wall extends GameActor implements HitableByArrow {
    private World world;
    private Body body;
    private ERQUserDataEntities erqUserDataEntities;
    private Sprite sprite;
    private ERQUserDataEntities userData;
    private boolean destroy = false;
    private float finalX;

    public B3Wall(World world, float x, float y, float width, float height, boolean left) {
        this.world = world;

        finalX = x;
        if(left){
            setBounds(0 - width / 2, y, width, height);
        } else {
            setBounds(20, y, width, height);
        }

        initSprite();
        initBody();

        addAction(Actions.moveTo(finalX + width / 2, y, 1f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setColor(getColor());
        sprite.setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        if(userData.isCollisionWithPlayer()){
            userData.getPlayer().getBody().setLinearVelocity(userData.getPlayer().getBody().getLinearVelocity().x, -2);
        }

        if(destroy){
            destroy();
        }

    }

    private void initSprite() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.LOCKED_WALL);
        sprite.setBounds(getX()- getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        sprite.setOriginCenter();
    }

    static int test = 0;
    private void initBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(finalX + getWidth() / 2, getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0.5f);
        shape.dispose();

        userData = new ERQUserDataEntities(this, EntityVars.WALL_WITH_HOLE);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
    }

    @Override
    public void gotHitByArrow() {
        //do Nothing, the only purpose is, that the arrow disappears
    }

    public void destroy() {
        if(body != null) {
            world.destroyBody(body);
            body = null;
        }
        destroy = false;
        addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                remove();
            }
        })));
    }

    public void triggerToDestroy(){
        destroy = true;
    }
}
