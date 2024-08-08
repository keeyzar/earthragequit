package de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.UpgradeMainDialog;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class SkillPlace1Unlocker extends Unlock{
    private ERQGame game;

    public SkillPlace1Unlocker(ERQGame game){
        this.game = game;
        unlockTitel = "unlock_skillplace_1_title";
        unlockerText = "You unlocked your first skill place. Look at the upgrades, there's new stuff!";
    }

    @Override
    public void unlock() {
        game.getSkillsVerwalter().unlock(1);
        if(!game.getTutorialVerwalter().isAbsolved(TVars.SKILLS_INTRODUCTION)) {
            game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.SKILLS_INTRODUCTION, true);
        }
    }

    @Override
    public boolean isUnlocked() {
        return !game.getSkillsVerwalter().isLocked(1);
    }

    @Override
    public boolean shouldBeDisplayed() {
        return true;
    }

    @Override
    public Sprite getImage() {
        return ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.SKILL_PLACE);
    }

    @Override
    public int getType() {
        return UpgradeMainDialog.SITE_SKILLS;
    }
}
