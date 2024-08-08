package de.keeyzar.earthragequit.custom_ui.planets;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 27.06.2016.
 */
public class PlanetSkillsDialog extends Image {
    public PlanetSkillsDialog(){
        super(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_SKILLSDIALOG));
        getColor().a = 0.6f;
        addAction(Actions.forever(Actions.rotateBy(100, 5)));
        addAction(Actions.forever(Actions.sequence(Actions.alpha(0, 1f), Actions.alpha(0.5f, 5))));
        setSize(512, 512);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(ScreenVariables.SCREEN_WIDTH - getWidth() / 4 * 3,  -getHeight() / 4);
    }
}
