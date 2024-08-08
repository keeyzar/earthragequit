package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.RadarableComponent;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Radarable;

/**
 * @author = Keeyzar on 29.03.2016
 */
public abstract class NonMovingRadarEntity extends NonMovingEntity implements Radarable {
    private short entityGroup;
    private float _rW;
    private float _rH;
    private int mapId = -1;


    private boolean circle = false;

    /**
     * sets the entity Id(from EntityVars.__)
     *
     * @param entityId
     */
    public NonMovingRadarEntity(int entityId, short entityGroup) {
        super(entityId);
        this.entityGroup = entityGroup;
    }

    /**
     * inits the scale of the object on the radar.
     */

    public void initRadar(){
        _rW = getWidth() * 9;
        _rH = getHeight() * 9;
    }

    public void initRadar(float width, float height){
        _rW = getWidth() * width;
        _rH = getHeight() * height;
    }

    @Override
    public short getGroup() {
        return entityGroup;
    }

    @Override
    public int getID() {
        return entityId;
    }

    @Override
    public int getMapId() {
        return mapId;
    }

    @Override
    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    @Override
    public void drawOnMap(ShapeRenderer shapeRenderer, Vector2 playerPos, float xPos, float yPos, float scale) {
        RadarableComponent.drawOnMap(shapeRenderer, playerPos, xPos, yPos, scale, _rW, _rH, body, circle);
    }


    public boolean isCircle() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }
}
