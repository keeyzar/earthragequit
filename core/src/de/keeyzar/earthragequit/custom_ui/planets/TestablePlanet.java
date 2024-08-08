package de.keeyzar.earthragequit.custom_ui.planets;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.tools.TweeningAccessors.ActorAccessor.*;

/**
 * DO NOT DELETE - is a PoC
 * @author = Keeyzar on 27.02.2017.
 */
public class TestablePlanet extends Actor {
    Sprite sprite;
    public TestablePlanet(ERQGame game){
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANETS, 1);
        setSize(sprite.getWidth(), sprite.getHeight());
        sprite.setOriginCenter();
        setPosition(-400, 900);
        getColor().a = 0.1f;
        initTween(game);
    }

    private void initTween(ERQGame game) {
        int time = 5;
        final Tween movementTween = Tween.to(this, POSITION_XY, time)
//                .delay(5)
                .ease(TweenEquations.easeInSine)
                .target(1200, -200);

        final Tween tweenRotation = Tween.to(this, ROTATION, time)
                .target(360);


        final Tween tweenAlpha = Tween.to(this, COLOR_ALPHA, time)
                .target(0.3f);

        final Tween tweenSize = Tween.set(this, SCALE_WH)
                .targetRelative(-0.7f, -0.7f);


        final TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if (type == TweenCallback.END) {
                    setPosition(MathUtils.random(0, 1000), 800);
                    sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANETS, MathUtils.random(1, 8));
                    setSize(sprite.getWidth(), sprite.getHeight());
                    setOrigin(sprite.getOriginX(), sprite.getOriginY());
                    movementTween.target(MathUtils.random(-200, 1200), -400);
                }
            }
        };
        final Timeline timeline = Timeline.createParallel()
                .push(movementTween)
//                .push(tweenRotation)
//                .push(tweenAlpha)
                .push(tweenSize);


        startSequence(game, tweenCallback, timeline);

    }

    private void startSequence(ERQGame game, TweenCallback tweenCallback, Timeline timeline) {
        Timeline.createSequence()
                .push(timeline)
                .setCallback(tweenCallback)
                .setCallbackTriggers(TweenCallback.END)
                .start(game.getTweenManager());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setRotation(getRotation());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setColor(getColor());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
