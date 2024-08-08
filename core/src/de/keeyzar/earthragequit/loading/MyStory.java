package de.keeyzar.earthragequit.loading;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * Created by Keeyzar on 09.02.2016.
 */
public class MyStory extends Actor {
    private Sprite sprite;
    public MyStory(Sprite sprite, final LoadingScreen ld){
        this.sprite = sprite;
        setSize(1000, 500);
        setPosition(0, ScreenVariables.SCREEN_HEIGHT / 2 - 250);

        getColor().a = 0;
        SequenceAction sequence = Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(1.2f), Actions.fadeOut(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                ld.startLoading();
            }
        }));
        addAction(sequence);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setAlpha(getColor().a);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX(), getY());
        sprite.draw(batch, parentAlpha);
    }

}
