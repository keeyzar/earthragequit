package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills;

import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * @author = Keeyzar on 27.01.2017.
 */
public interface IngameSkill {
    /**
     * if this Skill needs to work on act, with delta once per frame HERE it is.
     * @param delta
     */
    void act(float delta);

    /**
     * here is a possibility to activate a skill.
     */
    void activate();

    /**
     * return the skill name {@link de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars}
     * @return
     */
    String getName();

    /**
     * if the object needs instantiation at later points {@link de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar.Ingame_Radar}
     */
    void init(ERQGame game, World world, Player player);
}
