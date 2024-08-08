package de.keeyzar.earthragequit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import de.keeyzar.earthragequit.saving.Safeable;

/**
 * saves some global vars, like coins, megaboost multiplicator, gems, gems multiplicator
 * @author Keeyzar on 10.02.2016
 */
public class GlobalPlayerInformation implements Safeable {
    private int coins;
    private int gems; // for lottery
    private static final String PREFS = "PlayerPrefState";
    //hidden feature
    private int megaBoostMultiplikator = 2;
    private int gemMultiplicator = 1;
    private int showUpgradeCounter = 0;
    private int freeUpgrade = 0;
    private String defaultLang = "";
    private String PREF_LANG = "defaultLang";

    public GlobalPlayerInformation(){
        loadState();
    }


    public void loadState(){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        gems = prefs.getInteger("GG", 0);
        gemMultiplicator = prefs.getInteger("GM", 1);

        coins = prefs.getInteger("CC", 0);
        megaBoostMultiplikator = prefs.getInteger("MBM", 2);
        showUpgradeCounter = prefs.getInteger("UpgradeCounter", 0);
        freeUpgrade = prefs.getInteger("freeUpgrades", 0);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void addFreeUpgrades(){
        freeUpgrade++;
    }

    public int getFreeUpgrades(){
        return freeUpgrade;
    }

    public void removeOneFreeUpgrade(){
        freeUpgrade--;
    }

    @Override
    public void save() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putInteger("CC", coins);
        prefs.putInteger("MBM", megaBoostMultiplikator);

        prefs.putInteger("GG", gems);
        prefs.putInteger("GM", gemMultiplicator);
        prefs.putInteger("UpgradeCounter", showUpgradeCounter);
        prefs.putInteger("freeUpgrades", freeUpgrade);
        prefs.flush();
    }

    @Override
    public void reset() {
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        preferences.clear();
        preferences.flush();
        loadState();
    }

    public int getMegaBoostMultiplikator() {
        return megaBoostMultiplikator;
    }

    public void saveOnOwn(Class whichClass, String whichPref, int value){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putInteger(whichClass.toString()+whichPref, value);
        prefs.flush();
    }

    public void saveOnOwn(Class whichClass, String whichPref, String value){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putString(whichClass.toString()+whichPref, value);
        prefs.flush();
    }

    public void saveOnOwn(Class whichClass, String whichPref, boolean value){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putBoolean(whichClass.toString()+whichPref, value);
        prefs.flush();
    }

    public void saveOnOwn(Class whichClass, String whichPref, float value){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.putFloat(whichClass.toString()+whichPref, value);
        prefs.flush();
    }

    public int loadOnOwn(Class whichClass, String whichPref, int defaultValue){
        final Preferences preferences = Gdx.app.getPreferences(PREFS);
        return preferences.getInteger(whichClass+whichPref, defaultValue);
    }

    public String loadOnOwn(Class whichClass, String whichPref, String defaultValue){
        final Preferences preferences = Gdx.app.getPreferences(PREFS);
        return preferences.getString(whichClass+whichPref, defaultValue);
    }

    public boolean loadOnOwn(Class whichClass, String whichPref, boolean defaultValue){
        final Preferences preferences = Gdx.app.getPreferences(PREFS);
        return preferences.getBoolean(whichClass+whichPref, defaultValue);
    }

    public float loadOnOwn(Class whichClass, String whichPref, float defaultValue){
        final Preferences preferences = Gdx.app.getPreferences(PREFS);
        return preferences.getFloat(whichClass+whichPref, defaultValue);
    }

    public void setDefaultLang(String langCode){
        defaultLang = langCode;
        saveOnOwn(getClass(), PREF_LANG, defaultLang);
    }

    public String getDefaultLang() {
        defaultLang = loadOnOwn(getClass(), PREF_LANG, "");
        return defaultLang;
    }
}
