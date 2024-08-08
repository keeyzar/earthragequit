package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author = Keeyzar on 29.03.2016
 */
public interface EnableAbleActor{
    Body getBody();

    /**
     * This method is called, when the user is activated(got into FOV)
     * override, if you want to disable activation. (may be for boss levels!
     */
    void activateActor();

    /**
     * This method is called, when the user is disabled(got out of FOV)
     */
    void disableActor();
}
