package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * @author = Keeyzar on 26.11.2016.
 */
public class StoneClone extends Actor {
    private final Stone stone;
    private final Sprite sprite;

    public StoneClone(Stone stone, OrthographicCamera camera) {
        this.stone = stone;
        setBounds(camera.position.x, camera.position.y, stone.getWidth(), stone.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        sprite = new Sprite(this.stone.getCurrentSprite());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();

        getColor().a = 0;
        addAction(Actions.fadeIn(1f));
        addAction(Actions.repeat(20, Actions.sequence(
                Actions.moveBy(0.05f, 0.05f, 0.02f),
                Actions.moveBy(-0.05f, 0.05f, 0.02f)
        )));
        addAction(Actions.sizeBy(1.4f, 1));

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setColor(getColor());
        if(!hasActions()){
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
