package de.keeyzar.earthragequit.gameplay.ingame.contact_listener;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.Magnetic;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.Interpolateable;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ERQUserDataPlayer;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.ERQMagnetUserData;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.ERQRadarUserData;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Radarable;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling.CullUD;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 11.01.2017.
 */
public class BasicContactListener {

    private ContactListenerHookForNewItems contactListenerHookForNewItems;
    private EntityInterpolator entityInterpolator;

    public BasicContactListener(ContactListenerHookForNewItems contactListenerHookForNewItems){
        this.contactListenerHookForNewItems = contactListenerHookForNewItems;
    }

    public void setEntityInterpolator(EntityInterpolator entityInterpolator) {
        this.entityInterpolator = entityInterpolator;
    }

    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object fixA_UD = fixtureA.getUserData();
        Object fixB_UD = fixtureB.getUserData();

        if(contactListenerHookForNewItems.beginContactHookBeforeEverything(contact, fixtureA, fixtureB, fixA_UD, fixB_UD)){
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof CullUD || fixB_UD != null && fixB_UD instanceof CullUD) {
            culling(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, true);
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof ERQUserDataPlayer || fixB_UD != null && fixB_UD instanceof ERQUserDataPlayer) {
            playerCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, true, false);
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof ERQMagnetUserData || fixB_UD != null && fixB_UD instanceof  ERQMagnetUserData){
            magnetCollision(fixtureA, fixtureB, fixA_UD, fixB_UD);
        }

        if (fixA_UD != null && fixA_UD instanceof ERQRadarUserData || fixB_UD != null && fixB_UD instanceof  ERQRadarUserData){
            radarCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, true);
        }

    }

    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object fixA_UD = fixtureA.getUserData();
        Object fixB_UD = fixtureB.getUserData();

        if(contactListenerHookForNewItems.endContactHookBeforeEverything(contact, fixtureA, fixtureB, fixA_UD, fixB_UD)){
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof CullUD || fixB_UD != null && fixB_UD instanceof CullUD) {
            culling(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, false);
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof ERQUserDataPlayer || fixB_UD != null && fixB_UD instanceof ERQUserDataPlayer) {
            playerCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, false, false);
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof ERQMagnetUserData || fixB_UD != null && fixB_UD instanceof  ERQMagnetUserData){
            //do noddinHill
        }

        if (fixA_UD != null && fixA_UD instanceof ERQRadarUserData || fixB_UD != null && fixB_UD instanceof  ERQRadarUserData){
            radarCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, false);
        }
    }

    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object fixA_UD = fixtureA.getUserData();
        Object fixB_UD = fixtureB.getUserData();

        if(contactListenerHookForNewItems.preSolveHookBeforeEverything(contact, fixtureA, fixtureB, fixA_UD, fixB_UD)){
            return;
        }

        if (fixA_UD != null && fixA_UD instanceof ERQUserDataPlayer || fixB_UD != null && fixB_UD instanceof ERQUserDataPlayer) {
            playerCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, false, true);
            return;
        }
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {
        //still do nothing. :D
    }


    /**
     * for Culling purpose
     */
    private CullUD c_cullUD;
    private ERQUserDataEntities c_entitiyUD;
    private void culling(Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD, Contact contact, boolean begin) {

        if (fixA_UD != null && fixA_UD instanceof CullUD) {
            c_cullUD = (CullUD) fixA_UD;
            c_entitiyUD = (ERQUserDataEntities) fixB_UD;
        } else {
            c_cullUD = (CullUD) fixB_UD;
            c_entitiyUD = (ERQUserDataEntities) fixA_UD;
        }
        if(c_entitiyUD == null){
            return;
        }

        if(begin){
            c_entitiyUD.setPlayer(c_cullUD.getPlayer());
            if(c_entitiyUD.getUserDataHolder() instanceof Interpolateable){
                entityInterpolator.addActor((Interpolateable) c_entitiyUD.getUserDataHolder());
            }
            c_entitiyUD.setEnabled(true);
        } else {
            c_entitiyUD.setEnabled(false);
            if(c_entitiyUD.getUserDataHolder() instanceof Interpolateable){
                entityInterpolator.removeThisActor((Interpolateable) c_entitiyUD.getUserDataHolder());
            }

        }
    }





    /////////////////////////////////////////////////////////////////
    ////////////////RADAR COLL STUFF ///////////////////////////////
    /////////////////////////////////////////////////////////////////

    private ERQRadarUserData rc_radarUD;
    private ERQUserDataEntities rc_entitiyUD;
    private void radarCollision(Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD, Contact contact, boolean begin) {
        if (fixA_UD != null && fixA_UD instanceof ERQRadarUserData) {
            rc_radarUD = (ERQRadarUserData) fixA_UD;
            rc_entitiyUD = (ERQUserDataEntities) fixB_UD;
        } else {
            rc_radarUD = (ERQRadarUserData) fixB_UD;
            rc_entitiyUD = (ERQUserDataEntities) fixA_UD;
        }
        if(rc_entitiyUD == null){
            return;
        }
        if(rc_entitiyUD.getUserDataHolder() instanceof Radarable){
            if(begin){
                rc_radarUD.addContact((Radarable) rc_entitiyUD.getUserDataHolder());
            } else {
                rc_radarUD.removeContact((Radarable) rc_entitiyUD.getUserDataHolder());
            }
        }
    }

    /////////////////////////////////////////////////////////////////
    ////////////////MAGNET COLL STUFF ///////////////////////////////
    /////////////////////////////////////////////////////////////////


    private Fixture mc_playerFix, mc_objectFix;
    private ERQMagnetUserData mc_magnetUD;
    private ERQUserDataEntities mc_objectUD;
    private void magnetCollision(Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {



        if (fixA_UD != null && fixA_UD instanceof ERQMagnetUserData) {
            mc_playerFix = fixtureA;
            mc_magnetUD = (ERQMagnetUserData) fixA_UD;

            mc_objectFix = fixtureB;
            mc_objectUD = (ERQUserDataEntities) fixB_UD;
        } else {
            mc_playerFix = fixtureB;
            mc_magnetUD = (ERQMagnetUserData) fixB_UD;

            mc_objectFix = fixtureA;
            mc_objectUD = (ERQUserDataEntities) fixA_UD;
        }
        if(mc_objectUD == null){
            return;
        }

        magnetContactStuff(mc_playerFix, mc_objectFix, mc_magnetUD, mc_objectUD);

    }

    private void magnetContactStuff(Fixture playerFix, Fixture objectFix, ERQMagnetUserData magnetUD, ERQUserDataEntities objectUD){
        //ist es beginn contact oder stop contact?
        if(objectUD.getUserDataHolder() instanceof Magnetic){
            magnetUD.addMagneticActor((Magnetic) objectUD.getUserDataHolder());
            return;
        }
    }

    /////////////////////////////////////////////////////////////////
    ////////////////PLAYER COLL STUFF ///////////////////////////////
    /////////////////////////////////////////////////////////////////

    //pc stands for playercontact
    Fixture pc_playerFix, pc_objectFix;
    ERQUserDataPlayer pc_playerUD;
    ERQUserDataEntities pc_objectUD;
    private void playerCollision(Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD, Contact contact, boolean beginning, boolean preSolve) {

        //vertauschen, damit ich lediglich einmal abfragen muss!
        if (fixA_UD != null && fixA_UD instanceof ERQUserDataPlayer) {
            pc_playerFix = fixtureA;
            pc_playerUD = (ERQUserDataPlayer) fixA_UD;

            pc_objectFix = fixtureB;
            pc_objectUD = (ERQUserDataEntities) fixB_UD;
        } else {
            pc_playerFix = fixtureB;
            pc_playerUD = (ERQUserDataPlayer) fixB_UD;

            pc_objectFix = fixtureA;
            pc_objectUD = (ERQUserDataEntities) fixA_UD;
        }
        if(pc_objectUD == null){
            return;
        }

        //ist es von Presolve?
        if(preSolve) {
            preSolveStuff(pc_playerFix, pc_objectFix, pc_playerUD, pc_objectUD, contact);
        } else {
            playerContactStuff(pc_playerFix, pc_objectFix, pc_playerUD, pc_objectUD, beginning);
        }
    }


    private void playerContactStuff(Fixture playerFix, Fixture objectFix, ERQUserDataPlayer playerUD, ERQUserDataEntities objectUD, boolean beginning) {
        //ist es beginn contact oder stop contact?
        if(beginning){
            if(objectUD.getUserDataHolder() instanceof Ground){
                playerUD.setTouchesGround(true);
                return;
            }
            objectUD.setCollisionWithPlayer(true);
        } else {
            if(objectUD.getUserDataHolder() instanceof Ground){
                playerUD.setTouchesGround(false);
                return;
            }
            objectUD.setCollisionWithPlayer(false);
        }
    }

    private void preSolveStuff(Fixture playerFix, Fixture objectFix, ERQUserDataPlayer playerUD, ERQUserDataEntities objectUD, Contact contact) {
        //should the contact be disabled?
        if(!objectUD.shouldCollideWithPlayer()){
            contact.setEnabled(false);
        }
    }

    public interface ContactListenerHookForNewItems{
        /**
         * @return whether or not other collision stuff should be handled
         * if true --> no more collision detection
         * else --> check for other collision, too!
         */
        boolean beginContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD);

        /**
         * @return whether or not other collision stuff should be handled
         * if true --> no more collision detection
         * else --> check for other collision ending stuff, too!
         */
        boolean endContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD);
        /**
         * @return whether or not other collision stuff should be handled
         * if true --> no more collision handling
         * else --> check for other collision ending stuff, too!
         */
        boolean preSolveHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD);
    }
}
