package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class NewDimension_StoneClone extends Actor {
    private Sprite sprite;

    private Sprite spriteLeft;
    private Sprite spriteRight;

    Actor left;
    Actor right;

    Group group;
    private NewDimension_StoneClone.Callback callback;

    public NewDimension_StoneClone(final Callback callback){
        this.callback = callback;
        sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STONE_BOSS_3);
        sprite.setOriginCenter();


        TextureRegion txLeft = new TextureRegion(sprite.getTexture(), sprite.getRegionX(), sprite.getRegionY(),
                sprite.getRegionWidth() / 2, sprite.getRegionHeight());

        group = new Group();

        left = new Actor(){
            @Override
            public void act(float delta) {
                super.act(delta);
                spriteLeft.setBounds(left.getX(), left.getY(), left.getWidth(), left.getHeight());
                spriteLeft.setRotation(getRotation());
            }

            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                spriteLeft.draw(batch, parentAlpha);
            }
        };
        left.setBounds(0, 0, 100, 200);

        TextureRegion txRight = new TextureRegion(sprite.getTexture(), sprite.getRegionX() + sprite.getRegionWidth() / 2, sprite.getRegionY(),
                sprite.getRegionWidth() / 2, sprite.getRegionHeight());

        right = new Actor() {
            @Override
            public void act(float delta) {
                super.act(delta);
                spriteRight.setBounds(right.getX(), right.getY(), right.getWidth(), right.getHeight());
                spriteRight.setRotation(getRotation());
            }

            @Override
            public void draw(Batch batch, float parentAlpha) {
                spriteRight.draw(batch, parentAlpha);
            }
        };
        right.setBounds(0+left.getWidth(), 0, 100, 200);


        spriteLeft = new Sprite(txLeft);
        spriteRight = new Sprite(txRight);

        group.addActor(left);
        group.addActor(right);

        group.setBounds(800, 300, 200, 200);
        group.setOrigin(100, 100);

        left.setOrigin(100, 0);
        spriteLeft.setOrigin(left.getOriginX(), left.getOriginY());
        left.addAction(Actions.rotateBy(10, 3));

        right.setOrigin(0, 0);
        spriteRight.setOrigin(right.getOriginX(), right.getOriginY());
        right.addAction(Actions.rotateBy(-10, 3));

        group.addAction(Actions.sequence(Actions.repeat(45, Actions.sequence(Actions.moveBy(-5, -5, 0.02f),
                Actions.moveBy(5, 5, 0.02f))),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        callback.finishedAnim();
                    }
                }),
                Actions.fadeOut(4f)
                ));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        group.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        group.act(delta);
    }

    public float getCenterX() {
        return group.getX() + group.getWidth() / 2;

    }

    public float getCenterY() {
        return group.getY() + group.getHeight() / 2;
    }

    public interface Callback{
        void finishedAnim();
    }
}
