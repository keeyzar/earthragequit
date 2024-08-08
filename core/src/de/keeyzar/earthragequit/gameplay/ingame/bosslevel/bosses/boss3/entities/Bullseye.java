package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.HitableByArrow;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.Boss3_Spawning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 11.01.2017.
 */
public class Bullseye extends GameActor implements HitableByArrow {
    private ERQUserDataEntities userData;
    private World world;
    private Boss3_Spawning boss3_spawning;
    private Sprite sprite;
    private int hitCounter = 0;
    private Sound correctHitSound;
    private boolean init = false;


    public Bullseye(World world, Boss3_Spawning boss3_spawning){

        this.world = world;
        this.boss3_spawning = boss3_spawning;

        setWidth(1f);
        setHeight(1.6f);
        setY(5f);
        setX(18);
        initBody(EntityVars.BULLSEYE);
        initSprite();

        changeColor();
        init = true;
        hitCounter = 0;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX() - getWidth(), getY() - getHeight() / 2);
        sprite.setColor(getColor());
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            changeColor();
        }
    }

    private void initSprite() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BOSS3_BULLSEYE);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX() - getWidth(), getY() - getHeight() / 2);
        sprite.setOriginCenter();
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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }


    public void changeColor() {
        //Random Color!
        hitCounter++;
        if(init) {
            addAction(
                    Actions.repeat(5, Actions.sequence(
                            Actions.moveBy(0.05f, 0.05f, 0.02f),
                            Actions.moveBy(-0.05f, -0.05f, 0.02f)
                    ))
            );
            correctHit();
        }
        if(hitCounter >= 3){
            boss3_spawning.firstPhaseFinished();
            destroy();
        }
        setColor(MathUtils.random(0, 1),
                MathUtils.random(0, 1),
                MathUtils.random(0, 1),
                1);

        if(getColor().equals(Color.WHITE)){
            setColor(Color.BLACK.cpy());
        }
        sprite.setColor(getColor());
    }

    public void destroy(){
        if(body != null) {
            world.destroyBody(body);
            body = null;
        }
        addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                remove();
            }
        })));
    }

    private void correctHit() {
        if(correctHitSound == null){
            correctHitSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_UNLOCK);
        }
        correctHitSound.play(MusicHandler.getSoundVolume());
    }


    @Override
    public void disableActor() {
        //do Nothing
    }

    @Override
    public void activateActor() {
        //do Nothing
    }
}
