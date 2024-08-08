package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities.Stone;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.bosses.boss3.entities.StoneClone;
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
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Galaxy2Unlocker;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.tutorial.TVars;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

import static de.keeyzar.earthragequit.achievements.all.AVars.BOSS_3_KILLED;
import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 26.11.2016.
 */
public class Boss3_Defined extends BossLevelDefining {
    private ERQGame game;
    private DelayAction delayAction;
    private Boss3_Spawning boss3_spawning;

    @Override
    public RegularSpawning createSpawn(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        this.game = game;
        delayAction = new DelayAction(150);
        bossStage.addAction(delayAction);
        boss3_spawning = new Boss3_Spawning(game, worldUtils, entityInterpolator, bossStage, player);
        return boss3_spawning;
    }

    public void checkWinLoose(BossScreen bossScreen){
        //FIXME REMOVE BEFORE RELEASE
        if(Gdx.input.isKeyJustPressed(Input.Keys.M) || Gdx.input.isTouched(4)){
            game.getMusicHandler().playMusic(MusicHandler.LEVEL_WON_MUSIC);
            game.getAchievementVerwalter().getAchievementMap().get(BOSS_3_KILLED).checkConditionsAndApplyIfTrue();
            game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.NEW_DIMENSION, true);
            bossScreen.startBeforeEnd(true);
        }
        if(boss3_spawning.gameWon()){
            game.getMusicHandler().playMusic(MusicHandler.LEVEL_WON_MUSIC);
            game.getAchievementVerwalter().getAchievementMap().get(BOSS_3_KILLED).checkConditionsAndApplyIfTrue();
            game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.NEW_DIMENSION, true);
            bossScreen.startBeforeEnd(true);
        }
        if(player.getPlayerCalculatedVars().getLife() <= 0){
            bossScreen.startBeforeEnd(false);
        }
        if(delayAction.getTime() >= delayAction.getDuration()){
            bossScreen.startBeforeEnd(false);
        }
    }

    public String getBackgroundName(){
        return LevelVars.LEVEL_GREEN;
    }

    @Override
    public Actor getBossIntroClone(OrthographicCamera camera) {
        return new StoneClone((Stone)getBoss(), camera);
    }

    @Override
    public void setHudSettings(HudStage hudStage) {
        hudStage.setEnemyProgressBarEnabled(new BossProgress() {

            @Override
            public float getCurrentProgress() {
                return delayAction.getDuration() - delayAction.getTime();
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
        hudStage.enable(false, false, false);
        hudStage.setRadarAllow(false);
        hudStage.createSteuerung();
    }

    @Override
    public int getNextLevel() {
        return -1;
    }

    @Override
    public Array<PlayerHasUnlocked> getUnlocks() {
        final Array<PlayerHasUnlocked> playerHasUnlockeds = new Array<PlayerHasUnlocked>();
        playerHasUnlockeds.add(new Galaxy2Unlocker(game));
        return playerHasUnlockeds;

    }

    @Override
    public Player installPlayer(World world, ERQGame game, WorldUtils worldUtils) {
        player = new Player(world, game, worldUtils);
        PlayerCalculatedVars playerCalculatedVars = player.getPlayerCalculatedVars();
        playerCalculatedVars.setFixFuel(2);
        return player;
    }

    @Override
    public void dispose() {
        super.dispose();
        boss3_spawning = null;
    }

    @Override
    public String getQuestInfo() {
        return LANG.format("3_boss_quest");
    }
}
