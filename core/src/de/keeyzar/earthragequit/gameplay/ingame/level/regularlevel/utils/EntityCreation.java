package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.Spawner;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;

/**
 * @author = Keeyzar on 04.03.2016
 */
public class EntityCreation {
    public EntityCreation(WorldUtils regularWorld, Stage stage, Player player, GameScreen gameScreen) {
        gameScreen.getLevelDefining().createEntities(regularWorld.getWorld(), stage, player);
        Spawner.releaseWorld();
    }
}
