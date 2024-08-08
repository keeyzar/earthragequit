package de.keeyzar.earthragequit.story.regular_story.open_new_dimension;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 13.01.2017.
 */
public class RonaxAlAndOrb extends Actor {
    private Actor ronax;
    private Sprite ronaxSprite;

    private Actor al;
    private Sprite alSprite;

    public RonaxAlAndOrb() {
        initRonax();
        initAl();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        al.act(delta);
        ronax.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ronax.draw(batch, parentAlpha);
        al.draw(batch, parentAlpha);
    }

    private void initAl() {
        al = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                alSprite.draw(batch, parentAlpha);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                alSprite.setBounds(getX(), getY(), getWidth(), getHeight());
            }
        };
        al.setBounds(800, 200, 150, 300);
        alSprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_AL);
    }

    private void initRonax() {
        ronax = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                ronaxSprite.draw(batch, parentAlpha);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                ronaxSprite.setBounds(getX(), getY(), getWidth(), getHeight());
            }
        };
        ronax.setBounds(200, 200, 125, 225);
        ronaxSprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_ALIEN);
    }

    public Vector2 getPositionFromRonax() {
        return new Vector2(ronax.getX() + ronax.getWidth() / 2, ronax.getY() + ronax.getHeight() / 2);
    }

    public void moveAlToRight() {
        al.addAction(Actions.moveBy(100, 0, 1));
    }

    public void moveRonaxToPortal(Vector2 pos) {
        ronax.addAction(Actions.moveTo(pos.x, pos.y, 2));
    }
}
