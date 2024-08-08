package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gamestage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.contact_listener.LevelContactListener;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;

/**
 * @author = Keeyzar on 23.02.2016
 */
public class RegularWorld implements WorldUtils {

    private final int worldWidth;
    private final int worldHeigth;
    private final ERQGame game;
    private final OrthographicCamera camera;
    private World world;
    private Player player;
    private Ground ground;


    public RegularWorld(LevelDefining levelDefining, ERQGame game, OrthographicCamera camera, EntityInterpolator entityInterpolator) {
        this.worldWidth = levelDefining.getWorldWidth();
        this.worldHeigth = levelDefining.getWorldHeight();
        this.game = game;
        this.camera = camera;
        createWorld(levelDefining);
        final LevelContactListener listener = new LevelContactListener(entityInterpolator);
        world.setContactListener(listener);
        createPlayer();
    }


    private void createWorld(LevelDefining levelDefining) {
        world = new World(levelDefining.getLevGravity(), true);
        ground = new Ground(world, worldWidth);
    }

    private void createPlayer() {
        player = new Player(world, game, this);
    }


    @Override
    public int getWorldHeigth() {
        return worldHeigth;
    }

    @Override
    public int getWorldWidth() {
        return worldWidth;
    }


    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
        world = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public Ground getGround() {
        return ground;
    }
}
