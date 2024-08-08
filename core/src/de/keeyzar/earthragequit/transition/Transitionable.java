package de.keeyzar.earthragequit.transition;

import com.badlogic.gdx.Screen;

/**
 * If you implement this interface, than your screen
 * can be changed smooth.
 * @author = Keeyzar on 21.03.2016
 */
public abstract class Transitionable implements Screen {

    /**
     * What will be drawn, when the Transition between screens is fading open/close
     */
    public abstract void draw();

    /**
     * The Code, that initializes this Screen
     */
    public abstract void init();

    /**
     * actAndDraw is called all the time. Maybe it's intelligent to call "renderBeforeRegularStage" in "draw" method
     * even if you have all your gamelogic in renderBeforeRegularStage.
     * @param delta
     */
    public abstract void act(float delta);


}
