package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static net.dermetfan.gdx.physics.box2d.Box2DUtils.*;

/**
 * Used for bodies, which are interpolated aswell.
 * @author = Keeyzar on 28.02.2017.
 */
public class InterpolatedBodySprite extends Sprite{
    /** if the width and height should be adjusted to those of the {@link Body} or {@link Fixture} this {@link Box2DSprite} is attached to (true by default) */
    private boolean adjustWidth = true, adjustHeight = true;

    /** for internal, temporary usage */
    private static final Vector2 vec2 = new Vector2();

    /** @see Sprite#Sprite(Sprite) */
    public InterpolatedBodySprite(Sprite sprite) {
        super(sprite);
    }

    /** draws this {@link Box2DSprite} on the given {@link Fixture} */
    public void draw(Batch batch, Fixture fixture) {
        vec2.set(position(fixture));
        draw(batch, vec2.x, vec2.y, width(fixture), height(fixture), fixture.getBody().getAngle());
    }

    /** draws this {@link Box2DSprite} on the given {@link Body} */
    public void draw(Batch batch, Body body, Vector2 positionToUse, float angleToUse) {
        float width = width(body), height = height(body);
        vec2.set(minX(body) + width / 2, minY(body) + height / 2);
        vec2.set(positionToUse);
        draw(batch, vec2.x, vec2.y, width, height, angleToUse);
    }

    public void draw(Batch batch, float box2dX, float box2dY, float box2dWidth, float box2dHeight, float box2dRotation) {
        batch.setColor(getColor());
        batch.draw(this, box2dX - box2dWidth / 2 + getX(), box2dY - box2dHeight / 2 + getY(), box2dWidth / 2,  box2dHeight / 2, adjustWidth ? box2dWidth : getWidth(), adjustHeight ? box2dHeight : getHeight(), getScaleX(), getScaleY(), box2dRotation * MathUtils.radDeg + getRotation());
    }

    /** @return the {@link #adjustWidth} */
    public boolean isAdjustWidth() {
        return adjustWidth;
    }

    /** @param adjustWidth the {@link #adjustWidth} to set */
    public void setAdjustWidth(boolean adjustWidth) {
        this.adjustWidth = adjustWidth;
    }

    /** @return the {@link #adjustHeight} */
    public boolean isAdjustHeight() {
        return adjustHeight;
    }

    /** @param adjustHeight the {@link #adjustHeight} to set */
    public void setAdjustHeight(boolean adjustHeight) {
        this.adjustHeight = adjustHeight;
    }

    /** @param adjustSize the {@link #adjustWidth} and {@link #adjustHeight} to set */
    public void setAdjustSize(boolean adjustSize) {
        adjustWidth = adjustHeight = adjustSize;
    }

    /** @see Sprite#setSize(float, float) */
    public void setWidth(float width) {
        setSize(width, getHeight());
    }

    /** @see Sprite#setSize(float, float) */
    public void setHeight(float height) {
        setSize(getWidth(), height);
    }
}
