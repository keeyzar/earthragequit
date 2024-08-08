package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.contact_listener.BossContactListener;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;

/**
 * @author = Keeyzar on 10.03.2016
 */
public class BossWorld implements WorldUtils {
    private final Ground ground;
    private ERQGame game;
    private OrthographicCamera camera;

    private int bw_Width; //bossWorldWidth
    private int bw_Height;

    private World world;
    private Player player;

    public BossWorld(ERQGame game, OrthographicCamera camera, BossStage bossStage, BossLevelDefining bld, EntityInterpolator entityInterpolator){
        this.game = game;
        this.camera = camera;
        bw_Width = GVars.FOV_WIDTH;
        bw_Height = GVars.FOV_HEIGHT;
        world = new World(new Vector2(0, -10), true);
        ground = new Ground(world, bw_Width, -0.9f);
        world.setContactListener(new BossContactListener(true, entityInterpolator));
        player = bld.installPlayer(world, game, this);
        bossStage.addActor(player);

    }

    @Override
    public int getWorldWidth() {
        return bw_Width;
    }

    @Override
    public int getWorldHeigth() {
        return bw_Height;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {

    }

    @Override
    public Ground getGround() {
        return ground;
    }
}
