package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins;

/**
 * If you want to create a hidden Ship Skin do the following:
 * override "isUnlocked" as default getter and setter.
 * @author = Keeyzar on 10.02.2016.
 */
public abstract class Ship_Skin implements ShipSkin {
    public final int id;
    final int costs;
    boolean bought = false;
    boolean unlocked = true;

    public Ship_Skin(int id, int costs){
        this.id = id;
        this.costs = costs;
    }

    @Override
    public int getSkinCosts() {
        return costs;
    }

    @Override
    public boolean isBought() {
        return bought;
    }

    @Override
    public int getSkinId() {
        return id;
    }

    /**
     * if you want to make your skin unlockable, override this method+-
     */
    @Override
    public void setUnlocked(boolean unlocked) {
        //do Nothing, because majority is always unlocked
    }

    @Override
    public boolean isUnlocked() {
        return unlocked;
    }


    /**
     * If you want to create a default ship Skin, that's always bought, override this method, and do nothing.
     */
    @Override
    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
