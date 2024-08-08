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
 * @author = Keeyzar on 28.02.2016
 */
public class LifeBar extends ProgBar {
    Player player;
    Actor warningActor;
    private Color oldColor;
    private Color warningColor;
    ColorAction colorActionToWarning;
    ColorAction colorActionToColor;


    public LifeBar(final Player player){
        super(HudVars.LIFE_BAR);
        setDisplayName(LANG.format("hud_name_life_bar"));
        this.player = player;

        oldColor = Color.GREEN.cpy();
        warningColor = Color.RED.cpy();
        warningActor = new Actor();
        warningActor.setColor(oldColor.cpy());

        colorActionToColor = Actions.color(oldColor, 0.4f, Interpolation.pow3);
        colorActionToWarning = Actions.color(warningColor, 0.4f, Interpolation.exp5);

        Action warningAction = Actions.forever(Actions.sequence(colorActionToWarning, colorActionToColor));
        warningActor.addAction(warningAction);


        init(new ProgressListener() {
            @Override
            public float getProgress() {
                return Math.min(player.getPlayerCalculatedVars().getLife() / player.getPlayerCalculatedVars().getLifeMax(), 1f);
            }
        }, Color.GREEN, Color.GRAY, true);
        setImage(new Image(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_HEART))));
    }

    @Override
    public void initPosition() {
        int _width = 300;
        int _height = 20;
        int _x = 50;
        int _y = 765;

        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);
    }


    @Override
    public void modifyForeground() {
        if (progress <= 0.33f) {
            colorActionToColor.setDuration(0 + progress);
            colorActionToWarning.setDuration(Math.max(0.2f, 0 + progress));
            warningActor.act(Math.min(0.25f, Gdx.graphics.getDeltaTime()));
            foreground.set(warningActor.getColor());
        } else {
            foreground.set(oldColor);
        }
    }


}
