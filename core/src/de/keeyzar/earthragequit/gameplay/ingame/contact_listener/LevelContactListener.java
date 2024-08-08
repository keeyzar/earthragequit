package de.keeyzar.earthragequit.gameplay.ingame.contact_listener;

import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 01.03.2016
 */
public class LevelContactListener implements ContactListener {

    private BasicContactListener extractedFunc;
    public LevelContactListener(EntityInterpolator entityInterpolator){
        extractedFunc = new BasicContactListener(new BasicContactListener.ContactListenerHookForNewItems() {
            @Override
            public boolean beginContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
                return false;
            }

            @Override
            public boolean endContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
                return false;
            }

            @Override
            public boolean preSolveHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
                return false;
            }
        });
        extractedFunc.setEntityInterpolator(entityInterpolator);
    }

    @Override
    public void beginContact(Contact contact) {
        extractedFunc.beginContact(contact);

    }

    @Override
    public void endContact(Contact contact) {
        extractedFunc.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        extractedFunc.preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        extractedFunc.postSolve(contact, impulse);
    }
}
