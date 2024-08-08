package de.keeyzar.earthragequit.gameplay.ingame.entities.bad;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * if called instantiates a rectangle cage
 * @author = Keeyzar on 18.04.2016
 */
public class CageLock {
    LockedWall lockLeft;
    LockedWall lockRight;
    LockedWall lockTop;
    LockedWall lockBot;

    public CageLock(World world,int requiredKey, float posX, float posY, float innerCageWidth, float innerCageHeight,
                    Stage stage){

        float vWidth = 1, vHeight = 1;
        float hWidth = vHeight, hHeight = vWidth;

        lockLeft = new LockedWall(world, requiredKey,
                hWidth,
                hHeight + innerCageHeight,
                posX - innerCageWidth / 2 - hWidth / 4 * 3,
                posY - innerCageHeight / 2 - hHeight / 2, new LockedWall.UnlockListener() {
            @Override
            public void unlocked() {
                unlockAll();
            }
        });
        lockBot = new LockedWall(world, requiredKey,
                vWidth + innerCageWidth,
                vHeight,
                posX - innerCageWidth / 2 - vWidth / 2,
                posY - innerCageHeight / 2 - vHeight / 4 * 3, new LockedWall.UnlockListener() {
            @Override
            public void unlocked() {
                unlockAll();
            }
        });
        lockRight = new LockedWall(world, requiredKey,
                vWidth,
                vHeight + innerCageHeight,
                posX + innerCageWidth / 2 - vWidth / 4,
                posY - innerCageHeight / 2 - hHeight / 2, new LockedWall.UnlockListener() {
            @Override
            public void unlocked() {
                unlockAll();
            }
        });
        lockTop = new LockedWall(world, requiredKey,
                hWidth + innerCageWidth,
                hHeight,
                posX - innerCageWidth / 2 - hWidth / 2,
                posY + innerCageHeight / 2 - hHeight / 4, new LockedWall.UnlockListener() {
            @Override
            public void unlocked() {
                unlockAll();
            }
        });
        stage.addActor(lockLeft);
        stage.addActor(lockRight);
        stage.addActor(lockBot);
        stage.addActor(lockTop);
    }

    private void unlockAll(){
        lockLeft.gotUnlocked();
        lockRight.gotUnlocked();
        lockBot.gotUnlocked();
        lockTop.gotUnlocked();
    }
}
