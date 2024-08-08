package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.PlayerCalculatedVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.PlayerMovement;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class RotatingMovement implements PlayerMove {
    private Player player;
    private final PlayerMovement pm;
    private PlayerCalculatedVars pcv;

    public RotatingMovement(Player player, PlayerMovement pm, PlayerCalculatedVars pcv){
        this.pm = pm;
        this.pcv = pcv;
        this.player = player;
    }

    private float calc_XImpulse;
    private float calc_YImpulse;
    private float gas;
    private void moveUp() {
        pm.moveUp = true;
        if(pcv.getU_speed_fuel() > 0 && pcv.getLife() > 0){
            pm.calcAngle();
            calc_YImpulse = calc_y_impulse();
            calc_XImpulse = calc_x_impulse();
            gas = (Math.abs(calc_XImpulse) + Math.abs(calc_YImpulse)) / 10;
            player.getBody().applyLinearImpulse(new Vector2(calc_XImpulse, calc_YImpulse), player.getBody().getWorldCenter(), true);
            pcv.addU_fuel(-gas);
        } else {
            pm.endMusic();
        }
    }


    private Vector2 currVel_xImpulse = new Vector2();
    private float desiredVel_xImpulse;
    private float velChange_xImpulse;
    private float xImpulse;
    private float mulX;
    boolean wantsToLeft = false;
    //berechnet den x impulse anhand der derzeitigen Drehung
    private float calc_x_impulse() {
        currVel_xImpulse = player.getBody().getLinearVelocity();
        desiredVel_xImpulse = pcv.getU_speed_max();

        if(pm.calculatedAngle < 180){
            wantsToLeft = true;
            desiredVel_xImpulse = Math.max(currVel_xImpulse.x - pcv.getU_speed_power(), -desiredVel_xImpulse);
        } else {
            wantsToLeft = false;
            desiredVel_xImpulse = Math.min(currVel_xImpulse.x + pcv.getU_speed_power(), desiredVel_xImpulse);
        }

        velChange_xImpulse = desiredVel_xImpulse - currVel_xImpulse.x;
        xImpulse = player.getBody().getMass() * velChange_xImpulse;
        mulX = Math.abs(MathUtils.sinDeg(pm.calculatedAngle));
        if(wantsToLeft && currVel_xImpulse.x > 0){
            mulX *= 3;
        } else if(!wantsToLeft && currVel_xImpulse.x < 0){
            mulX *= 3;
        }

        return 4 * xImpulse * mulX * Gdx.graphics.getDeltaTime();
    }



    private Vector2 currVel_yImpulse = new Vector2();
    private float desiredVel_yImpulse;
    private float velChange_yImpulse;
    private float yImpulse;
    private float mulY;
    //berechnet den y impulse anhand der derzeitigen drehung
    private float calc_y_impulse() {
        //wenn er z.B. auf einer Platform oder sonstiges war, und dadurch
        //noch einen Boost hatte, dann muss hier gebremst werden!
        if(pm.isBoosting()){
            return 0;
        }
        if(pm.calculatedAngle > 90 && pm.calculatedAngle < 270){
            if(pcv.getU_speed_max() < Math.abs(player.getBody().getLinearVelocity().y) || player.getPlayerCurrentStates().isTouchingGround())
            return 0;
        }
        currVel_yImpulse = player.getBody().getLinearVelocity();

        desiredVel_yImpulse = pcv.getU_speed_max();
        desiredVel_yImpulse = Math.min(currVel_yImpulse.y + pcv.getU_speed_power(), desiredVel_yImpulse);

        velChange_yImpulse = desiredVel_yImpulse - currVel_yImpulse.y;
        yImpulse = player.getBody().getMass() * velChange_yImpulse;

        mulY = MathUtils.cosDeg(pm.calculatedAngle);
        if(mulY > 0){
            mulY = Math.max(mulY, 0.4f);
        } else {
            mulY /= 3;
        }

        return (10 * yImpulse) * mulY * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void move(Vector2 dir) {
        moveUp();
        final float v = (dir.angle() - 90) * MathUtils.degRad;
        Body body = player.getBody();
        float nextAngle = body.getAngle() + body.getAngularVelocity() / 10;
        float totalRotation = v - nextAngle;
        while ( totalRotation < -180 * MathUtils.degRad ) totalRotation += 360 * MathUtils.degRad;
        while ( totalRotation >  180 * MathUtils.degRad ) totalRotation -= 360 * MathUtils.degRad;
        float desiredAngularVelocity = totalRotation * 10;
        float change = 50 * MathUtils.degRad; //allow 1 degree rotation per time step
        desiredAngularVelocity = Math.min( change, Math.max(-change, desiredAngularVelocity));
        float impulse = body.getInertia() * desiredAngularVelocity;
        body.applyAngularImpulse(impulse, true);
    }

    @Override
    public void doesNotMove() {
        player.getBody().setAngularVelocity(player.getBody().getAngularVelocity() * 0.95f);
    }
}
