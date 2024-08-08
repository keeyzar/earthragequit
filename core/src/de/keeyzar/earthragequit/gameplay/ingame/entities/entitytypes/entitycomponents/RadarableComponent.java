package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author = Keeyzar on 25.03.2016
 */
public class RadarableComponent {
    static Vector2 tempPosVec = new Vector2();
    static float scaleFactorW;
    static float scaleFactorH;

    public static void drawOnMap(ShapeRenderer shapeRenderer, Vector2 playerPos, float xPos, float yPos, float scale,
                                 float _rW, float _rH, Body body, boolean circle) {
        scaleFactorW = 9 / scale;
        scaleFactorH = 9 / scale;
        tempPosVec.set(body.getPosition());
        tempPosVec.sub(playerPos);
        tempPosVec.scl(scale);

        _rW /= scaleFactorW;
        _rH /= scaleFactorH;
        if(circle){
            shapeRenderer.circle(xPos + tempPosVec.x, yPos + tempPosVec.y, _rW / 2);
        } else {
            shapeRenderer.rect(xPos + tempPosVec.x - _rW / 2, yPos + tempPosVec.y - _rH / 2, _rW, _rH);
        }
    }

    public static void drawOnMapRotated(ShapeRenderer shapeRenderer, Vector2 playerPos, float xPos, float yPos, float scale,
                                        float _rW, float _rH, Body body) {

        scaleFactorW = 9 / scale;
        scaleFactorH = 9 / scale;
        tempPosVec.set(body.getPosition());
        tempPosVec.sub(playerPos);
        tempPosVec.scl(scale);

        _rW /= scaleFactorW;
        _rH /= scaleFactorH;


        shapeRenderer.rect(xPos + tempPosVec.x - _rW / 2, yPos + tempPosVec.y - _rH / 2,
                _rW / 2, _rH / 2, _rW, _rH, 1, 1, body.getAngle() * MathUtils.radiansToDegrees);
    }

}
