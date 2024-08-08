//package de.keeyzar.earthragequit.gameplay.ingame.level.levels.level10;
//
//import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.utils.Array;
//import de.keeyzar.earthragequit.ERQGame;
//import Transitionable;
//import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
//import de.keeyzar.earthragequit.gameplay.ingame.level.levels.LevelVars;
//import de.keeyzar.earthragequit.gameplay.ingame.level.levels.level1.finishedActor.FinishedOne;
//import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.LevelDefining;
//import de.keeyzar.earthragequit.gameplay.ingame.unlocks.PlayerHasUnlocked;
//import de.keeyzar.earthragequit.gameplay.ingame.unlocks.unlockable.Level6Unlocker;
//
///**
// * @author = Keeyzar on 10.03.2016
// *
// */
//public class Level10 extends LevelDefining {
//    private final String levelName = LevelVars.LEV_10;
//    private final int level = 10;
//    private final String levelTmxPath = LevelVars.BACKGROUND_TMX_10;
//    private final int worldHeight = 800;
//    private final int worldWidth = 100;
//    private final FinishedOne finOne;
//    private final Level10EntitieCreator level10EntitieCreator;
//    private ERQGame game;
//
//    public Level10(ERQGame game) {
//        this.game = game;
//        finOne = new FinishedOne();
//        level10EntitieCreator = new Level10EntitieCreator();
//    }
//
//
//    @Override
//    public String getLevelName() {
//        return levelName;
//    }
//
//    @Override
//    public int getLevel() {
//        return level;
//    }
//
//    @Override
//    public String getLevelTmxPath() {
//        return levelTmxPath;
//    }
//
//    @Override
//    public void createEntities(World world, Stage stage, Player player) {
//        level10EntitieCreator.createEntities(world, stage, getWorldHeight());
//    }
//
//    @Override
//    public int getWorldWidth() {
//        return worldWidth;
//    }
//
//    @Override
//    public int getWorldHeight() {
//        return worldHeight;
//    }
//
//    @Override
//    public Array<Actor> getActorArrayFromBeforeBossFight() {
//        return finOne.getFinishedActors();
//    }
//
//    @Override
//    public Transitionable getBossLevel() {
//        return null;
//    }
//
//    @Override
//    public Array<PlayerHasUnlocked> getUnlocksForThisLevel(ERQGame game) {
//        Array<PlayerHasUnlocked> unlockeds = new Array<PlayerHasUnlocked>();
//        unlockeds.add(new Level6Unlocker(game));
//        return unlockeds;
//    }
//
//}
