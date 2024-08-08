package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.starting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class StartingBoostBalken extends Actor {
    NinePatch foreground;
    NinePatch background;


    public StartingBoostBalken(){
        setWidth(800);
        setHeight(50);
        setX(1024/2 - getWidth() / 2);
        setY(800/2 - getHeight() / 2);
        init();
    }

    private void init() {
        foreground = new NinePatch(ERQAssets.T_ATLAS_GAME.createPatch(TextureAtlasVariables.STARTING_BALKEN_FOREGROUND));
        background = new NinePatch(ERQAssets.T_ATLAS_GAME.createPatch(TextureAtlasVariables.STARTING_BALKEN_BACKGROUND));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, getX() - 20, getY() - 20, getWidth() + 40, getHeight() + 40);
        foreground.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
