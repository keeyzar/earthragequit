package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.MovingRadarEntity;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * Entity, which follows the play.
 * @author = Keeyzar on 19.04.2016
 */
public class FollowPlayer extends MovingRadarEntity {


    private World world;
    private final Player player;
    private float speedToFollow;

    public FollowPlayer(World world, Player player, float x, float y, float width, float height, float speedToFollow) {
        super(EntityVars.FOLLOW_PLAYER, EntityVars.GROUP_BAD);
        this.world = world;
        this.player = player;
        initBounds(x, y, width, height);
        initBodyAsBox(world, EntityVars.CATEGORY_ENTITY, EntityVars.MASK_ENTITY);
        initRadar();
        initMovement(0.1f, -1);
        this.speedToFollow = speedToFollow;
    }

    /**
     * inits a FollowPlayer entity, with the width 1.5f, and height 1.5f
     */
    public FollowPlayer(World world, Player player, float x, float y){
        this(world, player, x, y, 1.5f, 1.5f, 3);
    }

    @Override
    protected void collisionStuff() {
        userData.getPlayer().getPlayerCollision().createShield(2);
        userData.getPlayer().getPlayerCalculatedVars().addLife(-1);
        removeNow();
    }


    Vector2 movement = new Vector2();
    @Override
    protected void randomHorMovement() {
        movement.set(player.position);
        movement.sub(body.getPosition()).nor().scl(speedToFollow);
        body.setLinearVelocity(movement);
    }

    private void removeNow() {
        world.destroyBody(body);
        body = null;
        remove();
    }
}
