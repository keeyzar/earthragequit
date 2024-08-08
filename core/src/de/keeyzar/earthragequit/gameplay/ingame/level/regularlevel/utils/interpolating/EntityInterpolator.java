package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating;

import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.Interpolateable;

import java.util.Iterator;

/**
 * @author = Keeyzar on 07.03.2016
 */
public class EntityInterpolator {
    private Array<Interpolateable> dynamicActors;
    public EntityInterpolator(){
        dynamicActors = new Array<Interpolateable>();
    }

    public void addActor(Interpolateable dynamicActor){
        dynamicActor.setOldAngle(dynamicActor.getAngle());

        dynamicActor.getOldPosition().x = dynamicActor.getBody().getPosition().x;
        dynamicActor.getOldPosition().y = dynamicActor.getBody().getPosition().y;
        dynamicActor.getPosition().x = dynamicActor.getBody().getPosition().x;
        dynamicActor.getPosition().y = dynamicActor.getBody().getPosition().y;
        dynamicActors.add(dynamicActor);
    }

    public void calculateInterpolation(float alpha){
        //---- interpolate: currentState*alpha + previousState * ( 1.0 - alpha ); ------------------
        final Iterator<Interpolateable> iterator = dynamicActors.iterator();
        while(iterator.hasNext()){
            Interpolateable dyn = iterator.next();
            if(dyn.getBody() != null) {

                dyn.getPosition().x = dyn.getBody().getPosition().x * alpha + dyn.getOldPosition().x * (1.0f - alpha);
                dyn.getPosition().y = dyn.getBody().getPosition().y * alpha + dyn.getOldPosition().y * (1.0f - alpha);
                dyn.setAngle(dyn.getBody().getAngle() * alpha + dyn.getOldAngle() * (1.0f - alpha));
            } else {
                iterator.remove();
            }
        }
    }

    public void safeOldPosition(){
        for(Interpolateable dyn : dynamicActors){
            if(dyn.getBody() != null) {
                dyn.getOldPosition().x = dyn.getBody().getPosition().x;
                dyn.getOldPosition().y = dyn.getBody().getPosition().y;
                dyn.setOldAngle(dyn.getBody().getAngle());
            }
        }
    }

    public void removeThisActor(Interpolateable interpolateable){
        dynamicActors.removeValue(interpolateable, false);
    }
}
