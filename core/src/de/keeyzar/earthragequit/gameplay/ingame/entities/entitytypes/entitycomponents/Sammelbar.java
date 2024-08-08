package de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.entitycomponents;

import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 02.03.2016
 */
public interface Sammelbar {
    void destroy();
    void doStuffToPlayer(Player player);
}
