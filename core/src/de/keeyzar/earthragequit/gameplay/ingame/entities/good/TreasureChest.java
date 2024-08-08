package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.achievements.all.Achievement;
import de.keeyzar.earthragequit.achievements.all.unlockable_achievs.UpgradeForFreeAchiev;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.NonMovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class TreasureChest extends NonMovingRadarEntity {
    public final World world;

    private Box2DSprite sprite;
    private static Sound sound;
    public static final float _WIDTH = 1.5f;
    public static final float _HEIGHT = 1f;
    private boolean opened = false;
    private ParticleEffect particleEffect;
    private boolean finish = false;
    private boolean spawned = false;
    private boolean coinRain = false;
    private int coinCounter = 0;
    private float timer = 0;
    private Player player;

    public TreasureChest(World world, float x, float y) {
        super(EntityVars.TREASURE_CHEST, EntityVars.GROUP_SPECIAL);
        this.world = world;
        initBounds(x, y, _WIDTH, _HEIGHT);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
        if(sound == null){
            initSound();
        }
        particleEffect =  new ParticleEffect(ERQAssets.MANAGER.get(AssetVariables.TREASURE_CHEST_SPARKLE, ParticleEffect.class));
        particleEffect.setPosition(x, y);
    }

    public void init() {
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.TREASURE_CHEST));
    }

    @Override
    protected void collisionStuff() {
        if(!opened) {
            opened = true;
            userData.setCollisionWithPlayer(false);
            TreasureChest.playSound();
            particleEffect.start();
            addAction(Actions.fadeOut(0.3f));
            player = userData.getPlayer();
        }

    }

    @Override
    public void extraActStuff() {
        particleEffect.update(Gdx.graphics.getDeltaTime());
        if(opened && body != null && !spawned){
            spawnItem();
            spawned = true;
        }
        if(!coinRain && finish){
            world.destroyBody(body);
            body = null;
            remove();
        }
    }

    @Override
    public void actWhetherOrNotEnabled(float delta) {
        if(coinRain){
            timer += delta;
            if(coinCounter < 50 && timer > 0.01f) {
                timer = 0;
                coinCounter++;
                if (player.body != null) {
                    getStage().addActor(new Coin_Spawnable(world, 1, getRandomXY(true, player.body),
                            getRandomXY(false, player.body), player.body));
                }
            }
            if(coinCounter >= 50){
                coinRain = false;
                world.destroyBody(body);
                body = null;
                remove();
            }
        }
    }

    private void spawnItem() {
        Achievement achievement;
        switch (MathUtils.random(0, 2)){
            case 0:
                achievement = player.getGame().getAchievementVerwalter().getAchievementMap().get(AVars.SKIN_UNLOCK_1);
                if(!achievement.isFinished()){
                    achievement.checkConditionsAndApplyIfTrue();
                    break;
                }
            case 1:
                achievement = player.getGame().getAchievementVerwalter().getAchievementMap().get(AVars.SKILLPLACE_4);
                if(!achievement.isFinished()){
                    achievement.checkConditionsAndApplyIfTrue();
                    break;
                }
            case 2:
                coinRain = true;
                new UpgradeForFreeAchiev(player.getGame()).checkConditionsAndApplyIfTrue();
                break;
        }
    }

    public void destroy(){
        addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                finish = true;
            }
        })));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()) {
            if (body == null) return;
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch, body);
            if (opened) {
                particleEffect.draw(batch);
                if (particleEffect.isComplete()) {
                    finish = true;
                }
            }
        }
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

    public static void playSound(){
        sound.play(MusicHandler.getSoundVolume());
    }


    public static void initSound() {
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_PICK_UP_SOUND);
    }

}
