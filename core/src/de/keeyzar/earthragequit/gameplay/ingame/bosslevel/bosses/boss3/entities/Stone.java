package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * @author = Keeyzar on 26.11.2016.
 */
public class Stone extends BossEnemy {
    private static Sound rejectSound;
    private final ERQGame game;
    private World world;

    private Sprite sprite;
    private ERQUserDataEntities userData;
    private boolean finished = false;

    public Stone(ERQGame game, World world, Player player) {
        this.game = game;
        this.world = world;
        setBounds(2, 11, 3, 3);
        sprite = new Box2DSprite(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STONE_BOSS_3));
        sprite.setBounds(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        sprite.setOriginCenter();
        initBody();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(userData != null && userData.isCollisionWithPlayer()){
            Player player = userData.getPlayer();
            if(player.getUserData().hasKey(EntityVars.KEY_LEV_ONE)){
                player.getUserData().removeKey(EntityVars.KEY_LEV_ONE);
                openedStone();
            } else {
                playRejectSound();
            }
        }
    }

    private void openedStone() {
        if(body != null) {
            world.destroyBody(body);
        }
        body = null;
        userData = null;
        finished = true;
    }

    @Override
    public Sprite getCurrentSprite() {
        return sprite;
    }

    @Override
    public int getLife() {
        return 0;
    }

    @Override
    public int getMaxLife() {
        return 0;
    }

    @Override
    public void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(getX(), getY()));
        body = world.createBody(bodyDef);
        body.setAwake(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth() / 2);
        Fixture fixture = body.createFixture(shape, 0);
        shape.dispose();

        userData = new ERQUserDataEntities(this, EntityVars.BOSS_LOCKED_CIRCLE);
        fixture.setUserData(userData);
        userData.setShouldContactWithPlayer(true);


        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_ENTITY;
        filterData.maskBits = EntityVars.MASK_ENTITY;
        fixture.setFilterData(filterData);
    }

    public boolean isOver() {
        return finished;
    }

    private static void playRejectSound() {
        if(rejectSound == null){
            rejectSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_REJECT);
        }
        rejectSound.play(MusicHandler.getSoundVolume());
    }
}
