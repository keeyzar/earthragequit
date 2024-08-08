package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * This interface allows an entitiy to be spotted on radar.
 * @author = Keeyzar on 25.03.2016
 */
public interface Radarable {
    /**
     * Which group (good, bad, neutral)
     * @return the group (got from RADVars)
     */
    short getGroup();

    /**
     * Which entity it is, everyone has a specific id
     * @return the entity id (got from RADVars)
     */
    int getID();

    int getMapId();
    void setMapId(int mapId);

    void drawOnMap(ShapeRenderer shapeRenderer, Vector2 playerPos, float xPos, float yPos, float scale);
}
