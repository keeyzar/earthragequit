package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkill_Magnet;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.Magnetic;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

import java.util.Iterator;

/**
 * @author = Keeyzar on 04.03.2016
 */
public class Ingame_Magnet implements IngameSkill {
    private Player player;
    private World world;
    private PlayerSkill_Magnet magnet;
    private Body body;

    @Override
    public void init(ERQGame game, World world, Player player) {
        this.world = world;
        this.player = player;
        magnet = (PlayerSkill_Magnet) game.getSkillsVerwalter().getSkillByName(SkillsVars.MAGNET);
        init(0, game);
    }

    public void init(int strength, ERQGame game){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(player.position.x, player.position.y));
        CircleShape shape = new CircleShape();
        if(strength == 0) {
            shape.setRadius(Player.WIDTH + magnet.getBoostForCurrentLevel());
        } else {
            shape.setRadius(Player.WIDTH + strength);
        }
        body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0);
        fixture.setFriction(0);
        body.setAngularDamping(0f);
        MassData massData = body.getMassData();
        massData.mass = 0.00000000000000001f;
        body.setMassData(massData);
        body.setGravityScale(0);

        shape.dispose();

        RevoluteJointDef magnetPlayerJoint = new RevoluteJointDef();
        magnetPlayerJoint.type = JointDef.JointType.RevoluteJoint;
        magnetPlayerJoint.bodyA = player.getBody();
        magnetPlayerJoint.bodyB = body;
        magnetPlayerJoint.localAnchorA.set(0,0);
        magnetPlayerJoint.localAnchorB.set(0,0);
        world.createJoint(magnetPlayerJoint);

        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_PLAYER;
        filterData.maskBits = EntityVars.MASK_PLAYER;
        fixture.setFilterData(filterData);
        fixture.setSensor(true);
        fixture.setUserData(new ERQMagnetUserData(game));
    }

    @Override
    public void act(float delta) {
        act();
    }

    @Override
    public void activate() {
        //do nothing.
    }

    @Override
    public String getName() {
        return SkillsVars.MAGNET;
    }

    /**
     * the culling Operator cant handle objects removed from their position, so the Magnet must handle it.
     */
    public void act(){
        ERQMagnetUserData userData = (ERQMagnetUserData) body.getFixtureList().first().getUserData();
        Iterator<Magnetic> magneticActors = userData.getMagneticActors().iterator();
        while(magneticActors.hasNext()){
            Magnetic next = magneticActors.next();
            next.applyForce(body);
            GameActor gm = (GameActor)next;
            if(gm.getBody() == null){
                magneticActors.remove();
            }

        }
    }
}
