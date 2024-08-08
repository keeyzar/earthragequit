package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * @author = Keeyzar on 31.03.2016
 */
public class BalthazarClone extends Actor {

    private Balthazar balthazar;
    private Sprite sprite;

    public BalthazarClone(Balthazar balthazar, OrthographicCamera camera){
            this.balthazar = balthazar;
            setBounds(camera.position.x, camera.position.y, this.balthazar.getWidth(), this.balthazar.getHeight());
            setOrigin(getWidth() / 2, getHeight() / 2);

            sprite = new Sprite(this.balthazar.getCurrentSprite());
            sprite.setBounds(getX(), getY(), getWidth(), getHeight());
            sprite.setOriginCenter();

            SequenceAction sequence = Actions.sequence(Actions.moveBy(-5, 0, 2), Actions.moveBy(5, 0, 2));
            getColor().a = 0;
            Action action = new SequenceAction(Actions.parallel(Actions.repeat(4, Actions.sequence(
                    Actions.fadeIn(0.5f, Interpolation.bounceIn),
                    Actions.fadeOut(0.5f, Interpolation.bounceOut))),
                    sequence)
            );
            addAction(action);

    }

    private Interpolation randomInterpolation() {
        int i = MathUtils.random(1, 5);
        switch (i){
            case 1:
                return Interpolation.bounce;
            case 2:
                return Interpolation.pow5;
            case 3:
                return Interpolation.fade;
            case 4:
                return Interpolation.swing;
            case 5:
                return Interpolation.pow5In;
        }
        return Interpolation.linear;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setColor(getColor());
        if(!hasActions()){
            balthazar.setPosition(getX(), getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
