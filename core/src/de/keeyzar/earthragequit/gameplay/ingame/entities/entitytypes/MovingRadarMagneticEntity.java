package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.Magnetic;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.MagneticComponent;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.RadarableComponent;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Radarable;

/**
 * @author = Keeyzar on 29.03.2016
 */
public abstract class MovingRadarMagneticEntity extends MovingEntity implements Radarable, Magnetic{
    private short entityGroup;
    private float _rW;
    private float _rH;
    private int mapId = -1;
    private int magneticSpeed = 10;

    /**
     * sets the entity Id(from EntityVars.__)
     *
     * @param entityId
     */
    public MovingRadarMagneticEntity(int entityId, short entityGroup) {
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
        RadarableComponent.drawOnMap(shapeRenderer, playerPos, xPos, yPos, scale, _rW, _rH, body, false);
    }

    @Override
    public void applyForce(Body whereToMove) {
        userData.setEnabled(true);
        magneticSpeed += MagneticComponent.applyForce(body, whereToMove, magneticSpeed);
    }

    @Override
    public int getId() {
        return entityId;
    }

}
