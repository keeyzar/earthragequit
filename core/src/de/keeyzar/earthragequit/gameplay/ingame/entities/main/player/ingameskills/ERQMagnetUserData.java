package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills;

import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents.Magnetic;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.ERQStats;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;

/**
 * @author = Keeyzar on 04.03.2016
 */
public class ERQMagnetUserData {
    Array<Magnetic> magneticActors;
    boolean magnetBags = false;

    public ERQMagnetUserData(ERQGame game){
        magneticActors = new Array<Magnetic>();
        final ERQStats statByName = game.getStatsVerwalter().getStatByName(STAT_VARS.STAT_MAGNETIC_MONEYBAG);
        magnetBags = !statByName.isLocked() && statByName.getCurrentLevel() != 0;
    }

    public void addMagneticActor(Magnetic magneticActor){
        if(!magnetBags){
            if(magneticActor.getId() == EntityVars.MONEYBAG) return;
        }
        magneticActors.add(magneticActor);
    }

    public Array<Magnetic> getMagneticActors() {
        return magneticActors;
    }
}
