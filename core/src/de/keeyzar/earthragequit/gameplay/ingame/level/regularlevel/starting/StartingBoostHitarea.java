package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.starting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.assets.TextureAtlasVariables;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class StartingBoostHitarea extends Actor {


    private StartingBoostMovingPart startingBoostStrich;
    private NinePatch ninePatch;

    public StartingBoostHitarea(StartingBoostMovingPart startingBoostStrich){
        this.startingBoostStrich = startingBoostStrich;
        setWidth(50);
        setHeight(50);
        setX(1024/2 - getWidth() / 2);
        setY(800/2 - getHeight() / 2);
        ninePatch = new NinePatch(ERQAssets.T_ATLAS_GAME.createPatch(TextureAtlasVariables.STARTING_BALKEN_HITAREA));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ninePatch.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
