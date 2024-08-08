package de.keeyzar.earthragequit.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.achievements.all.AVars;

/**
 * @author = Keeyzar on 08.03.2016
 */
public class AchievementSaferLoader {
    private final String PREFERENCES = "ACHIEVEMENTS";
    private AchievementVerwalter achievementVerwalter;

    public AchievementSaferLoader(AchievementVerwalter achievementVerwalter){
        this.achievementVerwalter = achievementVerwalter;
    }

    public void safe() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        ObjectMap.Entries<Integer, de.keeyzar.earthragequit.achievements.all.Achievement> achievementMap = achievementVerwalter.getAchievementMap().iterator();
        while (achievementMap.hasNext()){
            ObjectMap.Entry<Integer, de.keeyzar.earthragequit.achievements.all.Achievement> next = achievementMap.next();
            de.keeyzar.earthragequit.achievements.all.Achievement value = next.value;
            prefs.putBoolean("finished_"+next.key, value.isFinished());
        }
        ObjectIntMap.Entries<Integer> iterator = achievementVerwalter.getCounterMap().iterator();
        while(iterator.hasNext()){
            ObjectIntMap.Entry<Integer> next = iterator.next();
            prefs.putInteger("Count_" + next.key, next.value);
        }

        prefs.putFloat("time_played", AVars.TIME_PLAYED);
        prefs.flush();
    }

    public void load() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        ObjectMap.Entries<Integer, de.keeyzar.earthragequit.achievements.all.Achievement> achievementMap = achievementVerwalter.getAchievementMap().iterator();
        while (achievementMap.hasNext()){
            ObjectMap.Entry<Integer, de.keeyzar.earthragequit.achievements.all.Achievement> next = achievementMap.next();
            de.keeyzar.earthragequit.achievements.all.Achievement value = next.value;
            boolean aBoolean = prefs.getBoolean("finished_" + next.key, false);
            value.setFinished(aBoolean);
        }

        AVars.TIME_PLAYED = prefs.getFloat("time_played", 0);

        ObjectIntMap.Entries<Integer> iterator = achievementVerwalter.getCounterMap().iterator();
        while(iterator.hasNext()){
            ObjectIntMap.Entry<Integer> next = iterator.next();
            int value = prefs.getInteger("Count_" + next.key, 0);
            achievementVerwalter.setStat(next.key, value);
        }
    }

    public void reset() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES);
        preferences.clear();
        preferences.flush();
    }
}
