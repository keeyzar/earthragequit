package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress;

import com.badlogic.gdx.graphics.Color;
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
public class RegularProgressBar extends ProgBar {
    Player player;

    public RegularProgressBar(final Player player){
        super(HudVars.REGULAR_PROGRESSBAR);
        setDisplayName(LANG.format("hud_name_regular_progressbar"));
        this.player = player;
        init(new ProgressListener() {

            @Override
            public float getProgress() {
                return Math.min(player.body.getPosition().y / player.getRegularWorld().getWorldHeigth(), 1f);
            }
        }, Color.GREEN, Color.GRAY, false);
        setImage(new Image(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_FINISH_LINE))));
    }

    @Override
    public void initPosition() {
        int _width = 15;
        int _height = 300;

        int _x = 10;
        int _y = 425;
        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);
    }
}
