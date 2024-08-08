package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.all.AVars;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.Balthazar;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss2.entities.BalthazarClone;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossStage;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra.BossProgress;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.PlayerCalculatedVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level5Unlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.MegaboostUnlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.ShieldUnlocker;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.SkillPlace2Unlocker;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.achievements.all.AVars.BOSS_2_KILLED;
import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class Boss2_Defined extends BossLevelDefining {
    private ERQGame game;
    private DelayAction delayAction;

    @Override
    public RegularSpawning createSpawn(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        this.game = game;
        delayAction = new DelayAction(40);
        bossStage.addAction(delayAction);
        return new Boss2_Spawning(game, worldUtils, entityInterpolator, bossStage);
    }

    public void checkWinLoose(BossScreen bossScreen){
        if(player.getPlayerCalculatedVars().getLife() <= 0){
            bossScreen.startBeforeEnd(false);
            game.getAchievementVerwalter().addStat(AVars.COUNTER_DEATH, 1);
        }
        if(delayAction.getTime() >= delayAction.getDuration()){
            game.getMusicHandler().playMusic(MusicHandler.LEVEL_WON_MUSIC);
            game.getAchievementVerwalter().getAchievementMap().get(BOSS_2_KILLED).checkConditionsAndApplyIfTrue();
            bossScreen.startBeforeEnd(true);
        }
    }

    public String getBackgroundName(){
        return LevelVars.LEVEL_GREEN;
    }

    @Override
    public Actor getBossIntroClone(OrthographicCamera camera) {
        return new BalthazarClone((Balthazar)getBoss(), camera);
    }

    @Override
    public void setHudSettings(HudStage hudStage) {
        hudStage.setEnemyProgressBarEnabled(new BossProgress() {

            @Override
            public float getCurrentProgress() {
                return delayAction.getTime();
            }

            @Override
            public float getMaxProgress() {
                return delayAction.getDuration();
            }

            @Override
            public Image getImage() {
                return new Image(ERQAssets.T_ATLAS_GAME.createSprite(TextureAtlasVariables.ICON_FINISH_LINE));
            }
        });
        hudStage.enable(true, false, false);
        hudStage.createSteuerung();
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocks() {
        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
        unlockeds.add(new MegaboostUnlocker(game));
        unlockeds.add(new ShieldUnlocker(game));
        unlockeds.add(new SkillPlace2Unlocker(game));
        unlockeds.add(new Level5Unlocker(game));
        return unlockeds;
    }

    @Override
    public Player installPlayer(World world, ERQGame game, WorldUtils worldUtils) {
        player = new Player(world, game, worldUtils);
        PlayerCalculatedVars playerCalculatedVars = player.getPlayerCalculatedVars();
        playerCalculatedVars.setFixFuel(2);
        playerCalculatedVars.setFixMaxSpeed(5);
        playerCalculatedVars.setFixSpeed(3);
        playerCalculatedVars.setFixRotation(1f);
        return player;
    }

    @Override
    public int getNextLevel() {
        return 5;
    }

    @Override
    public void dispose() {
        super.dispose();
        //do Nothing
    }

    @Override
    public String getQuestInfo() {
        return LANG.format("2_boss_quest");
    }
}
