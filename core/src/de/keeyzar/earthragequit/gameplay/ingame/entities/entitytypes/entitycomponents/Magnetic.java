package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author = Keeyzar on 04.03.2016
 */
public interface Magnetic {
    void applyForce(Body whereToMove);
    int getId();
}
