package de.keeyzar.earthragequit.custom_ui.planets;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 27.06.2016.
 */
public class PlanetUpgradeSite extends Image {
    public PlanetUpgradeSite(){
        super(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_UPGRADESITE));
        getColor().a = 0.6f;
        addAction(Actions.forever(Actions.rotateBy(50, 5)));
        setSize(500, 500);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(ScreenVariables.SCREEN_WIDTH - getWidth() / 4 * 3, ScreenVariables.SCREEN_HEIGHT - getHeight() / 2);
    }
}
