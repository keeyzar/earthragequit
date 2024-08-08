package de.keeyzar.earthragequit.story.regular_story.first_meeting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 12.02.2017.
 */
public class RonaxAndAl extends Actor {

    private Actor ronax;
    private Sprite ronaxSprite;

    private Actor al;
    private Sprite alSprite;

    public RonaxAndAl() {
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
        al.setBounds(600, 400, 50, 100);
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
        ronax.setBounds(500, 400, 50, 100);

        ronaxSprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.STORY_ALIEN);
    }

}
