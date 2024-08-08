package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author = Keeyzar on 29.03.2016
 */
public class GameActor extends Actor implements EnableAbleActor{
    public Body body;
    public Body getBody() {
        return body;
    }

    public void activateActor(){
        //do nothing
    }

    public void disableActor(){
        //do nothing
    }
}
