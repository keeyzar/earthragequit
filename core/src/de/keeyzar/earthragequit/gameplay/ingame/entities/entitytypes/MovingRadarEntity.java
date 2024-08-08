package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.RadarableComponent;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.Interpolateable;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Radarable;

/**
 * @author = Keeyzar on 29.03.2016
 */
public abstract class MovingRadarEntity extends MovingEntity implements Radarable, Interpolateable{
    private short entityGroup;
    private float _rW;
    private float _rH;
    private int mapId = -1;
    private boolean rotatingObject = false;

    public Vector2 position = new Vector2();
    public Vector2 oldPosition = new Vector2();
    public float angle;
    public float oldAngle;

    /**
     * sets the entity Id(from EntityVars.__)
     *
     * @param entityId
     */
    public MovingRadarEntity(int entityId, short entityGroup) {
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


    public void setRotatingObject(boolean rotatingObject){
        this.rotatingObject = rotatingObject;
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
        if(rotatingObject){
            RadarableComponent.drawOnMapRotated(shapeRenderer, playerPos, xPos, yPos, scale, _rW, _rH, body);
        } else {
            RadarableComponent.drawOnMap(shapeRenderer, playerPos, xPos, yPos, scale, _rW, _rH, body, false);
        }
    }

    @Override
    public Vector2 getOldPosition() {
        return oldPosition;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getAngle() {
        return angle;
    }

    @Override
    public float getOldAngle() {
        return oldAngle;
    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void setOldAngle(float angle) {
        this.oldAngle = angle;
    }

}
