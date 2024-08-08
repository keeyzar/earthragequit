package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.IonizedClouds;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.movement.PlayerMove;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.movement.RotatingMovement;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * Kümmert sich um jedwege bewegung was den Player angeht (u.A. Platform berührungen etc)
 * @author = Keeyzar on 02.03.2016
 */
public class PlayerMovement {
    private Player player;
    private PlayerCalculatedVars playerCalculatedVars;
    public boolean moveUp = false;
    private PlayerMove playerMove;
    public int calculatedAngle;
    private float disabledMovementTimerTime = 0;
    private float disabledMovementTimer = 0;
    private boolean movementDisabled = false;
    private long currentSoundEffect = -1;
    private Sound rocketSound;
    private Vector2 impulse;


    PlayerMovement(Player player){
        impulse = new Vector2();
        this.player = player;
        this.playerCalculatedVars = player.getPlayerCalculatedVars();
        playerMove = new RotatingMovement(player, this, playerCalculatedVars);
        rocketSound = ERQAssets.MANAGER.get(AssetVariables.ROCKET_MUSIC);
        rocketSound.stop(); // so all current instances are stopping! (preventing multiple rocketSounds.
    }


    public void calcAngle(){
        float unroundCalcAngle = player.getBody().getAngle() * MathUtils.radiansToDegrees;
        while (unroundCalcAngle <= 0){
            unroundCalcAngle += 360;
        }
        while (unroundCalcAngle > 360) {
            unroundCalcAngle -= 360;
        }
        calculatedAngle = MathUtils.round(unroundCalcAngle);
    }


    /////////////INDIREKTE BEWEGUNG////////////////////

    boolean playerHitPlatform() {
        if(player.getBody().getLinearVelocity().y < 0.05){
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            impulse.x = 0;
            impulse.y = playerCalculatedVars.getPlatform_impulse() * player.getBody().getMass();;
            player.getBody().applyLinearImpulse(impulse, player.getBody().getPosition(), true);
            return true;
        }
        return false;
    }

    public void startingBoost(){
        impulse.y = player.getBody().getMass() * playerCalculatedVars.getStarting_boost();
        impulse.x = 0;
        player.getBody().applyLinearImpulse(impulse, player.getBody().getPosition(), true);
        player.getPlayerCollision().createShield(impulse.y / Math.abs(player.body.getWorld().getGravity().y));
        player.getPlayerCurrentStates().setShaking(player.getPlayerCollision().getShieldTime() / 2, 0.1f, 0.002f);
    }





    ///////////MISC/////////////

    public boolean isBoosting(){
        return player.getBody().getLinearVelocity().y > playerCalculatedVars.getU_speed_max();
    }


    void checkIfPlayerIsInWorld() {
        if(player.getBody().getPosition().x < 0){
            pushPlayerBackInWorld(true);
        } else if (player.getBody().getPosition().x + player.getWidth() / 2 > player.getRegularWorld().getWorldWidth()){
            pushPlayerBackInWorld(false);
        }
    }

    private void pushPlayerBackInWorld(boolean fromLeft){
        impulse.x = fromLeft ? 5 : -5;
        player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
        impulse.y = 0;
        player.getBody().applyLinearImpulse(impulse, player.getBody().getPosition(), true);
    }

    /**
     * to know whether the player had pushed gas through this rendering
     */
    void stopMoveUp() {
        moveUp = false;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void act() {
        if(disabledMovementTimerTime != 0){
            disabledMovementTimer += Gdx.graphics.getDeltaTime();
            if(disabledMovementTimer > disabledMovementTimerTime){
                disabledMovementTimerTime = 0;
                disabledMovementTimer = 0;
                movementDisabled = false;
            }
        }
    }


    void ionizedCloudHit() {
        disableMovement(IonizedClouds.getShieldTime() - 1f);
        player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
        impulse.x = 0;
        impulse.y = -10;
        player.body.applyLinearImpulse(impulse, player.body.getPosition(), true);
    }

    private void disableMovement(float v) {
        disabledMovementTimerTime = v;
        movementDisabled = true;
    }

    void schrottHit() {
        player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
        impulse.x = 0;
        impulse.y = -6;
        player.body.applyLinearImpulse(impulse, player.body.getPosition(), true);
        disableMovement(0.5f);
    }

    public void playerMovement(Vector2 dir) {
        playerMove.move(dir);
        if(currentSoundEffect == -1) {
            currentSoundEffect = rocketSound.loop(MusicHandler.getSoundVolume());
        }
    }

    public void playerNoMovement() {
        playerMove.doesNotMove();
        if(currentSoundEffect != -1){
            rocketSound.stop(currentSoundEffect);
            currentSoundEffect = -1;
        }
    }

    public void dispose() {
        endMusic();
    }

    public void endMusic() {
        rocketSound.stop();
    }
}
