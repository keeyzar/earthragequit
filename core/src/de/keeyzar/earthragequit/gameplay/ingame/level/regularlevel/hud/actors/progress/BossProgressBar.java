package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.progress;

import com.badlogic.gdx.graphics.Color;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra.BossProgress;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class BossProgressBar extends ProgBar {
    BossProgress bossProgress;

    public BossProgressBar(final BossProgress bossProgress){
        super(HudVars.BOSSPROGRESS);
        this.bossProgress = bossProgress;
        init(new ProgressListener() {

            @Override
            public float getProgress() {
                return Math.min(bossProgress.getCurrentProgress() / bossProgress.getMaxProgress(), 1);
            }
        }, Color.RED, Color.GRAY, true);

        setImage(bossProgress.getImage());
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
