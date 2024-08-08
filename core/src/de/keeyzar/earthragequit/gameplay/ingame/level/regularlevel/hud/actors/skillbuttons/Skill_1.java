package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.skillbuttons;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class Skill_1 extends Skill {

    public Skill_1(Player player, ERQGame game){
        super(HudVars.SKILL_1, game, player, 1);
        setDisplayName(LANG.format("hud_name_skill_one"));
    }

    @Override
    public void initPosition() {
        int _width = 50;
        int _height = 50;
        int _x = 15;
        int _y = 200;

        setBounds(_x, _y, _width, _height);
    }
}
