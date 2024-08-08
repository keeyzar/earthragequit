package de.keeyzar.earthragequit.transition;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * A half screen, that closes, when starting to load and opens when
 * should show new Screen.
 * @author = Keeyzar on 21.03.2016
 */
public class TransActor extends Actor {
    Sprite sprite;
    int dir;
    public static final int DIR_LEFT = 1;
    public static final int DIR_RIGHT = 2;
    private boolean closed = false;
    private boolean opened = false;
    private float speed = 0.3f;

    public TransActor(Sprite sprite, int dir){
        this.sprite = sprite;
        this.dir = dir;
        setSize(ScreenVariables.SCREEN_WIDTH / 2, ScreenVariables.SCREEN_HEIGHT);
        if(dir == DIR_LEFT){
            setPosition(0 - getWidth(), 0);
        } else {
            setPosition(ScreenVariables.SCREEN_WIDTH, 0);
        }
        this.sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public void closeScreen(){
        int mul = dir == DIR_LEFT ? 1 : -1;
        MoveByAction moveByAction = Actions.moveBy(mul * getWidth(), 0, speed);
        SequenceAction sequence = Actions.sequence(moveByAction, Actions.run(new Runnable() {
            @Override
            public void run() {
                closed = true;
            }
        }));

        addAction(sequence);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setPosition(getX(), getY());
    }

    public void openScreen(){
        int mul = dir == DIR_LEFT ? -1 : 1;
        MoveByAction moveByAction = Actions.moveBy(mul * getWidth(), 0, speed);
        SequenceAction sequence = Actions.sequence(moveByAction, Actions.run(new Runnable() {
            @Override
            public void run() {
                opened = true;
            }
        }));
        addAction(sequence);
    }

    public boolean isClosed(){
        return closed;
    }

    public boolean isOpened() {
        return opened;
    }
}
