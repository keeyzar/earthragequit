package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.InterpolatedBodySprite;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class Creep extends MovingRadarEntity {
    private static Sound sound;
    public static final int STRENGTH = 2;
    InterpolatedBodySprite sprite;
    Action action;
    Runnable runnable;

    Vector2 ranVec;

    public static final float CD_TIMER = 2f;
    public Creep(World world, float x, float y, final Player player) {
        super(EntityVars.INVISIBLE_CREEP, EntityVars.GROUP_BAD);
        initBounds(x, y, 1.2f, 1.2f);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        initMovement(3f, 3f);
        init();
        runnable = new Runnable() {
            @Override
            public void run() {
                followPlayer(player);
            }
        };
        action = Actions.forever(Actions.delay(1f,  Actions.run(runnable)));
    }

    Vector2 calculationVector = new Vector2();
    private void followPlayer(Player player){
        calculationVector.set(player.getBody().getPosition());
        calculationVector.sub(body.getPosition()).nor();
        body.setLinearVelocity(calculationVector.scl(3));
    }

    public void init() {
        ranVec = new Vector2();
        sprite = new InterpolatedBodySprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.INVISIBLE_CREEP));
    }

    @Override
    public void extraActStuff() {
        //follow player at low speed
        action.act(Gdx.graphics.getDeltaTime());
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
        //but check always for a collision
        if(!userData.getPlayer().getPlayerCurrentStates().hasShield()){
            userData.getPlayer().getPlayerCollision().createShield(Creep.CD_TIMER);
            userData.getPlayer().getPlayerStatistic().hit(EntityVars.INVISIBLE_CREEP);
            userData.getPlayer().getPlayerCalculatedVars().addLife(-Creep.STRENGTH);
            playSound();
        }
    }

    private static void playSound() {
        if(sound == null){
            sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_STRANGE_COLLISION);
        }
        sound.play(MusicHandler.getSoundVolume());
    }
}

