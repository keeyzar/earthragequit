package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.InterpolatedBodySprite;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class Wasp extends MovingRadarEntity {
    public static final int STRENGTH = 1;
    public static Sound sound;
    private InterpolatedBodySprite sprite;

    Vector2 ranVec;
    public Wasp(World world, float x, float y) {
        super(EntityVars.WASP, EntityVars.GROUP_BAD);
        initBounds(x, y, 1.5f, 1.33f);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        initMovement(3f, 2f);
        init();
    }

    public void init() {
        ranVec = new Vector2();
        sprite = new InterpolatedBodySprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.BEE));
    }

    @Override
    public void extraActStuff() {
        //do nothing
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            sprite.setAlpha(parentAlpha);
            sprite.draw(batch, body, position, angle);
        }
    }

    @Override
    protected void randomHorMovement() {
        body.setLinearVelocity(MathUtils.random(-3, 3), body.getLinearVelocity().y);
    }

    @Override
    protected void randomVerMovement() {
        body.setLinearVelocity(body.getLinearVelocity().x, MathUtils.random(-3, 3));
    }

    @Override
    protected void collisionStuff() {
        if(userData.getPlayer().getPlayerCollision().waspCollision()){
            playSound();
        }
    }

    private static void playSound(){
        if(sound == null){
            sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_STRANGE_COLLISION);
        }
        sound.play(MusicHandler.getSoundVolume());
    }
}

