package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.starting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class StartingBoostMovingPart extends Actor {
    StartingBoostBalken startingBoostBalken;
    NinePatch ninePatch;
    int direction = 1;


    public StartingBoostMovingPart(StartingBoostBalken startingBoostBalken){
        this.startingBoostBalken = startingBoostBalken;
        setWidth(30);
        setHeight(50);
        float xPos = MathUtils.random(startingBoostBalken.getX() + 10, startingBoostBalken.getX() + startingBoostBalken.getWidth() - 10);
        setX(xPos);
        setY(800/2 - getHeight() / 2);
        init();
    }

    private void init() {
        ninePatch = new NinePatch(ERQAssets.T_ATLAS_GAME.createPatch(TextureAtlasVariables.STARTING_BALKEN_MOVING_PART));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getX() + getWidth() > startingBoostBalken.getX() + startingBoostBalken.getWidth()){
            direction = -1;
        } else if(getX() <= startingBoostBalken.getX()){
            direction = 1;
        }
        moveBy(direction * 20, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ninePatch.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
