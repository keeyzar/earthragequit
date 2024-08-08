package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.Ground;

/**
 * @author = Keeyzar on 10.03.2016
 */
public interface WorldUtils {
    int getWorldWidth();
    int getWorldHeigth();
    OrthographicCamera getCamera();
    Player getPlayer();
    World getWorld();

    void dispose();

    Ground getGround();
}
