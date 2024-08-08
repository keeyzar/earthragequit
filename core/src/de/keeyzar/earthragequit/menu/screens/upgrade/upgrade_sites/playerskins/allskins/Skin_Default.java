package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskins.allskins;

/**
 * @author = Keeyzar on 10.02.2016.
 */
public class Skin_Default extends Ship_Skin {

    public Skin_Default() {
        super(SkinVars.SKIN_DEFAULT,0);
        this.bought = true;
    }

    @Override
    public String getSkinDescription() {
        return "This ship is the default ship, as you expected. You can use it all the time";
    }

    @Override
    public void setBought(boolean bought) {
        //do nothing, because is always bought!
    }
}
