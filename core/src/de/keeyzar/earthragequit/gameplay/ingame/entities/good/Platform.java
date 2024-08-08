package de.keeyzar.earthragequit.gameplay.ingame.entities.good;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
public class Platform extends NonMovingRadarEntity {
    private Box2DSprite sprite;
    private static Sound sound;
    private World world;
    private TreasureChest chest;
    boolean triggerToDestroy = false;

    public Platform(World world, float x, float y, Stage stage) {
        super(EntityVars.PLATFORM, EntityVars.GROUP_NEUTRAL);
        this.world = world;
        initBounds(x, y, 5, 0.5f);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        init();
        if(sound == null){
            Platform.initSound();
        }
        if(MathUtils.random(600) < 3){
            chest = new TreasureChest(world, x, y + getHeight());
            stage.addActor(chest);
        }
    }


    public void init() {
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLATFORM, MathUtils.random(1, 6)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(userData.isEnabled()){
            if(body == null) return;
            sprite.setAlpha(getColor().a * parentAlpha);
            sprite.draw(batch, body);
        }
    }


    @Override
    protected void collisionStuff() {
        if(userData.getPlayer().getPlayerCollision().platformCollision()){
            addAction(Actions.fadeOut(0.4f));
            triggerToDestroy = true;
            if(chest != null){
                chest.destroy();
            }
        }
    }

    boolean playerMovedUp = true;
    boolean playerMoveDirectionChanged = true;
    @Override
    public void extraActStuff() {
        if(playerMovedUp != userData.getPlayer().getPlayerMovement().isMoveUp()){
            playerMoveDirectionChanged = true;
            playerMovedUp = userData.getPlayer().getPlayerMovement().isMoveUp();
        }
        if(playerMoveDirectionChanged && !triggerToDestroy){
            if(playerMovedUp){
              addAction(Actions.alpha(0.4f, 0.2f));
            } else {
                addAction(Actions.alpha(1f, 0.2f));
            }
        }

        if(triggerToDestroy && getColor().a < 0.1f) {
            world.destroyBody(body);
            body = null;
            remove();
        }
        playerMoveDirectionChanged = false;
    }

    private static void initSound() {
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_JUMP);
    }

    public static void playSound(){
        sound.play(MusicHandler.getSoundVolume());
    }

}
