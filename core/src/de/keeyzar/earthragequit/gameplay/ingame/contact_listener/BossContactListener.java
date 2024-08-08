package de.keeyzar.earthragequit.gameplay.ingame.contact_listener;

import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.ERQArrowUD;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.HitableByArrow;
import de.keeyzar.earthragequit.gameplay.ingame.entities.ERQUserDataEntities;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.Interpolateable;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ERQUserDataPlayer;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling.CullUD;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 01.03.2016
 */
public class BossContactListener implements ContactListener, BasicContactListener.ContactListenerHookForNewItems {

    private boolean enableArrow;
    private EntityInterpolator entityInterpolator;
    private BasicContactListener basicContactListener;

    public BossContactListener(boolean enableArrow, EntityInterpolator entityInterpolator){
        this.enableArrow = enableArrow;
        this.entityInterpolator = entityInterpolator;
        basicContactListener = new BasicContactListener(this);
        basicContactListener.setEntityInterpolator(entityInterpolator);
    }


    @Override
    public void beginContact(Contact contact) {
        basicContactListener.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        basicContactListener.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        basicContactListener.preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
       basicContactListener.postSolve(contact, impulse);
    }


    @Override
    public boolean beginContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
        if(enableArrow){
            if (fixA_UD != null && fixA_UD instanceof ERQArrowUD|| fixB_UD != null && fixB_UD instanceof ERQArrowUD) {
                arrowCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, true, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean endContactHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
        if(enableArrow){
            if (fixA_UD != null && fixA_UD instanceof ERQArrowUD|| fixB_UD != null && fixB_UD instanceof ERQArrowUD) {
                //doNothing, but return true!
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preSolveHookBeforeEverything(Contact contact, Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD) {
        if(enableArrow){
            if (fixA_UD != null && fixA_UD instanceof ERQArrowUD|| fixB_UD != null && fixB_UD instanceof ERQArrowUD) {
                arrowCollision(fixtureA, fixtureB, fixA_UD, fixB_UD, contact, false, true);
                return true;
            }
        }
        return false;
    }


    /////////////////////////////////////////////////////////////////
    ////////////////ARROW COLL STUFF ///////////////////////////////
    /////////////////////////////////////////////////////////////////

    private Fixture ac_arrowFix, ac_objectFix;
    private ERQArrowUD ac_arrowUD;
    Object ac_objectUD;
    private void arrowCollision(Fixture fixtureA, Fixture fixtureB, Object fixA_UD, Object fixB_UD, Contact contact, boolean beginning, boolean preSolve) {

        //vertauschen, damit ich lediglich einmal abfragen muss!
        if (fixA_UD != null && fixA_UD instanceof ERQArrowUD) {
            ac_arrowFix = fixtureA;
            ac_arrowUD = (ERQArrowUD) fixA_UD;

            ac_objectFix = fixtureB;
            ac_objectUD = fixB_UD;
        } else {
            ac_arrowFix = fixtureB;
            ac_arrowUD = (ERQArrowUD) fixB_UD;

            ac_objectFix = fixtureA;
            ac_objectUD = fixA_UD;
        }
        if(ac_objectUD == null){
            return;
        }

        if(preSolve){
            contact.setEnabled(false);
            return;
        }
        if(beginning){
            if(ac_objectUD instanceof CullUD){
                if(ac_arrowUD.userDataHolder instanceof Interpolateable){
                    entityInterpolator.addActor((Interpolateable) ac_arrowUD.userDataHolder);
                }
            }
            if(ac_objectUD instanceof ERQUserDataEntities){
                if(((ERQUserDataEntities) ac_objectUD).getUserDataHolder() instanceof HitableByArrow){
                    ac_arrowUD.isAlive = false;
                    ((HitableByArrow) ((ERQUserDataEntities) ac_objectUD).getUserDataHolder()).gotHitByArrow();

                    //this part is for Boss_3 arrow
                    if(((ERQUserDataEntities) ac_objectUD).getUserDataHolder() instanceof GameActor){
                        ac_arrowUD.actualCollisioningUserData = (ERQUserDataEntities) ac_objectUD;
                    }
                    //til here (boss_3 arrow)

                    return;
                }
            }
            if(ac_objectUD instanceof ERQUserDataPlayer){
                ac_arrowUD.fixThatPickedArrowUp = ac_objectFix;
                return;
            }
        } else {
            if(ac_objectUD instanceof CullUD){
                if(ac_arrowUD.userDataHolder instanceof Interpolateable){
                    entityInterpolator.removeThisActor((Interpolateable) ac_arrowUD.userDataHolder);
                }
            }
        }


    }
}
