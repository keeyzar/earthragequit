package de.keeyzar.earthragequit.tutorial.movement.introstage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.contact_listener.LevelContactListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 16.03.2016
 */
public class TutorialWorld implements WorldUtils {
    Ground ground;
    World world;
    OrthographicCamera camera;

    public TutorialWorld(OrthographicCamera camera){
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new LevelContactListener(new EntityInterpolator()));
        ground = new Ground(world, getWorldWidth());
        this.camera = camera;
    }
    @Override
    public int getWorldWidth() {
        return 50;
    }

    @Override
    public int getWorldHeigth() {
        return 50;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        //doNothing
    }

    @Override
    public Ground getGround() {
        return ground;
    }
}
