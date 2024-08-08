package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.IngameSkill;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;

import java.util.Map;

/**
 * Kümmert sich um jegliche aktive Skills des Player
 * @author = Keeyzar on 04.03.2016
 */
public class PlayerIngameSkills {
    private final Player player;
    private final ERQGame game;
    private Array<IngameSkill> activeSkills;
    private Array<PlayerSkills> skillRegister;

    private World world;
    //MagnetStuff

    public PlayerIngameSkills(Player player, ERQGame game, World world) {
        this.player = player;
        this.game = game;
        this.world = world;

        activeSkills = new Array<IngameSkill>();
        skillRegister = new Array<PlayerSkills>();

        //remove all Skills, which are not in use.
        for(int ix = 1; ix<5; ix++) {
            Map<Integer, PlayerSkills> act_playerskillsMap = game.getSkillsVerwalter().getAct_PlayerskillsMap();
            PlayerSkills playerSkills = act_playerskillsMap.get(ix);
            if(playerSkills != null)skillRegister.add(playerSkills);
            if(playerSkills != null)activeSkills.add(playerSkills.getIngameSkill());
        }
    }

    public void init(){
        for(IngameSkill ingameSkill : activeSkills){
            ingameSkill.init(game, world, player);
        }
    }

    public void activateSkill(int place) {
        PlayerSkills playerSkills = game.getSkillsVerwalter().getAct_PlayerskillsMap().get(place);
        playerSkills.activate(player);
    }

    public void act(float delta, Camera camera) {
        for(IngameSkill ingameSkill : activeSkills){
            ingameSkill.act(delta);
        }
    }

    /**
     * get IngameSkill or null
     * @param skillName is found in (@link {@link SkillsVars)}
     */
    public IngameSkill getActiveIngameSkills(String skillName){
        for(IngameSkill ingameSkill : activeSkills){
            if(ingameSkill.getName().equals(skillName)) {
                return ingameSkill;
            }
        }
        return null;
    }

    public void dispose(){
        for(PlayerSkills playerSkills : skillRegister){
            playerSkills.dispose();
        }
    }
}
