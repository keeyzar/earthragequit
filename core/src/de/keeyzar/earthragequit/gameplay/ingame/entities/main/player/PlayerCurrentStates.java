package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

/**
 * This class holds some vars from the player like
 * if he's touching the ground, or
 * if he's currently megaboosting. its
 * only holding it's value, not more
 * @author = Keeyzar on 23.03.2016
 */
public class PlayerCurrentStates {
    private final Player player;
    private boolean isTouchingGround = false;
    private boolean hasMegaboost = false;
    private boolean hasShield = false;
    private boolean magnetActivated = false;
    private boolean radarActive = false;
    private boolean shaking;
    private float shakingIntensity = 0;
    private float shakingTime = 0;
    private float shakingDecrease = 0;

    public PlayerCurrentStates(Player player){
        this.player = player;
    }

    public boolean isTouchingGround() {
        return isTouchingGround;
    }

    public void setTouchingGround(boolean touchingGround) {
        isTouchingGround = touchingGround;
    }

    public boolean hasMegaboost() {
        return hasMegaboost;
    }

    public void setHasMegaboost(boolean hasMegaboost) {
        this.hasMegaboost = hasMegaboost;
    }

    public boolean hasShield() {
        return hasShield;
    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }

    public void setMagnetActivated(boolean magnetActivated) {
        this.magnetActivated = magnetActivated;
    }

    public boolean isMagnetActivated() {
        return magnetActivated;
    }

    public boolean isRadarActive() {
        return radarActive;
    }

    public void setRadarActive(boolean radarActive) {
        this.radarActive = radarActive;
    }

    public boolean isShaking() {
        return shaking;
    }

    public void setShaking(float time, float shakingIntensity, float shakingDecrease) {
        shakingTime = time;
        shaking = true;
        this.shakingIntensity = shakingIntensity;
    }

    public float getShakingIntensity() {
        return shakingIntensity;
    }

    public void setShakingIntensity(float shakingIntensity) {
        this.shakingIntensity = shakingIntensity;
    }

    public void act(float delta){
        if(shakingTime > 0){
            shakingTime -= delta;
            shakingIntensity -= shakingDecrease;
            if(shakingIntensity < 0){
                shakingIntensity = 0;
            }
        } else if(shaking){
            shaking = false;
            shakingTime = 0;
            shakingIntensity = 0;
        }
    }

    public void setShakingDecrease(float shakingDecrease) {
        this.shakingDecrease = shakingDecrease;
    }
}
