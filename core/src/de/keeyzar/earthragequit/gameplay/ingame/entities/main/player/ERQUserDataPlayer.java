package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.utils.ObjectIntMap;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class ERQUserDataPlayer {
    private int id;
    private Player player;
    //reference on all Keys, that the Player has catched while he played.
    private ObjectIntMap<Integer> keys;


    public ERQUserDataPlayer(Player player){
        this.player = player;
        keys = new ObjectIntMap<Integer>();
    }

    public void addKey(int keyId){
        keys.getAndIncrement(keyId, 0, 1);
    }

    public boolean hasKey(int keyId){
        return keys.get(keyId, 0) != 0;
    }

    public void removeKey(int keyId){
        keys.getAndIncrement(keyId, 0, -1);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTouchesGround(boolean touchesGround) {
        player.getPlayerCurrentStates().setTouchingGround(touchesGround);
    }
}
