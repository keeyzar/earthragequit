package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level;

import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.CanSpriteCloning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.DynamicActor;

/**
 * @author = Keeyzar on 12.03.2016
 */
public abstract class BossEnemy extends DynamicActor implements CanSpriteCloning {
    public abstract int getLife();
    public abstract int getMaxLife();


}
