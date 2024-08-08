package de.keeyzar.earthragequit.gameplay.ingame.level.levels;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.gameplay.ingame.entities.bad.*;
import de.keeyzar.earthragequit.gameplay.ingame.entities.entitytypes.GameActor;
import de.keeyzar.earthragequit.gameplay.ingame.entities.good.*;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;

/**
 * A helper class to Spawn entities in a world
 * @author = Keeyzar on 27.03.2016
 */
public class Spawner {

    private static float X, Y;
    private static World world;
    private static float currYPos;
    private static float sliceSize;
    private static float worldHeight;
    private static boolean fillEvenly = true;


    public static void sliceWorld(float sliceSize){
        Spawner.sliceSize = sliceSize;
        currYPos = 0;
        Spawner.fillEvenly = false;
    }

    public static void setWorld(World world) {
        Spawner.world = world;
        fillEvenly = true;
    }

    private static void calcXY(){
        if(!fillEvenly) {
            X = MathUtils.random(0, 100);
            Y = MathUtils.random(currYPos, Math.min(Spawner.worldHeight, currYPos + sliceSize));
        } else {
            X = MathUtils.random(0, 100);
            Y = MathUtils.random(0, worldHeight);
        }
    }

    public static GameActor randomFuel() {
        calcXY();
        return new Fuel(world, X ,Y);
    }

    public static GameActor randomBee() {
        calcXY();
        return new Wasp(world, X ,Y);
    }
   public static GameActor randomChest() {
        calcXY();
        return new TreasureChest(world, X ,Y);
    }

    public static GameActor randomPlatform(Stage stage) {
        calcXY();
        return new Platform(world, X , Y, stage);
    }

    public static Coin randomCoin(int value){
        calcXY();
        return new Coin(world, value, X , Y);
    }

    public static GameActor randomIonizedCloud() {
        calcXY();
        return new IonizedClouds(world, X , Y);
    }

    public static GameActor randomMoneyBag(int value) {
        calcXY();
        return new Moneybag(world, value, X , Y);
    }

    /**
     *
     * @param whichSchrott
     * @param pool
     * @return
     */
    public static GameActor randomSchrott(int whichSchrott, ParticleEffectPool pool){
        calcXY();
        if(whichSchrott == 1){
            return new Schrott(world, X, Y, pool);
        } else{
            return new Schrott2(world, X, Y, pool);
        }
    }

    public static void setWorldHeight(float worldHeight) {
        Spawner.worldHeight = worldHeight;
    }

    public static void nextSlice() {
        Spawner.currYPos += Spawner.sliceSize;
    }

    public static Actor randomCreep(Player player) {
        calcXY();
        return new Creep(world, X, Y, player);
    }

    public static void releaseWorld() {
        world = null;
    }
}
