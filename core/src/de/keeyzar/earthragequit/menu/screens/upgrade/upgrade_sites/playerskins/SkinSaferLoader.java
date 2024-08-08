package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins.ShipSkin;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins.SkinVars;

/**
 * Safes all skin relevant data
 * Created by Keeyzar on 27.04.2016.
 */
public class SkinSaferLoader {
    public static final String PREFS = "SKIN_PREFS";
    private SkinVerwalter skinVerwalter;

    public SkinSaferLoader(SkinVerwalter skinVerwalter) {
        this.skinVerwalter = skinVerwalter;
    }

    /**
     * loads all skins
     */
    public void load() {
        Array<ShipSkin> skinArray = skinVerwalter.getSkinArray();
        Preferences prefs = Gdx.app.getPreferences(PREFS);

        for(ShipSkin shipSkin : skinArray){
            shipSkin.setBought(prefs.getBoolean("SHIP_SKIN_NO" + shipSkin.getSkinId(), false));
            shipSkin.setUnlocked(prefs.getBoolean("SHIP_UNLOCKED_NO" +shipSkin.getSkinId(), false));
        }

        skinVerwalter.setActualSkin(prefs.getInteger("CURR_SKIN", SkinVars.SKIN_DEFAULT));
    }

    public void safe() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        Array<ShipSkin> skinArray = skinVerwalter.getSkinArray();
        for(ShipSkin shipSkin : skinArray){
            prefs.putBoolean("SHIP_SKIN_NO" + shipSkin.getSkinId(), shipSkin.isBought());
            prefs.putBoolean("SHIP_UNLOCKED_NO" + shipSkin.getSkinId(), shipSkin.isUnlocked());
        }
        prefs.putInteger("CURR_SKIN", skinVerwalter.getActualSkin().getSkinId());
        prefs.flush();
    }

    public void reset() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        prefs.clear();
        prefs.flush();
    }
}
