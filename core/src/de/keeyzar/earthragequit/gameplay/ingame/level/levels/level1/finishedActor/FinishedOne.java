package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level1.finishedActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class FinishedOne {
    private Array<Actor> finishedActors;

    public FinishedOne() {
        finishedActors = null;
        init();
    }

    private void init() {
        //addAllActors
    }

    public Array<Actor> getFinishedActors() {
        return finishedActors;
    }
}
