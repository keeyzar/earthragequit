package de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.stat_coll;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.menu.screens.statistics.all_statistics.Statistic;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class Coins_Coll extends Statistic {
    private final ERQGame game;

    public Coins_Coll(ERQGame game){
        super(LANG.format("statistic_collected_coins"));
        this.game = game;
    }

    @Override
    public String getStatValue() {
        return "" + game.getAchievementVerwalter().getStat(AVars.OVERALL_COLLECTED_COINS);
    }
}
