package de.keeyzar.earthragequit.story.regular_story.landing_story;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 17.03.2016
 */
public class PlayerClone extends Actor {
    Sprite sprite;
    Sprite spriteAlien;
    ParticleEffect xplo3;
    Sound sound;
    private boolean explode = false;
    private boolean rocketDestroyed = false;
    private boolean finished = false;

    public PlayerClone(ERQGame game){
        sprite = game.getSkinVerwalter().getBestShipSkin().getSprite();
        setSize(200, 200);
        setPosition(-300, -100);
        sprite.setBounds(getX(), getY(), getWidth(),getHeight());
        spriteAlien = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_ALIEN);
        xplo3 = ERQAssets.MANAGER.get(AssetVariables.STORY_XPLO_3, ParticleEffect.class);
        sound = ERQAssets.MANAGER.get(AssetVariables.SOUND_EXPLOSION, Sound.class);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setColor(getColor());
        sprite.setRotation(getRotation());
        sprite.setOrigin(getOriginX(), getOriginY());
        sprite.setBounds(getX(), getY(), getWidth(),getHeight());
        spriteAlien.setColor(getColor());
        spriteAlien.setRotation(getRotation());
        spriteAlien.setOrigin(getOriginX(), getOriginY());
        spriteAlien.setBounds(getX(), getY(), getWidth(),getHeight());
        xplo3.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);

        if(xplo3.isComplete()){
            explode = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!rocketDestroyed) {
            sprite.draw(batch, parentAlpha);
        } else {
            spriteAlien.draw(batch, parentAlpha);
        }
        if(explode){
            xplo3.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    public void moveToMid(){
        MoveToAction moveToAction = Actions.moveTo(ScreenVariables.SCREEN_WIDTH / 2 - getWidth() / 2, ScreenVariables.SCREEN_HEIGHT / 2 - getHeight() / 2, 2);
        SequenceAction sequence = Actions.sequence(Actions.parallel(Actions.forever(Actions.rotateBy(360, 4)), moveToAction));
        addAction(sequence);
    }

    public void rotateTo45(){
        clearActions();
        while(getRotation() > 360){
            setRotation(getRotation() - 360);
        }
        addAction(Actions.sequence(Actions.rotateTo(45, 1), Actions.forever(Actions.sequence(Actions.moveBy(3, 3, 0.02f),
                Actions.moveBy(-3, 0, 0.02f),
                Actions.moveBy(0, -6, 0.02f),
                Actions.moveBy(-3, 3, 0.02f),
                Actions.moveBy(3, 0, 0.02f)))));
    }

    public void explosion() {
        xplo3.start();
        addAction(Actions.sequence(Actions.delay(1.7f), Actions.run(new Runnable() {
            @Override
            public void run() {
                sound.play(MusicHandler.getSoundVolume());
                addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            rocketDestroyed = true;
                                            getColor().a = 1;
                                            addAction(Actions.sequence(Actions.parallel(Actions.repeat(20, Actions.rotateBy(360, 0.3f)), Actions.sizeTo(10, 10, 6)), Actions.fadeOut(0.3f), Actions.run(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finished();
                                                }
                                            })));
                                        }
                                        }))); }
        }),
                Actions.delay(0.3f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        sound.play(MusicHandler.getSoundVolume());
                    }
                }),
                Actions.delay(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        sound.play(MusicHandler.getSoundVolume());
                        addAction(Actions.sequence(Actions.delay(2.2f), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                sound.stop();
                            }
                        })));
                    }
                })));
        explode = true;
    }

    private void finished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }
}
