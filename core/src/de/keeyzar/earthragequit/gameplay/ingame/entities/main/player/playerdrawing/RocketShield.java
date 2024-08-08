package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.playerdrawing;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 05.02.2017.
 */
public class RocketShield extends Image {
    private final int SHIELD_HEIGHT = (int) (Player.HEIGHT * 2);
    private AnimSpriteRef animatedSprite;
    private boolean isEnabled = false;

    public RocketShield(){
        setOrigin(SHIELD_HEIGHT / 2, SHIELD_HEIGHT / 2);
        setSize(SHIELD_HEIGHT, SHIELD_HEIGHT);

        TextureRegion[] shield_animation = new TextureRegion[10];
        for (int i = 0; i < 10; i++){
            shield_animation[i] = ERQAssets.T_ATLAS_GAME.findRegion(TextureAtlasVariables.SCHILD, i+1);
        }
        Animation<TextureRegion> shieldAnimation = new Animation<TextureRegion>(0.05f, shield_animation);
        shieldAnimation.setPlayMode(Animation.PlayMode.LOOP);
        animatedSprite = new AnimSpriteRef(shieldAnimation);
        animatedSprite.setSize(SHIELD_HEIGHT, SHIELD_HEIGHT);
        animatedSprite.setPosition(Player.WIDTH / 2 - SHIELD_HEIGHT / 2, Player.HEIGHT / 2 - SHIELD_HEIGHT / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isEnabled){
            animatedSprite.draw(batch, parentAlpha);
            isEnabled = false;
        }
    }

    @Override
    public void act(float delta) {
        if(isEnabled) {
            animatedSprite.update(delta);
        }
    }

    public void enable(boolean enable){
        this.isEnabled = enable;
    }
}
