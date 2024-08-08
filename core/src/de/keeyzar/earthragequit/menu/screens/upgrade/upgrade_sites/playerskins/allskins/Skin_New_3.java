package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins;

/**
 * @author = Keeyzar on 10.02.2016.
 */
public class Skin_New_3 extends Ship_Skin{

    public Skin_New_3() {
        super(SkinVars.SKIN_3, 0);
    }

    @Override
    public String getSkinDescription() {
        return "So crazy and coul, yeah";
    }

    @Override
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
