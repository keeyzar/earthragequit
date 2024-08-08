package de.keeyzar.earthragequit.loading;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 20.03.2016
 */
public class Tap extends Actor {
    Sprite sprite;

    public Tap(Texture texture){

        sprite = new Sprite(texture);
        setSize(ScreenVariables.SCREEN_WIDTH / 2, 200);
        setPosition(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 + getHeight() / 2);
        getColor().a = 0;
        sprite.setAlpha(getColor().a);
        sprite.setPosition(getX(), getY());
        sprite.setSize(getWidth(), getHeight());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setAlpha(getColor().a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    public void start(){
        addAction(Actions.forever(Actions.sequence(Actions.fadeIn(1), Actions.fadeOut(1))));
    }

    public void stop(){
        clearActions();
        addAction(Actions.fadeOut(0.5f));
    }
}