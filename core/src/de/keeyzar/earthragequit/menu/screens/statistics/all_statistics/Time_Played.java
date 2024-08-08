package de.keeyzar.earthragequit.menu.screens.statistics.all_statistics;

import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.assets.ERQAssets;

/**
 * @author = Keeyzar on 12.04.2016
 */
public class Time_Played extends Statistic {

    public Time_Played() {
        super(ERQAssets.LANG.format("statistic_time_play_time"));
    }

    @Override
    public String getStatValue() {
        int seconds = (int) AVars.TIME_PLAYED;

        int hours = seconds / 60 / 60;
        int minutes = (seconds - hours * 60 * 60) / 60 ;
        seconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
