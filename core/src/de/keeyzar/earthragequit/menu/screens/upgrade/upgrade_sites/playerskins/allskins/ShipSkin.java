package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins;

/**
 * Diese Klasse Implementieren und im Skinverwalter eintragen, dann ist ein neuer Skin ingame. GOIL
 * Created by Keeyzar on 10.02.2016.
 */
public interface ShipSkin {
    int getSkinCosts();
    String getSkinDescription();
    boolean isBought();
    int getSkinId();
    void setUnlocked(boolean isUnlocked);
    boolean isUnlocked();
    void setBought(boolean isBought);
}
