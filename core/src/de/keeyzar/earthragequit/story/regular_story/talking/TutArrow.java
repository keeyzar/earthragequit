package de.keeyzar.earthragequit.story.regular_story.talking;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 16.03.2016
 */
public class TutArrow extends Actor {
    Sprite sprite;
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
    public static final int NORTHEAST = 5;
    public static final int NORTHWEST = 6;
    public static final int SOUTHEAST = 7;
    public static final int SOUTHWEST = 8;
    private Stage stage;

    public TutArrow(Stage stage){
        this.stage = stage;
        this.sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.TUT_ARROW);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setPosition(getX(), getY());
        sprite.setColor(getColor());

    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        sprite.setBounds(x, y, width, height);
        setPosition(-30, -30);
        sprite.setPosition(getX(), getY());
    }

    public void start(int DIR, int x, int y, int intensity){
        addAction(Actions.sequence(Actions.fadeIn(0.3f)));
        stage.addActor(this);
        setPosition(x, y);
        getColor().a = 0;

        float mX = 0, mY = 0;
        switch(DIR){
            case NORTH:
                mY = -1;
                moveBy(-getWidth() / 4, - getHeight());
                setRotation(0);
                break;
            case SOUTH:
                moveBy((getWidth() / 4), 0);
                mY = +1;
                setRotation(180);
                break;
            case EAST:
                moveBy(0, getHeight() / 2);
                mX = -1;
                setRotation(270);
                break;
            case WEST:
                moveBy(0, - (getHeight() / 2));
                mX = +1;
                setRotation(90);
                break;
            case NORTHEAST:
                moveBy(- (getWidth() / 4 * 3), -getHeight());
                mX = - 0.5f;
                mY = - 0.5f;
                setRotation(315);
                break;
            case NORTHWEST:
                moveBy(+ (getWidth() / 3), - (getHeight() / 4 * 3));
                setRotation(45);
                mX = + 0.5f;
                mY = - 0.5f;
                break;
            case SOUTHEAST:
                moveBy(-getWidth(), -getHeight() / 2);
                setRotation(225);
                mX = -0.5f;
                mY = 0.5f;
                break;
            case SOUTHWEST:
                moveBy(0, -getHeight() / 4);
                setRotation(135);
                mX = 0.5f;
                mY = 0.5f;
                break;
        }
        mX *= intensity;
        mY *= intensity;
        sprite.setRotation(getRotation());
        sprite.setPosition(getX(), getY());
        sprite.setColor(getColor());
        addAction(Actions.forever(Actions.sequence(Actions.moveBy(mX, mY, 1), Actions.moveBy(-mX, -mY, 1))));
    }

    public void stop(){
        getColor().a = 0;
        setPosition(-30, -30);
        sprite.setPosition(getX(), getY());
        clearActions();
        remove();
    }


}
