package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.IonizedClouds;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.Wasp;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Platform;

/**
 * Kümmert sich um die Kollisionen
 * @author = Keeyzar on 02.03.2016
 */
public class PlayerCollision {
    private final ERQGame game;

    private final Player player;
    private final PlayerMovement playerMovement;
    private final PlayerCalculatedVars playerCalculatedVars;

    private final ObjectMap<Integer, Long> collisionCooldownTime;


    //Cooldowns
    private final long platformCDTimer = 200;
    private final long waspCDTimer = 2;
    private float shieldTimer = 0;
    private float shieldTime = 0;

    public PlayerCollision(Player player, ERQGame game){
        this.game = game;
        this.player = player;
        this.playerMovement = player.getPlayerMovement();
        this.playerCalculatedVars = player.getPlayerCalculatedVars();
        collisionCooldownTime = new ObjectMap<Integer, Long>();
    }

    public void act(float delta) {
        //look if shield needs  to be disabled
        if(shieldTime != 0) {
            shieldTimer += delta;
            if (shieldTimer > shieldTime) {
                shieldTimer = 0;
                shieldTime = 0;
                player.getPlayerCurrentStates().setHasShield(false);
            }
        }
    }

    public void schrottCollision(float fuelAbzug, float shieldTime) {
        if(!player.getPlayerCurrentStates().hasShield()){
            createShield(shieldTime);
            player.getPlayerStatistic().schrottHit();
            player.getPlayerMovement().schrottHit();
            player.getPlayerCalculatedVars().addU_fuel(fuelAbzug);
        }
    }

    public void ionizedCloudCollision() {
        if(!player.getPlayerCurrentStates().hasShield()){
            createShield(IonizedClouds.getShieldTime());
            player.getPlayerStatistic().cloudHit();
            playerMovement.ionizedCloudHit();
        }
    }

    public boolean waspCollision() {
        boolean hit = false;
        if(!player.getPlayerCurrentStates().hasShield()){
            hit = true;
            createShield(waspCDTimer);
            player.getPlayerStatistic().beeHit();
            playerCalculatedVars.addLife(-Wasp.STRENGTH);
        }
        return hit;
    }

    public void createShield(float cdInSeconds) {
        shieldTime = cdInSeconds;
        shieldTimer = 0; //reset shieldTimer
        if(shieldTime != 0) player.getPlayerCurrentStates().setHasShield(true);
    }

    public boolean platformCollision() {
        if(!player.getPlayerMovement().isMoveUp() && !isOnCooldown(EntityVars.PLATFORM)){
            if(playerMovement.playerHitPlatform()){
                collisionCooldownTime.put(EntityVars.PLATFORM, TimeUtils.millis() + platformCDTimer);
                player.getPlayerStatistic().platformHit();
                Platform.playSound();
                return true;
            }

        }
        return false;
    }

    private boolean isOnCooldown(int toCheck){
        Long timeOut = collisionCooldownTime.get(toCheck, -555L);
        if(timeOut != -555L && TimeUtils.millis() - timeOut > 0){
            return false;
        } else if(timeOut != -555L){
            return true;
        }
        return false;
    }

    public float getShieldTime() {
        return shieldTime;
    }
}
