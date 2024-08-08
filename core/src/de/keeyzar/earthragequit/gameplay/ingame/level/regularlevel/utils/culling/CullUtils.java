package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.utils.culling;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

/**
 * @author = Keeyzar on 28.03.2016
 */
public class CullUtils {
    public static Rectangle cullRect;
    public static Rectangle bodyRect = new Rectangle();
    public static boolean checkIt(Body cullBody, Body bodyToCheck){
        if(cullRect == null) {
            cullRect = new Rectangle();
            float xCTop = Box2DUtils.maxYWorld(cullBody);
            float xCBot = Box2DUtils.minYWorld(cullBody);
            float xCLeft = Box2DUtils.minXWorld(cullBody);
            float xCRight = Box2DUtils.maxXWorld(cullBody);
            cullRect.set(xCLeft, xCBot, xCRight - xCLeft, xCTop - xCBot);
        }
        float top = Box2DUtils.maxYWorld(bodyToCheck);
        float bot = Box2DUtils.minYWorld(bodyToCheck);
        //early check
        if(bot > cullRect.getY() + cullRect.getHeight()) return false;
        float left = Box2DUtils.minXWorld(bodyToCheck);
        float right = Box2DUtils.maxXWorld(bodyToCheck);
        bodyRect.set(left, bot, right-left, top-bot);
        return cullRect.overlaps(bodyRect);
    }

    public static void resetCullUtil(){
        cullRect = null;
    }
}
