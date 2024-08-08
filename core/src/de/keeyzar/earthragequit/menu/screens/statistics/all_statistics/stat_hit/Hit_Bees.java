package de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_hit;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.Statistic;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class Hit_Bees extends Statistic {
    private final ERQGame game;

    public Hit_Bees(ERQGame game){
        super(LANG.format("statistic_hitted_stuff_flyfly"));
        this.game = game;
    }

    @Override
    public String getStatValue() {
        return LANG.format("statistic_hitted_stuff_flyfly_amount", game.getAchievementVerwalter().getStat(AVars.OVERALL_HIT_BEES));
    }
}
