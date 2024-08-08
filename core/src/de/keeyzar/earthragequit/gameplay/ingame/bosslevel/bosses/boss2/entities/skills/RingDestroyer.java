package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 * @author = Keeyzar on 01.04.2016
 */
public class RingDestroyer extends Actor {
    private final World world;
    private final Player player;
    private FinishListener listener;
    Body body;
    ERQUserDataEntities userData;
    Box2DSprite sprite;
    boolean isActive = false;
    ParticleEffect particleEffect;
    Sound sound;

    public RingDestroyer(World world, Player player, FinishListener listener) {
        this.world = world;
        this.player = player;
        this.listener = listener;
        initBody(player.getX(), player.getY());
        particleEffect = new ParticleEffect((ParticleEffect) ERQAssets.MANAGER.get(AssetVariables.RING_EXPLO));
        particleEffect.scaleEffect(0.02f);
        particleEffect.setPosition(body.getPosition().x, body.getPosition().y);
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_EXPLOSION);
    }

    private void initBody(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.setAwake(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(1f);
        Fixture fixture = body.createFixture(shape, 0);
        shape.dispose();

        userData = new ERQUserDataEntities(this, EntityVars.RING_DESTROYER);
        fixture.setUserData(userData);

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_ENTITY;
        filterData.maskBits = EntityVars.MASK_ENTITY;
        fixture.setFilterData(filterData);


        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.CIRCLE_DESTROYER));
        sprite.setAdjustSize(false);
        sprite.setSize(0.1f, 0.1f);
        isActive = false;
        resetMoves();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isActive){
            if(userData.isCollisionWithPlayer()){
                playerHit();
            }
        }
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setSize(getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(body != null) {
            sprite.draw(batch);
        }
        particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    private void playerHit() {
        if(!player.getPlayerCurrentStates().hasShield()) {
            player.getPlayerCalculatedVars().addLife(-1);
            player.getPlayerCollision().createShield(0.5f);
        }
    }

    private void resetMoves(){
        setSize(0.1f, 0.1f);
        float desWidth = Box2DUtils.width(body);
        float desHeight = Box2DUtils.height(body);
        desWidth *= 1.25f;
        desHeight *= 1.25f;
        addAction(new SequenceAction(Actions.sizeTo(desWidth, desHeight, 0.8f), Actions.run(new Runnable() {
            @Override
            public void run() {
                startExplosion();
            }
        })));
    }

    private void startExplosion() {
        clearActions();
        SequenceAction sequence = Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                isActive = true;
                sound.play(MusicHandler.getSoundVolume());
                particleEffect.start();
            }
        }), Actions.delay(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                isActive = false;
                finish();
            }
        }));
        addAction(sequence);
    }

    private void finish() {
        world.destroyBody(body);
        remove();
        listener.finished();
    }

}
