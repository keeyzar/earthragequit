package de.keeyzar.earthragequit.gameplay.ingame.entities.main;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author = Keeyzar on 28.02.2017.
 */
public interface Interpolateable {
    Vector2 getOldPosition();
    Vector2 getPosition();
    float getAngle();
    float getOldAngle();
    void setAngle(float angle);
    void setOldAngle(float angle);

    Body getBody();

}
