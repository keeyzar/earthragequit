package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossEnemy;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class EnemyLifeBar extends ProgBar {
    BossEnemy bossEnemy;

    public EnemyLifeBar(final BossEnemy bossEnemy){
        super(HudVars.ENEMY_LIFE_BAR);
        setDisplayName(LANG.format("hud_name_enemy_life_bar"));
        this.bossEnemy = bossEnemy;
        init(new ProgressListener() {
            @Override
            public float getProgress() {
                return Math.min((float)bossEnemy.getLife() / (float)bossEnemy.getMaxLife(), 1);
            }
        }, Color.GREEN, Color.GRAY, true);

        setImage(new Image(new SpriteDrawable(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_HEART))));
    }

    @Override
    public void initPosition() {
        int _width = 300;
        int _height = 20;

        int _x = 400;
        int _y = 765;
        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);
    }
}
