package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class Skill_2 extends Skill {

    public Skill_2(Player player, ERQGame game){
        super(HudVars.SKILL_2, game, player, 2);
        setDisplayName(LANG.format("hud_name_skill_two"));
    }

    @Override
    public void initPosition() {
        int _width = 55;
        int _height = 55;
        int _x = 95;
        int _y = 155;

        setWidth(_width);
        setHeight(_height);
        setX(_x);
        setY(_y);

    }
}
