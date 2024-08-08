package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarMagneticEntity;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Moneybag extends MovingRadarMagneticEntity {
    public final World world;
    public final int value;

    private Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private Sprite sprite;
    private float stateTime;
    public static final float _WIDTH = 1.3f;
    public static final float _HEIGHT = 1.4f;
    private boolean catched = false;
    private int coinSpawnCounter = 0;

    public Moneybag(World world, int value, float x, float y) {
        super(EntityVars.MONEYBAG, EntityVars.GROUP_GOOD);
        this.value = value;
        this.world = world;
        initBounds(x, y, _WIDTH, _HEIGHT);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
    }

    public void init() {
        sprite = new Sprite();
        sprite.setOrigin(getWidth() / 2 + getWidth() / 4, getHeight() + getHeight() / 4);
        sprite.setSize(getWidth() + getWidth() / 4, getHeight() + getHeight() / 4);
        stateTime = 0f;

        TextureRegion[] anim = new TextureRegion[1];
//        for (int i = 0; i < anim.length; i++){
//            anim[i] = ERQAssets.T_ATLAS_GAME.findRegion(TextureAtlasVariables.MONEYBAG, i+1);
//        }
        anim[0] = ERQAssets.T_ATLAS_GAME.findRegion(TextureAtlasVariables.MONEYBAG);
        animation = new Animation<TextureRegion>(0.05f, anim);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

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
        if(!catched) {
            catched = true;
            addAction(Actions.fadeOut(0));
        }

        userData.setCollisionWithPlayer(false);
    }


    private float getRandomXY(boolean createX, Body playerBody) {
        Vector2 actualPos = playerBody.getPosition();
        if(createX){
            if(playerBody.getLinearVelocity().x > 0) {
                return MathUtils.random(actualPos.x + 2, actualPos.x + 6);
            } else {
                return MathUtils.random(actualPos.x -6, actualPos.x - 2);
            }
        } else {
            if(playerBody.getLinearVelocity().y > 0){
                return MathUtils.random(actualPos.y + 2, actualPos.y + 6);
            } else {
                return MathUtils.random(actualPos.y - 6, actualPos.y - 2);
            }
        }
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

        if(catched && coinSpawnCounter < 5) {
            coinSpawnCounter++;
            if(userData.getPlayer() != null) {
                getStage().addActor(new Coin_Spawnable(world, value, getRandomXY(true, userData.getPlayer().getBody()),
                        getRandomXY(false, userData.getPlayer().getBody()), userData.getPlayer().getBody()));
            }
        }
        if(coinSpawnCounter >=5){
            remove();
            world.destroyBody(body);
            body = null;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()) {
            if (body == null) return;
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch);
        }
    }
}
