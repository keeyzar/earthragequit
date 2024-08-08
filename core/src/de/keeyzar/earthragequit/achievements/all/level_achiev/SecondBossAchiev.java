package de.keeyzar.earthragequit.achievements.all.level_achiev;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementVerwalter;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 07.02.2017.
 */
public class SecondBossAchiev extends BossAchiev {
    public SecondBossAchiev(ERQGame game) {
        super(game, 100);
    }

    @Override
    public String getAchievementText() {
        return LANG.format("boss_2_title");
    }

    @Override
    public String getTitle() {
        return LANG.format("boss_2_title");
    }
}
