package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Pools;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class PlayerTouchpad extends Widget{
    private Player player;

    //Touchpad
    private boolean touched;
    private boolean resetOnTouchUp = true;
    private float deadzoneRadius;
    private final Circle knobBounds = new Circle(0, 0, 0);
    private final Circle touchBounds = new Circle(0, 0, 0);
    private final Circle deadzoneBounds = new Circle(0, 0, 0);
    private final Circle toFarAwayBounds = new Circle(0, 0, 0);
    private final Vector2 knobPosition = new Vector2();
    private final Vector2 knobPercent = new Vector2(0, 1);
    private final Vector2 knobPercentOld = new Vector2(0, 1);

    private Sprite sprite;
    private Vector2 newPos = new Vector2();
    private Vector2 oldPos = new Vector2();
    private boolean checkForTooFar = false;


    public PlayerTouchpad(Player player){
        this.player = player;
        initTouchpad();
        resetOnTouchUp = false;
        layout();
        getColor().a = 0.4f;
        sprite.setColor(getColor());
    }

    public void setCheckForTooFar(boolean checkForTooFar) {
        this.checkForTooFar = checkForTooFar;
    }

    private void initTouchpad() {
        this.deadzoneRadius = 20;
        setBounds(50, 50, 200, 200);
        knobPosition.set(getWidth() / 2f, getHeight() / 2f);
        initTouchpadSprite();
        addListener(new InputListener() {
            Vector2 tmp = new Vector2();
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (touched) return false;
                setPosition(x - getWidth() / 2, y - getHeight() / 2);
                touched = true;
                event.toCoordinates(PlayerTouchpad.this, tmp.set(x, y));
                calculatePositionAndValue(tmp.x, tmp.y, false);
                addAction(Actions.alpha(0.8f, 0.1f));
                return true;
            }

            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                event.toCoordinates(PlayerTouchpad.this, tmp.set(x, y));
                if(checkForTooFar) checkToFar(tmp.x, tmp.y);
                calculatePositionAndValue(tmp.x, tmp.y, false);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                touched = false;
                event.toCoordinates(PlayerTouchpad.this, tmp.set(x, y));
                calculatePositionAndValue(tmp.x ,tmp.y, resetOnTouchUp);
                addAction(Actions.alpha(0.4f, 0.1f));
            }
        });
    }

    private void checkToFar(float x, float y) {
        //movePosition to x and y, but with distance
        if(!toFarAwayBounds.contains(x, y)){
            oldPos = new Vector2(getX(), getY());
            newPos = localToParentCoordinates(new Vector2(x - getWidth() / 2 , y - getHeight() / 2));
            getActions().clear();
            final float x1 = MathUtils.lerp(oldPos.x, newPos.x, 0.6f);
            final float y1 = MathUtils.lerp(oldPos.y, newPos.y, 0.6f);
            addAction(Actions.moveTo(x1, y1, 0.1f));
        }
    }

    private void initTouchpadSprite() {
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.JOYSTICK);
        sprite.setBounds(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2, getHeight() / 2);
    }


    private void calculatePositionAndValue(float x, float y, boolean isTouchUp) {
        float oldPositionX = knobPosition.x;
        float oldPositionY = knobPosition.y;
        float oldPercentX = knobPercent.x;
        float oldPercentY = knobPercent.y;
        float centerX = knobBounds.x;
        float centerY = knobBounds.y;
        knobPosition.set(centerX, centerY);
        knobPercent.set(knobPercentOld);
        if (!isTouchUp) {
            if (!deadzoneBounds.contains(x, y)) {
                knobPercent.set((x - centerX) / knobBounds.radius, (y - centerY) / knobBounds.radius);
                float length = knobPercent.len();
                if (length > 1) knobPercent.scl(1 / length);
                if (knobBounds.contains(x, y)) {
                    knobPosition.set(x, y);
                } else {
                    knobPosition.set(knobPercent).nor().scl(knobBounds.radius).add(knobBounds.x, knobBounds.y);
                }
            }
        } else {
            getActions().clear();
        }
        if (oldPercentX != knobPercent.x || oldPercentY != knobPercent.y) {
            ChangeListener.ChangeEvent changeEvent = Pools.obtain(ChangeListener.ChangeEvent.class);
            if (fire(changeEvent)) {
                knobPercent.set(oldPercentX, oldPercentY);
                knobPosition.set(oldPositionX, oldPositionY);
            }
            Pools.free(changeEvent);
        }
        knobPercentOld.set(knobPercent);
    }

    @Override
    public void layout () {
        // Recalc pad and deadzone bounds
        float halfWidth = getWidth() / 2;
        float halfHeight = getHeight() / 2;
        float radius = Math.min(halfWidth, halfHeight);
        touchBounds.set(halfWidth, halfHeight, radius);
        knobBounds.set(halfWidth, halfHeight, radius);
        deadzoneBounds.set(halfWidth, halfHeight, deadzoneRadius);
        toFarAwayBounds.set(halfWidth, halfHeight, 175);
        // Recalc pad values and knob position
        knobPosition.set(halfWidth, halfHeight);
        knobPercent.set(0, 0);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch, parentAlpha);
    }


    Vector2 toRemove = new Vector2(0, 1);
    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.getPlayerMovement().playerMovement(toRemove);
        }
        if(touched) {
            player.getPlayerMovement().playerMovement(knobPercent);
            sprite.setPosition(getX() + getWidth() / 2 - getWidth() / 4, getY() + getHeight() / 2 - getHeight() / 4);
        } else {
            player.getPlayerMovement().playerNoMovement();
        }
    }

    @Override
    public Actor hit (float x, float y, boolean touchable) {
        return null;
    }
}
