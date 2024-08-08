package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.NonMovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ERQUserDataPlayer;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 15.04.2016
 */
public class LockedWall extends NonMovingRadarEntity {
    private static Sound rejectSound;
    private static Sound unlockSound;
    boolean appliedCollision = false;
    boolean locked = true;
    private World world;
    private int requiredKey;
    private UnlockListener listener;
    private boolean removed;
    private Box2DSprite sprite;

    public LockedWall(World world, int requiredKey, float width, float height, float x, float y) {
        this(world, requiredKey, width, height, x, y, null);
    }

    public LockedWall(World world, int requiredKey, float width, float height, float x, float y,
                      UnlockListener listener){
        super(EntityVars.LOCKED_WALL, EntityVars.GROUP_BAD);
        this.world = world;
        this.requiredKey = requiredKey;
        this.listener = listener;
        initBounds(x, y, width, height);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY, true);
        initRadar();
        init();
        userData.setShouldContactWithPlayer(true);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch, body);
        }
    }

    private void init() {
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.LOCKED_WALL));
    }

    @Override
    public void extraActStuff() {
        if(appliedCollision && !userData.isCollisionWithPlayer()){
            appliedCollision = false;
        }
        if(removed && !hasActions()){
            removeNow();
        }
    }

    public void gotUnlocked(){
        if(!removed) {
            removed = true;
            locked = false;
            addAction(Actions.fadeOut(0.3f));
        }
    }
    private void removeNow() {
        world.destroyBody(body);
        body = null;
        remove();
    }

    @Override
    protected void collisionStuff() {
        if(!removed) {
            //only search once in userdata from player, at begin contact.
            //so he does not search a million times a second in a Objectmap.. does not change in any way.
            if (!appliedCollision) {
                appliedCollision = true;
                ERQUserDataPlayer userData = this.userData.getPlayer().getUserData();
                locked = !userData.hasKey(requiredKey);

                //first check whether or not the key existed, if yes, remove this key
                if (!locked) {
                    userData.removeKey(requiredKey);
                    playUnlockSound();
                } else {
                    userData.getPlayer().displayText(LANG.format("wall_entity", requiredKey));
                    playRejectSound();
                }
            }
            if (!locked) {
                gotUnlocked();
                if (listener != null) {
                    listener.unlocked();
                }
            }
        }
    }


    private static void playRejectSound() {
        if(rejectSound == null){
            rejectSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_REJECT);
        }
        rejectSound.play(MusicHandler.getSoundVolume());
    }


    private static void playUnlockSound() {
        if(unlockSound == null){
            unlockSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_UNLOCK);
        }
        unlockSound.play(MusicHandler.getSoundVolume());
    }

    public interface UnlockListener{
        /**
         * is called, when this wall got unlocked
         */
        void unlocked();
    }
}
