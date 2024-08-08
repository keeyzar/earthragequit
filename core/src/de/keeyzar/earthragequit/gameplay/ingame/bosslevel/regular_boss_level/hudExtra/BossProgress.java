package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.hudExtra;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author = Keeyzar on 12.03.2016
 */
public interface BossProgress {

    //the current progress
    float getCurrentProgress();

    //the max progress
    float getMaxProgress();

    //the text that should be displayed on the progress bar
    Image getImage();
}
