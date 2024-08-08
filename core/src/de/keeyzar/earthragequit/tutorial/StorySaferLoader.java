package de.keeyzar.earthragequit.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.github.czyzby.kiwi.util.tuple.mutable.MutableTriple;

import java.util.Iterator;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class StorySaferLoader {
    private final String PREFS = "TUTORIAL";
    private TutorialVerwalter tutorialVerwalter;

    public StorySaferLoader(TutorialVerwalter tutorialVerwalter){
        this.tutorialVerwalter = tutorialVerwalter;
    }

    public void safe() {
        Preferences preferences = Gdx.app.getPreferences(PREFS);

        Iterator<MutableTriple<Integer, Boolean, Boolean>> iter = tutorialVerwalter.getStorys().iterator();
        while(iter.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iter.next();
            preferences.putBoolean("TUT_ABSOLVED_" + next.getFirst(), next.getSecond());
            preferences.putBoolean("TUT_SHOULD_ABSOLVE_" + next.getFirst(), next.getThird());
        }
        preferences.flush();
    }


    public void load() {
        Preferences preferences = Gdx.app.getPreferences(PREFS);

        Iterator<MutableTriple<Integer, Boolean, Boolean>> iter = tutorialVerwalter.getStorys().iterator();
        while(iter.hasNext()){
            MutableTriple<Integer, Boolean, Boolean> next = iter.next();
            boolean bShouldAbsolve;
            if(next.getFirst() == TVars.TUTORIAL){
                bShouldAbsolve = preferences.getBoolean("TUT_SHOULD_ABSOLVE_" + next.getFirst(), true);
            } else {
                bShouldAbsolve = preferences.getBoolean("TUT_SHOULD_ABSOLVE_" + next.getFirst(), false);
            }
            boolean bAbsolved = preferences.getBoolean("TUT_ABSOLVED_" + next.getFirst(), false);
            next.setSecond(bAbsolved);
            next.setThird(bShouldAbsolve);
        }
    }

    public void reset() {
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        preferences.clear();
        preferences.flush();
        load();
    }
}
