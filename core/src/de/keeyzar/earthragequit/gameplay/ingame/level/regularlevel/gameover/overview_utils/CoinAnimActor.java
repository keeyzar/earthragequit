package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.gameover.overview_utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 22.01.2017.
 */
public class CoinAnimActor extends Image {
    private Animation<TextureRegion> animation;
    private SpriteDrawable drawable;
    private Sprite sprite;
    private float stateTime;
    private TextureRegion currentFrame;

    public CoinAnimActor(){
        super();
        sprite = new Sprite();
        sprite.setSize(getWidth(), getHeight());
        drawable = new SpriteDrawable();
        stateTime = 0f;

        TextureRegion[] anim = new TextureRegion[6];
        for (int i = 0; i < anim.length; i++){
            anim[i] = ERQAssets.T_ATLAS_GAME.findRegion(TextureAtlasVariables.COIN, i+1);
        }
        animation = new Animation<TextureRegion>(0.07f, anim);

        stateTime += MathUtils.random(0, 1);
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setSize(getWidth(), getHeight());
        drawable.setSprite(sprite);
        setDrawable(drawable);
    }
}
