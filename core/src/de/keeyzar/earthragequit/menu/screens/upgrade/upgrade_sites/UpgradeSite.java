package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * is used in the UpgradeScreen
 * Created by Keeyzar on 27.04.2016.
 */
public interface UpgradeSite {
    void setSite(Table siteContainer, Label title);
    void updateAll();
}
