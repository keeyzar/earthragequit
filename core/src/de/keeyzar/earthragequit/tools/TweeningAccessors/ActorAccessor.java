package de.keeyzar.earthragequit.tools.TweeningAccessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author = Keeyzar on 27.02.2017.
 */
public class ActorAccessor implements TweenAccessor<Actor> {
    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;
    public static final int COLOR_ALPHA = 4;
    public static final int ROTATION = 5;
    public static final int SIZE_W = 6;
    public static final int SIZE_H = 7;
    public static final int SIZE_WH = 8;
    public static final int SCALE_W = 9;
    public static final int SCALE_H = 10;
    public static final int SCALE_WH = 11;


    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case POSITION_X: returnValues[0] = target.getX(); return 1;
            case POSITION_Y: returnValues[0] = target.getY(); return 1;
            case POSITION_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case COLOR_ALPHA: returnValues[0] = target.getColor().a; return 1;
            case ROTATION: returnValues[0] = target.getRotation(); return 1;
            case SIZE_W: returnValues[0] = target.getWidth(); return 1;
            case SIZE_H: returnValues[0] = target.getHeight(); return 1;
            case SIZE_WH:
            returnValues[0] = target.getWidth();
            returnValues[1] = target.getHeight();
            return 2;
            case SCALE_W: returnValues[0] = target.getScaleX(); return 1;
            case SCALE_H: returnValues[0] = target.getScaleY(); return 1;
            case SCALE_WH:
                returnValues[0] = target.getScaleX();
                returnValues[1] = target.getScaleY();
            return 2;
            default: throw new RuntimeException("Tweentype couldn't be matched");
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType){
            case POSITION_X: target.setX(newValues[0]); break;
            case POSITION_Y: target.setY(newValues[0]); break;
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case COLOR_ALPHA: target.getColor().a = newValues[0]; break;
            case ROTATION: target.setRotation(newValues[0]); break;
            case SIZE_W: target.setWidth(newValues[0]); break;
            case SIZE_H: target.setHeight(newValues[0]); break;
            case SIZE_WH: target.setSize(newValues[0], newValues[1]); break;
            case SCALE_W: target.setScale(newValues[0], target.getScaleY()); break;
            case SCALE_H: target.setScale(target.getScaleX(), newValues[0]); break;
            case SCALE_WH: target.setScale(newValues[0], newValues[1]); break;
            default: throw new RuntimeException("Tweentype couldn't be matched");
        }
    }
}
