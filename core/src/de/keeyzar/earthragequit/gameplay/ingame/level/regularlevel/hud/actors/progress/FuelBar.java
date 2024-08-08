package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 30.03.2016
 */
public class FuelBar extends ProgBar {
    private Actor warningActor;

    private Color oldColor;

    private ColorAction colorActionToWarning;
    private ColorAction colorActionToColor;

    public FuelBar(final Player player) {
        super(HudVars.FUEL_BAR);
        setDisplayName(LANG.format("hud_name_fuel_bar"));
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public float getProgress() {
                return Math.min(player.getPlayerCalculatedVars().getU_speed_fuel() /
                                player.getPlayerCalculatedVars().getU_speed_fuel_max(), 1f);

            }
        };

        oldColor = Color.GREEN.cpy();
        warningActor = new Actor();
        warningActor.setColor(new Color(oldColor));

        colorActionToColor = Actions.color(oldColor, 0.4f, Interpolation.pow3);
        colorActionToWarning = Actions.color(new Color(1, 0, 0, 1), 0.4f, Interpolation.exp5);

        Action warningAction = Actions.forever(Actions.sequence(colorActionToWarning, colorActionToColor));
        warningActor.addAction(warningAction);
        init(progressListener, Color.GREEN, Color.RED, false);
        setImage(new Image(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_OIL_DROP))));
    }

    @Override
    public void initPosition() {
        int _width = 15;
        int _height = 300;
        int _x = 45;
        int _y = 455;
        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);
    }

    @Override
    public void modifyForeground() {
        if (progress < 0.4f) {
            colorActionToColor.setDuration(0 + progress);
            colorActionToWarning.setDuration(Math.max(0.2f, 0 + progress));
            warningActor.act(Math.min(0.25f, Gdx.graphics.getDeltaTime()));
            foreground.set(warningActor.getColor());
        } else {
            foreground.set(oldColor);
        }
    }
}
