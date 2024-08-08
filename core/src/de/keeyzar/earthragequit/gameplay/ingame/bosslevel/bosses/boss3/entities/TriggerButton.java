package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * This Button is trigger able
 * @author = Keeyzar on 26.11.2016.
 */
public class TriggerButton extends Actor{

    private static Sound enableSound;
    private static Sound disableSound;
    ERQUserDataEntities userData;
    private World world;
    private Body body;
    private Sprite sprite;

    TriggeredFlame triggeredFlame;
    private boolean enabled = false;

    /**
     * @param position 1, 2 or 3
     */
    public TriggerButton(int position, World world, Stage stageToAdd){
        if(position < 1 || position > 3){
            throw new RuntimeException("I SAID ONE, TWO, OR THREE!(please use in "+ getClass().getName() +" as param" +
                    " position only 1 or 2 or 3)");
        }
        this.world = world;

        setWidth(1);
        setHeight(3);
        switch (position){
            case 1:
                setX(5);
                break;
            case 2:
                setX(10);
                break;
            case 3:
                setX(15);
                break;
        }
        setY(1.5f);
        initBody();
        initSprite();
        triggeredFlame = new TriggeredFlame(position, world);
        triggeredFlame.setEnabled(false);
        stageToAdd.addActor(triggeredFlame);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    private void initSprite() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BOSS3_TRIGGERBUTTON);
        sprite.setBounds(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setColor(getColor());
        collision();
    }

    public void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0.5f);
        shape.dispose();

        userData = new ERQUserDataEntities(this, EntityVars.TRIGGER_BUTTON);
        fixture.setUserData(userData);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_SCENERY;
        filterData.maskBits = EntityVars.MASK_SCENERY;
        fixture.setFilterData(filterData);
    }

    public void collision(){
        final boolean collisionWithPlayer = userData.isCollisionWithPlayer();

        if (this.enabled == collisionWithPlayer) return;
        this.enabled = collisionWithPlayer;

        if(collisionWithPlayer){
            triggeredFlame.setEnabled(true);
            playEnableSound();
        } else {
            triggeredFlame.setEnabled(false);
            playDisaableSound();
        }
    }

    public void destroy(){
        if(body != null) {
            world.destroyBody(body);
            body = null;
        }
        triggeredFlame.destroy();
        addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                remove();
            }
        })));
    }


    private static void playEnableSound() {
        if(enableSound == null){
            enableSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_SWITCH_ON);
        }
        enableSound.play(MusicHandler.getSoundVolume());
    }
    private static void playDisaableSound() {
        if(disableSound == null){
            disableSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_SWITCH_OFF);
        }
        disableSound.play(MusicHandler.getSoundVolume());
    }

}
