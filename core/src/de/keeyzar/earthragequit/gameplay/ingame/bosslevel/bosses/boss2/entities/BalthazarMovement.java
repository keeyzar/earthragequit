package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * @author = Keeyzar on 31.03.2016
 */
public class BalthazarMovement {
    private Balthazar balthazar;
    private OrthographicCamera camera;

    Actor xMoveActor;
    Actor yMoveActor;

    boolean movesLeft;
    boolean movesTop;

    public BalthazarMovement(Balthazar balthazar, OrthographicCamera camera){
        this.balthazar = balthazar;
        this.camera = camera;
        xMoveActor = new Actor();
        xMoveActor.setPosition(balthazar.getX(), balthazar.getY());

        yMoveActor = new Actor();
        yMoveActor.setPosition(balthazar.getX(), balthazar.getY());
        randomMovement(false, false);
    }

    public void act(float delta){
        xMoveActor.act(delta);
        yMoveActor.act(delta);
        balthazar.setPosition(xMoveActor.getX(), yMoveActor.getY());
    }

    public void checkMovement(){
        if(movesLeft && balthazar.getX() < camera.position.x - camera.viewportWidth / 2){
            randomMovement(true, true);
        } else if(!movesLeft && balthazar.getRight() > camera.position.x + camera.viewportWidth / 2){
            randomMovement(true, true);
        }

        if(movesTop && balthazar.getTop() > camera.position.y + camera.viewportHeight / 2){
            randomMovement(false, true);
        } else if(!movesTop && balthazar.getY() < camera.position.y){
            randomMovement(false, true);
        }
    }

    private void randomX(){
        xMoveActor.clearActions();
        float x = MathUtils.random(1, 5);
        movesLeft = MathUtils.randomBoolean();
        x *= movesLeft ? -1 : 1;
        xMoveActor.addAction(Actions.sequence(Actions.moveBy(x, 0, 0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                randomX();
            }
        })));
    }

    private void randomY(){
        yMoveActor.clearActions();
        float y = MathUtils.random(1, 5);
        movesTop = MathUtils.randomBoolean();
        y *= movesTop ? 1 : -1;
        yMoveActor.addAction(Actions.sequence(Actions.moveBy(0, y, 0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                randomY();
            }
        })));
    }

    private void randomMovement(boolean xDir, boolean invert){
        randomX();
        randomY();
    }

    private Interpolation randomInterpolation() {
        return Interpolation.linear;
    }
}
