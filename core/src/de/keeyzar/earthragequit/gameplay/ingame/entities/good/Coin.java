package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarMagneticEntity;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Coin extends MovingRadarMagneticEntity {
    public final World world;
    public final int value;

    private Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private Sprite sprite;
    private float stateTime;
    private static Sound sound;
    public static final float _WIDTH = 0.6f;
    public static final float _HEIGHT = 0.6f;

    public Coin(World world, int value, float x, float y) {
        super(EntityVars.COIN, EntityVars.GROUP_GOOD);
        this.value = value;
        this.world = world;
        initBounds(x, y, _WIDTH, _HEIGHT);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
        Color color;
        if(value < 2){
            color = Color.WHITE.cpy();
        } else if(value < 4){
            color = Color.LIME.cpy();
        } else if(value < 6){
            color = Color.FIREBRICK.cpy();
        } else if(value < 8){
            color = Color.PURPLE.cpy();
        } else {
            color = Color.ROYAL;
        }
        sprite.setColor(color);
        if(sound == null){
            initSound();
        }
    }

    public void init() {
        sprite = new Sprite();
        sprite.setOrigin(getWidth() / 2 + getWidth() / 4, getHeight() + getHeight() / 4);
        sprite.setSize(getWidth() + getWidth() / 4, getHeight() + getHeight() / 4);
        stateTime = 0f;

        TextureRegion[] anim = new TextureRegion[6];
        for (int i = 0; i < anim.length; i++){
            anim[i] = ERQAssets.T_ATLAS_GAME.findRegion(TextureAtlasVariables.COIN, i+1);
        }
        animation = new Animation<TextureRegion>(0.05f, anim);

        stateTime += MathUtils.random(0, 1);
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setBounds(getX() - getWidth() / 2 - getWidth() / 8,
                getY() - getHeight()/2 - getHeight() / 8,
                getWidth() + getWidth() / 4,
                getHeight() + getHeight() /4);
    }

    @Override
    protected void collisionStuff() {
        if(userData.getPlayer() == null) return;
        userData.getPlayer().addCoin(value);
        world.destroyBody(body);
        body = null;
        remove();
        userData.setCollisionWithPlayer(false);
        Coin.playCoinSound();
    }

    @Override
    public void extraActStuff() {
        setPosition(body.getPosition().x, body.getPosition().y);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setBounds(getX() - getWidth() / 2 - getWidth() / 8,
                getY() - getHeight()/2 - getHeight() / 8,
                getWidth() + getWidth() / 4,
                getHeight() + getHeight() /4);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()) {
            if (body == null) return;
            sprite.setAlpha(parentAlpha);
            sprite.draw(batch);
        }
    }

    public static void playCoinSound(){
        sound.play(MusicHandler.getSoundVolume());
    }


    public static void initSound() {
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_COIN);
    }
}
