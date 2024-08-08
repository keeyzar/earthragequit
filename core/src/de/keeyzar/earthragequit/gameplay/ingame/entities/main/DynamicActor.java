package de.keeyzar.earthragequit.gameplay.ingame.entities.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * A Dynamic Actor needs Interpolation!!
 * @author = Keeyzar on 10.03.2016
 */
public abstract class DynamicActor extends Actor implements Interpolateable{
    public Vector2 position;
    public Vector2 oldPosition;
    public float angle;
    public float oldAngle;
    public Body body;

    public DynamicActor(){
        position = new Vector2();
        oldPosition = new Vector2();
        angle = 0f;
        oldAngle = 0f;
    }

    public abstract void initBody();

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public Vector2 getOldPosition() {
        return oldPosition;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getAngle() {
        return angle;
    }

    @Override
    public float getOldAngle() {
        return oldAngle;
    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void setOldAngle(float oldAngle) {
        this.oldAngle = oldAngle;
    }
}
