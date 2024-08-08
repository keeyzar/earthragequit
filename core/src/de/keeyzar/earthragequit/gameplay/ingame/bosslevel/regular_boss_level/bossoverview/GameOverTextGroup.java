package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.bossoverview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.beforeend.Startable;

/**
 * This is the "Game" and "Over" text
 * @author = Keeyzar on 12.03.2016
 */
public class GameOverTextGroup extends Group implements Startable {
    Sprite gameSprite;
    Sprite overSprite;
    Actor gameActor;
    Actor overActor;

    public GameOverTextGroup(){
        gameActor = new Actor();
        gameActor.setPosition(-10, 5);
        gameActor.setSize(4, 2);
        gameActor.setRotation(-30);
        gameSprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.GAME_OVER_GAME);
        gameSprite.setPosition(gameActor.getX(), gameActor.getY());
        gameSprite.setSize(gameActor.getWidth(), gameActor.getHeight());
        overActor = new Actor();
        overActor.setPosition(30, 5);
        overActor.setSize(4, 2);
        overSprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.GAME_OVER_OVER);
        overSprite.setPosition(overActor.getX(), overActor.getY());
        overSprite.setSize(overActor.getWidth(), overActor.getHeight());
        overActor.setRotation(30);
        addActor(gameActor);
        addActor(overActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        overActor.setOrigin(overActor.getWidth() / 2, overActor.getHeight() / 2);
        gameActor.setOrigin(gameActor.getWidth() / 2, gameActor.getHeight() / 2);
        overSprite.setOrigin(overActor.getOriginX(), overActor.getOriginY());
        gameSprite.setOrigin(gameActor.getOriginX(), gameActor.getOriginY());


        overSprite.setPosition(overActor.getX(), overActor.getY());
        overSprite.setSize(overActor.getWidth(), overActor.getHeight());
        overSprite.setAlpha(overActor.getColor().a);
        overSprite.setRotation(overActor.getRotation());

        gameSprite.setPosition(gameActor.getX(), gameActor.getY());
        gameSprite.setSize(gameActor.getWidth(), gameActor.getHeight());
        gameSprite.setAlpha(gameActor.getColor().a);
        gameSprite.setRotation(gameActor.getRotation());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        gameSprite.draw(batch);
        overSprite.draw(batch);
    }

    @Override
    public void start(boolean playerWon) {
        if(!playerWon){
            SequenceAction action = Actions.sequence(Actions.alpha(0),
                    Actions.moveTo(3, 5, 0),
                    Actions.parallel(
                            Actions.fadeIn(4, Interpolation.linear),
                            Actions.rotateBy(30, 4)));

            SequenceAction action2 = Actions.sequence(Actions.alpha(0),
                    Actions.moveTo(13, 5, 0),
                    Actions.parallel(
                        Actions.fadeIn(4, Interpolation.linear),
                        Actions.rotateBy(-30, 4)));


            gameActor.addAction(action);
            overActor.addAction(Actions.sequence(action2, Actions.delay(2)));
        } else {
            this.remove();
        }
    }

    @Override
    public boolean hasActions() {
        boolean actions = false;
        if(gameActor.hasActions() || overActor.hasActions()){
            actions = true;
        }
        return actions;
    }


}
