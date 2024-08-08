package de.keeyzar.earthragequit.custom_ui.planets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * @author = Keeyzar on 29.06.2016.
 */
public class PlanetMainMenu extends Image {


    public PlanetMainMenu() {
        super(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.PLANET_MAINMENU));
        setSize(512, 512);
        addAction(Actions.forever(Actions.sequence(Actions.sizeTo(600, 600, 2, Interpolation.bounce),
                Actions.sizeTo(512, 512, 3, Interpolation.linear))));
        addAction(Actions.forever(Actions.rotateBy(200, 10)));
        makePositionStuff();
        getColor().a = 0.3f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        makePositionStuff();
    }

    private void makePositionStuff() {
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(0, ScreenVariables.SCREEN_HEIGHT - getHeight() / 4 * 3);
    }
}
