package de.keeyzar.earthragequit.custom_ui.planets;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 27.06.2016.
 */
public class PlanetLevelSelectScreen extends Image {
    public PlanetLevelSelectScreen(){
        super(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_LEVELSELECT));
        getColor().a = 0.3f;
        addAction(Actions.forever(Actions.rotateBy(360, 5)));
        addAction(Actions.forever(Actions.sequence(Actions.alpha(0, 1f), Actions.alpha(0.3f, 5))));
        setSize(200, 200);
        setOrigin(getWidth() / 2, getHeight() / 2);
        final int sW = ScreenVariables.SCREEN_WIDTH;
        final int sH = ScreenVariables.SCREEN_HEIGHT;
        setPosition(-getWidth() / 2,  -getHeight() / 2);
        int timePerSide = 4;
        addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, sH, timePerSide),
                Actions.moveBy(sW, 0, timePerSide),
                Actions.moveBy(0, -sH, timePerSide),
                Actions.moveBy(-sW, 0, timePerSide))));
    }
}
