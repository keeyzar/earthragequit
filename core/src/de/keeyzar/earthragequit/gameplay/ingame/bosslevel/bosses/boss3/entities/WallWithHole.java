package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author = Keeyzar on 12.01.2017.
 */
public class WallWithHole {
    private final B3Wall actorLeft;
    private final B3Wall actorRight;
    private World world;

    public WallWithHole(World world, Stage stage, float holeSize, int holeXPosition, int yPos) {
        this.world = world;

        actorLeft = new B3Wall(world, 0, yPos, holeXPosition, 1, true);
        stage.addActor(actorLeft);

        actorRight = new B3Wall(world, holeXPosition + holeSize, yPos, 13 - holeSize, 1, false);
        stage.addActor(actorRight);

    }

    public void destroy(){
        actorLeft.triggerToDestroy();
        actorRight.triggerToDestroy();

    }
}
