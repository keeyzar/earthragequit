package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.InterpolatedBodySprite;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class Schrott2 extends MovingRadarEntity {
    public static Sound sound;
    public static final int STRENGTH = 1;
    public static final float FUEL_ABZUG = -4f;
    private World world;
    private final ParticleEffectPool particleEffectPool;
    private ParticleEffectPool.PooledEffect particleEffect;

    public InterpolatedBodySprite sprite;
    private static float shieldTime = 1.5f;
    private boolean hit = false;
    private final int index;


    public Schrott2(World world, float x, float y, ParticleEffectPool particleEffectPool) {
        super(EntityVars.SCHROTT_2,
                EntityVars.GROUP_BAD);
        this.world = world;
        this.particleEffectPool = particleEffectPool;

        index = MathUtils.random(1, 7);
        initBodyViaLoader(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY, ERQAssets.BODY_LOADER,
                "planet_"+index, x, y, MathUtils.random(2, 3.5f));
        initBounds(x, y, Box2DUtils.width(body), Box2DUtils.height(body));
        initRadar();
        setRotatingObject(true);
        initMovement(-1, 10);
        init();
        randomHorMovement();
    }


    public void init() {
        sprite = new InterpolatedBodySprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANETS, index));
    }

    public static ParticleEffectPool getPool(){
        return new ParticleEffectPool(ERQAssets.MANAGER.get(AssetVariables.EXPLOSION_DEBRIS, ParticleEffect.class), 2, 5);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(particleEffect != null) {
            particleEffect.setPosition(position.x, position.y);
            particleEffect.update(delta);
        }
    }

    @Override
    public void extraActStuff() {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch, body, position, angle);
        }
        if(particleEffect != null){
            particleEffect.draw(batch);
            if(particleEffect.isComplete()){
                world.destroyBody(body);
                body = null;
                remove();
                particleEffectPool.free(particleEffect);
            }
        }
    }

    @Override
    public void activateActor() {
        super.activateActor();
        body.setAngularVelocity(MathUtils.random(0.5f, 2f));
    }

    @Override
    protected void collisionStuff() {
        if(!hit){
            hit = true;
            userData.getPlayer().getPlayerCollision().schrottCollision(FUEL_ABZUG, shieldTime);
            if(particleEffect == null){
                particleEffect = particleEffectPool.obtain();
            }
            playSound();
            particleEffect.start();
            addAction(Actions.fadeOut(0.4f));
            userData.getPlayer().getPlayerCurrentStates().setShaking(0.5f, 0.3f, 0.009f);
        }
    }

    private static void playSound() {
        if(sound == null){
            sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_EXPLOSION);
        }
        sound.play(MusicHandler.getSoundVolume());
    }

    @Override
    public void disableActor() {
        super.disableActor();
        body.setAngularVelocity(0);
    }

    @Override
    protected void randomHorMovement() {
        body.setLinearVelocity(MathUtils.random(2f, -2f), body.getLinearVelocity().y);
    }

    @Override
    protected void randomVerMovement() {
        body.setLinearVelocity(body.getLinearVelocity().x, MathUtils.random(1f, 2f));
    }

}

