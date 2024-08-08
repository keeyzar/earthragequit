package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;

/**
 * A body with 0.00000001 mass. attached to the player
 * @author = Keeyzar on 28.03.2016
 */
public class CullPro {

    private Joint joint;
    private Body body;
    public CullPro(Player player, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(player.position.x, player.position.y));
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(GVars.FOV_WIDTH, GVars.FOV_HEIGHT);

        body = world.createBody(bodyDef);

        Fixture fixture = body.createFixture(polygonShape, 1.2f);
        fixture.setFriction(0);
        fixture.setRestitution(0.2f);
        polygonShape.dispose();

        MassData massData = body.getMassData();
        massData.mass = 0.00000000001f;
        body.setMassData(massData);

        RevoluteJointDef cullingJoint = new RevoluteJointDef();
        cullingJoint.type = JointDef.JointType.RevoluteJoint;
        cullingJoint.bodyA = player.getBody();
        cullingJoint.bodyB = body;
        cullingJoint.localAnchorA.set(0,0);
        cullingJoint.localAnchorB.set(0,0);
        joint = world.createJoint(cullingJoint);

        fixture.setSensor(true);
        Filter filterData = fixture.getFilterData();
        filterData.categoryBits = EntityVars.CATEGORY_PLAYER;
        filterData.maskBits = EntityVars.MASK_PLAYER;
        fixture.setFilterData(filterData);
        final CullUD userData = new CullUD();
        userData.setPlayer(player);
        fixture.setUserData(userData);
    }

    public void gameOver(World world){
        world.destroyJoint(joint);
        body.setLinearVelocity(0, 0);
        body.setGravityScale(0);
        joint = null;
    }
    public Body getBody() {
        return body;
    }
}
