package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

/**
 * @author = Keeyzar on 24.03.2016
 */
public class Ingame_Radar implements IngameSkill {
    private ERQGame game;
    private Player player;
    private World world;
    private ERQRadarUserData radarUserData;
    private Body body;


    @Override
    public void init(ERQGame game, World world, Player player) {
        this.game = game;
        this.player = player;
        this.world = world;
        init();
    }


    @Override
    public void act(float delta) {

    }

    @Override
    public void activate() {

    }

    @Override
    public String getName() {
        return SkillsVars.RADAR;
    }


    public void init(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(player.position.x, player.position.y));
        CircleShape shape = new CircleShape();
        shape.setRadius(player.getPlayerCalculatedVars().getRadarSize());


        body = world.createBody(bodyDef);
        MassData massData = body.getMassData();
        massData.mass = 0.00000000001f;
        body.setMassData(massData);
        Fixture fixture = body.createFixture(shape, 0);
        fixture.setFriction(0);
        shape.dispose();

        RevoluteJointDef magnetPlayerJoint = new RevoluteJointDef();
        magnetPlayerJoint.type = JointDef.JointType.RevoluteJoint;
        magnetPlayerJoint.bodyA = player.getBody();
        magnetPlayerJoint.bodyB = body;
        magnetPlayerJoint.localAnchorA.set(0,0);
        magnetPlayerJoint.localAnchorB.set(0,0);
        world.createJoint(magnetPlayerJoint);

        fixture.setSensor(true);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_PLAYER;
        filterData.maskBits = EntityVars.MASK_PLAYER;
        fixture.setFilterData(filterData);
        radarUserData = new ERQRadarUserData();
        fixture.setUserData(radarUserData);
    }

    public ObjectMap<Integer, Radarable> getCollisionMap(short group) {
        switch (group){
            case EntityVars.GROUP_GOOD:
                return radarUserData.getGoodCurrentContacts();
            case EntityVars.GROUP_NEUTRAL:
                return radarUserData.getNeutralCurrentContacts();
            case EntityVars.GROUP_BAD:
                return radarUserData.getBadCurrentContacts();
            case EntityVars.GROUP_SPECIAL:
                return radarUserData.getSpecialCurrentContacts();
        }
        System.err.println("Noone should reach that here. [Ingame_Radar getCollisionMap]");
        return null;
    }
}
