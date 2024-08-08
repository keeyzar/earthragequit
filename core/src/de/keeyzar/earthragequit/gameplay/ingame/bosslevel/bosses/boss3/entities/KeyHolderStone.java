package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.HitableByArrow;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.KeyEntity;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 12.01.2017.
 */
public class KeyHolderStone extends GameActor implements HitableByArrow {
    private World world;
    private Stage stage;

    private Sprite sprite;
    private int hitCounter = 0;
    private boolean gotHit = false;

    private ERQUserDataEntities userData;
    private HitCallback hitCallback;

    ParticleEffect particleEffect;
    private boolean particleEffectStart = false;
    private boolean triggeredToDestroy = false;

    public KeyHolderStone(World world, Stage stage, HitCallback hitCallback){
        this.world = world;
        this.stage = stage;
        this.hitCallback = hitCallback;

        setBounds(18, 11, 3, 3);

        initBody(EntityVars.KEY_HOLDER_STONE);
        initSprite();

        hitCounter = 0;

        particleEffect = ERQAssets.MANAGER.get(AssetVariables.EXPLOSION_DEBRIS, ParticleEffect.class);
        particleEffect.setPosition(getX(), getY());
        particleEffect.start();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setRotation(getRotation());
        sprite.setBounds(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        sprite.setOriginCenter();
        if(particleEffectStart) {
            particleEffect.setPosition(getX(), getY());
            particleEffect.update(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            gotHitByArrow();
        }

        if(gotHit){
            hitCallback.gotHit();
            gotHit = false;
        }


        if(hitCounter >=  3 && !triggeredToDestroy){
            triggeredToDestroy = true;
            spawnKey();
            destroy();
        }
}

    private void spawnKey() {
        KeyEntity actor = new KeyEntity(world, EntityVars.KEY_LEV_ONE, getX(), getY());
        actor.userData.setEnabled(true);
        stage.addActor(actor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
        if(particleEffectStart) {
            particleEffect.draw(batch);
        }
    }

    private void initSprite() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.KEYHOLDER_STONE);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX(), getY());
        sprite.setOriginCenter();

        addAction(Actions.forever(Actions.parallel(Actions.rotateBy(360, 6), Actions.sequence(
                Actions.sizeBy(1.2f, 1.2f, 2), Actions.sizeBy(-1.2f, -1.2f, 1)
        ))));
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
        userData.setEnabled(false);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
    }



    @Override
    public void gotHitByArrow() {
        hitCounter++;
        gotHit = true;
        addAction(Actions.repeat(10,
                Actions.sequence(Actions.moveBy(-0.05f, -0.05f, 0.02f), Actions.moveBy(0.05f, 0.05f, 0.02f))));

        addAction(Actions.sizeBy(-0.3f, -0.3f, 0.5f));
    }

    private void destroy() {
        if(body != null) {
            world.destroyBody(body);
            body = null;
        }

        addAction(Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                particleEffectStart = true;
            }
        }), Actions.fadeOut(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                remove();
            }
        })));

    }

    public interface HitCallback{
        void gotHit();
    }
}
