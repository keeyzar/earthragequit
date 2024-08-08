package de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_lostGame;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.Statistic;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class Death_Statistic extends Statistic {

    private ERQGame game;

    public Death_Statistic(ERQGame game) {
        super(LANG.format("statistic_lost_deaths"));
        this.game = game;
    }

    @Override
    public String getStatValue() {
        return LANG.format("statistic_lost_deaths_amount", game.getAchievementVerwalter().getStat(AVars.COUNTER_DEATH));
    }
}
