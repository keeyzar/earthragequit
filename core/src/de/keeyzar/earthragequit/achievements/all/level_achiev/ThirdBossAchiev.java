package de.keeyzar.earthragequit.achievements.all.level_achiev;

import de.keeyzar.earthragequit.ERQGame;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 07.02.2017.
 */
public class ThirdBossAchiev extends BossAchiev {
    public ThirdBossAchiev(ERQGame game) {
        super(game, 300);
    }

    @Override
    public String getAchievementText() {
        return LANG.format("boss_3_title");
    }

    @Override
    public String getTitle() {
        return LANG.format("boss_3_title");
    }
}
