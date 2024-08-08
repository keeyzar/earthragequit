package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * @author = Keeyzar on 15.03.2016
 */
public interface PlayerMove {
    void move(Vector2 dir);

    void doesNotMove();
}
