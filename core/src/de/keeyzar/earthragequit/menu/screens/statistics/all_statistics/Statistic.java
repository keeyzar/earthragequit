package de.keeyzar.earthragequit.menu.screens.statistics.all_statistics;

/**
 * abstract class for statistics
 * @author = Keeyzar on 12.04.2016
 */
public abstract class Statistic {
    private final String statisticString;
    public Statistic(String text){
        statisticString = text;
    }

    public String getStatName() {
        return statisticString;
    }

    public abstract String getStatValue();
}
