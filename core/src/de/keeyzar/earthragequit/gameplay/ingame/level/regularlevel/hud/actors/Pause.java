package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class Pause extends HudActor {
    private final PauseListener pauseListener;
    Sprite sprite;

    public Pause(PauseListener pauseListener){
        super(HudVars.PAUSE);
        setDisplayName(LANG.format("hud_name_pause_button"));
        this.pauseListener = pauseListener;
        init();
    }

    private void init() {
        this.sprite = ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PAUSE);
        sprite.setSize(getWidth(), getHeight());
        sprite.setPosition(getX(), getY());
    }

    private void pauseGame() {
        pauseListener.pauseClicked();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setPosition(getX(), getY());
        sprite.setSize(getWidth(), getHeight());
        sprite.setColor(getColor());
    }

    @Override
    public void normalMode() {
        super.normalMode();
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pauseGame();
                return false;
            }
        });
    }

    @Override
    public void initPosition() {
        int _width = 75;
        int _height = 75;
        int _x = HudVars.HUD_WIDTH - (int)(_width * 1.4f);
        int _y = HudVars.HUD_HEIGHT - (int)(_height * 1.4f);

        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);

    }
}
