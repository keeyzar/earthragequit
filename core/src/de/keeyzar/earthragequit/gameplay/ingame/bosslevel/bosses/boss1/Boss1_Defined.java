package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.BossWaspIntroClone;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss1.entities.BossWaspMother;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level3Unlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.ShieldUnlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.SkillPlace1Unlocker;
import de.keeyzar.earthragequit.sound.MusicHandler;

import static de.keeyzar.earthragequit.achievements.all.AVars.BOSS_1_KILLED;
import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Boss1_Defined extends BossLevelDefining {
    ERQGame game;

    @Override
    public RegularSpawning createSpawn(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        this.game = game;
        return new Boss1_Spawning(game, worldUtils, entityInterpolator, bossStage);
    }

    public void checkWinLoose(BossScreen bossScreen){
        if(getBoss().getLife() <= 0){
            game.getAchievementVerwalter().getAchievementMap().get(BOSS_1_KILLED).checkConditionsAndApplyIfTrue();
            game.getMusicHandler().playMusic(MusicHandler.LEVEL_WON_MUSIC);
            bossScreen.startBeforeEnd(true);
        }
    }


    public String getBackgroundName(){
        return LevelVars.LEVEL_GREEN;
    }

    @Override
    public Actor getBossIntroClone(OrthographicCamera camera) {
        return new BossWaspIntroClone((BossWaspMother)getBoss());
    }

    @Override
    public void setHudSettings(HudStage hudStage) {
        hudStage.setEnemyLifeEnabled(getBoss());
        hudStage.enable(false, false, false);
        hudStage.createSteuerung();
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocks() {
        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
        unlockeds.add(new SkillPlace1Unlocker(game));
        unlockeds.add(new ShieldUnlocker(game));
        unlockeds.add(new Level3Unlocker(game));
        return unlockeds;
    }

    @Override
    public int getNextLevel() {
        return 3;
    }

    @Override
    public void dispose() {
        super.dispose();
        //do Nothing
    }

    @Override
    public String getQuestInfo() {
        return  LANG.format("1_boss_quest");
    }
}
