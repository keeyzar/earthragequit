package de.keeyzar.earthragequit.gameplay.ingame.entities;

import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.EnableAbleActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * Userdata which holds some crucial informations for collision, and if it should be drawn or not etc.
 * @author = Keeyzar on 02.03.2016
 */
public class ERQUserDataEntities {
    private int id;
    private Object userDataHolder;
    private boolean enabled;
    private boolean collisionWithPlayer = false;
    private Player player;
    private boolean shouldContact = false;
    private boolean isCullableActor = false;

    /**
     * creates a userdata, with collision with player false, and should contact false.
     * @param userDataHolder which entity is holding this userdata
     * @param id which id the object has
     */
    public ERQUserDataEntities(Object userDataHolder, int id){
        this.id = id;
        this.userDataHolder = userDataHolder;
        if(userDataHolder instanceof EnableAbleActor){
            isCullableActor = true;
        }
    }

    /**
     *
     * @return The id of this userdata
     */
    public int getId() {
        return id;
    }

    /**
     * reference on the object, from which the userdata is
     * @return reference from the object, which holds the userdata (an entity)
     */
    public Object getUserDataHolder() {
        return userDataHolder;
    }

    /**
     * if the player should be drawn / activated etc. or not
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        if(!isCullableActor) return;
        if(enabled) {
            ((EnableAbleActor) userDataHolder).activateActor();
        }
        else {
            ((EnableAbleActor) userDataHolder).disableActor();
        }
        this.enabled = enabled;
    }

    /**
     * @return if the userdata is enabled or not
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @return true if is colliding with the player (at this moment)
     */
    public boolean isCollisionWithPlayer() {
        return collisionWithPlayer;
    }

    /**
     * @param collisionWithPlayer if is colliding with player (at this moment)
     */
    public void setCollisionWithPlayer(boolean collisionWithPlayer) {
        this.collisionWithPlayer = collisionWithPlayer;
    }

    /**
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player set the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Default is false(fly through)
     * @return wheter or not if the entity should contact / collide with the player
     */
    public boolean shouldCollideWithPlayer() {
        return shouldContact;
    }

    /**
     * set if the player should collide with the player
     * @param shouldContact
     */
    public void setShouldContactWithPlayer(boolean shouldContact) {
        this.shouldContact = shouldContact;
    }

}
