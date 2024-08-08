package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.NonMovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ERQUserDataPlayer;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static de.keeyzar.earthragequit.assets.ERQAssets.*;

/**
 * @author = Keeyzar on 15.04.2016
 */
public class KeyEntity extends NonMovingRadarEntity {
    private static Sound sound;
    private World world;
    private int keyId;
    private Box2DSprite sprite;

    /**
     * If collected adds a key to the player, so he can open "doors"
     * @param keyId (found in entity vars)
     */
    public KeyEntity(World world, int keyId, float x, float y) {
        super(EntityVars.KEY_ENTITY, EntityVars.GROUP_SPECIAL);
        initBounds(x, y, 3, 2);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        this.world = world;
        this.keyId = keyId;
        sprite = new Box2DSprite(T_ATLAS_GAME.createSprite(TextureAtlasVariables.WALL_UNLOCKER));
        sprite.setColor(Color.GREEN);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()) {
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch, body);
        }
    }

    @Override
    public void extraActStuff() {
        sprite.setColor(getColor());
    }

    @Override
    protected void collisionStuff() {
        ERQUserDataPlayer userData = this.userData.getPlayer().getUserData();
        userData.addKey(keyId);
        userData.getPlayer().displayText(LANG.format("key_entity",keyId));
        playSound();
        destroyNow();
    }

    private void destroyNow() {
        remove();
        world.destroyBody(body);
        body = null;
    }


    private static void playSound() {
        if(sound == null){
            sound = MANAGER.get(AssetVariables.SOUND_PICK_UP_SOUND);
        }
        sound.play(MusicHandler.getSoundVolume());
    }
}
