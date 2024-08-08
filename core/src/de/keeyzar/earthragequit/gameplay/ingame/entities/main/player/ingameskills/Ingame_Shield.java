package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills;

import com.badlogic.gdx.physics.box2d.World;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

/**
 * @author = Keeyzar on 23.03.2016
 */
public class Ingame_Shield implements IngameSkill{
    private Player player;

    private boolean activated = false;
    private boolean over = false;

    private float shieldTime;
    private float actualTime;

    @Override
    public void init(ERQGame game, World world, Player player) {
        this.player = player;
        shieldTime = game.getSkillsVerwalter().getSkillByName(SkillsVars.SHIELD).getBoostForCurrentLevel();
        actualTime = shieldTime;
    }



    public void act(float delta){
        if(!activated || over) return;
        actualTime -= delta;
        if(actualTime <= 0f){
            over = true;
        }

    }

    @Override
    public void activate() {
        player.getPlayerCollision().createShield(shieldTime);
        activated = true;
    }

    @Override
    public String getName() {
        return SkillsVars.SHIELD;
    }

    public float getProgress() {
        if(!activated) return 1f;
        if(over) return 0f;
        return actualTime / shieldTime;
    }
}
