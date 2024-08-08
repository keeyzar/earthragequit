package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelDescription;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * If you want to create a normal map, you need to implement this Methods, and give it
 * to
 * @author = Keeyzar on 10.03.2016
 */
public abstract class LevelDefining {
    protected ERQGame game;
    protected LevelDescription levelDescription;

    public LevelDefining(){
        levelDescription = getLevelDescription();
    }

    public void initLevel(ERQGame game){
        this.game = game;
    }

    private Array<Pool> allPools;
    {
        allPools = new Array<Pool>();
    }

    public void addToPool(Pool pool){
        this.allPools.add(pool);
    }

    /**
     * the levelName
     * @return the name of the level
     */
    public String getLevelName(){
       return getLevelDescription().levelName;
    }

    /**
     * which level it is.
     * @return integer of level (1..2..3.. etc)
     */
    public int getLevel(){
        return getLevelDescription().level;
    }

    /**
     * the Path to the tmx level. Not the tmx level
     * @return the level TMX path
     */
    public String getLevelTmxPath(){
        return getLevelDescription().levelTmxPath;
    }

    /**
     * the entities must be created here. Watch for implementations to see how it's done
     * @param world in which the entities should be created
     * @param stage where the entities are added in order to be drawn
     * @param player with which all entities should interact
     *
     */
    public abstract void createEntities(World world, Stage stage, Player player);

    /**
     * return the world width;
     * @return worldWidth
     */
    public int getWorldWidth(){
        return getLevelDescription().width;
    }

    /**
     * return the wolrd height
     * @return world height
     */
    public int getWorldHeight(){
        return getLevelDescription().height;
    }

    /**
     * MUST NOT BE implemented. If you wanna set a fix player state, then it's time for this
     * if not.. leave empty
     * @param player
     */
    public void modifyPlayerStats(Player player){

    }

    /**
     * can be null, if it's null, and the player want to enter the bossfight the standart bossEntityActors are used
     * @return null or allActorsWithChainedActions
     */
    public Array<Actor> getActorArrayFromBeforeBossFight(){
        return null;
    }

    /**
     * can be null, if it's null, and the player want to enter the bossfight, he'll go to the menu
     * @return null or a BossLevel
     */
    public Transitionable getBossLevel(){
        final BossLevelDefining bossLevel = getLevelDescription().getBossLevel();
        if(bossLevel != null){
           return new BossScreen(game, bossLevel);
        }
        return null;
    }

    /**
     * can be null, if a boss is given
     * @param game
     * @return
     */
    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game){
        return null;
    }

    /**
     * Must NOT be implemented.
     * If you want to modify the hud for the gameplay, override this
     * if not.. just leave it empty.
     * @param hudStage to modify
     */
    void modifyHudStage(HudStage hudStage){

    }

    boolean isGameOverAllowed() {
        return true;
    }

    void gameOverStuff(GameScreen gameScreen) {
        //nothing
    }

    /**
     * If you want to add some explaining text to the gamescreen
     * than override this, and give a talking stage back.THX.
     * AND dont forget to override talking actAndDraw, too. or a null pointer is thrown
     */
    protected TalkingStage getTalkingStage(){
        return null;
    }


    /**
     * If getTalkingStage returns a talkingStage, then the acting must be done
     * in this method here.
     */
    protected void talkingAct(float delta){
        //do noddin'hill
    }

    /**
     * If you want to disable the starting boost for whatever you want.
     */
    public boolean isStartingBoostDisabled() {
        return false;
    }


    /**
     * A hook, if the player died, and it's game over, you'll get a methodcall here
     * @param gameScreen if you want to set another screen.
     */
    public void additionalInfosForGameOver(GameScreen gameScreen) {
        //do nothing
    }

    public Vector2 getLevGravity(){
        return new Vector2(0, -10);
    }

    /**
     * if you need to dispose something, do it here
     */
    public void dispose(){
        allPools.clear();
    }


    /**
     * callback, when the game is won
     */
    public void playerFinishedMap(){}

    public abstract LevelDescription getLevelDescription();

    public String getQuestInfo() {
        if(getLevelDescription().level <= 3) {
            return  LANG.format("quest_default");
        }
        return "";
    }
}
