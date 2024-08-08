package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.entity.RegularSpawning;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.PlayerCalculatedVars;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.WorldUtils;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.interpolating.EntityInterpolator;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;

/**
 * @author = Keeyzar on 12.03.2016
 */
public abstract class BossLevelDefining {
    public Player player;
    private RegularSpawning spawning;
    private boolean storyScreen;

    /**
     * standard player installation (fixed fuel, fixed speed, etc);
     * @param world
     * @param game
     * @param worldUtils
     * @return
     */
    public Player installPlayer(World world, ERQGame game, WorldUtils worldUtils) {
        player = new Player(world, game, worldUtils);
        PlayerCalculatedVars playerCalculatedVars = player.getPlayerCalculatedVars();
        playerCalculatedVars.setFixFuel(2);
        playerCalculatedVars.setFixMaxSpeed(5);
        playerCalculatedVars.setFixSpeed(3);
        return player;
    }

    public Player getPlayer() {
        return player;
    }

    public final void createSpawning(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage) {
        spawning = createSpawn(game, worldUtils, entityInterpolator, bossStage);
    }

    public abstract RegularSpawning createSpawn(ERQGame game, WorldUtils worldUtils, EntityInterpolator entityInterpolator, BossStage bossStage);

    public abstract void checkWinLoose(BossScreen bossScreen);

    public abstract String getBackgroundName();

    public abstract Actor getBossIntroClone(OrthographicCamera camera);

    public RegularSpawning getSpawning(){
        return spawning;
    }

    public BossEnemy getBoss(){
        return getSpawning().getBoss();
    }

    public abstract void setHudSettings(HudStage hudStage);

    /**
     * gives the next level (actual level + 1)
     * @return -1 if no next level is specified
     */
    public abstract int getNextLevel();

    public void dispose(){
        spawning = null;
    }

    public abstract Array<PlayerHasUnlocked> getUnlocks();

    public abstract String getQuestInfo();
}
