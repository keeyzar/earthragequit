package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author = Keeyzar on 25.03.2016
 */
public class MagneticComponent {
    static Vector2 vec = new Vector2();
    public static float applyForce(Body body, Body whereToMove, float currentSpeed){
        if(body != null) {
            vec.set(whereToMove.getPosition());
            vec.sub(body.getPosition()).nor();
            currentSpeed += 3f;
            body.setLinearVelocity(vec.scl(currentSpeed));
            return 3f;
        }
        return 0;
    }

}
