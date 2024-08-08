package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.NonMovingRadarEntity;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class Fuel extends NonMovingRadarEntity{
    private static Sound sound;

    private final World world;
    private Box2DSprite sprite;
    public Fuel(World world, float x, float y) {
        super(EntityVars.FUEL, EntityVars.GROUP_GOOD);
        this.world = world;
        initBounds(x, y, 1, 1);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
    }

    public void init() {
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.FUEL));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            sprite.setAlpha(parentAlpha);
            sprite.draw(batch, body);
        }
    }

    @Override
    protected void collisionStuff() {
        userData.getPlayer().getPlayerCalculatedVars().addU_fuel(userData.getPlayer().getPlayerCalculatedVars().getFuelTankValue());
        userData.setCollisionWithPlayer(false);
        world.destroyBody(body);
        body = null;
        remove();
        Fuel.playSound();
    }

    public static void playSound(){
        if(sound == null){
            sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_OIL_CATCHED);
        }
        sound.play(MusicHandler.getSoundVolume());
    }

}
