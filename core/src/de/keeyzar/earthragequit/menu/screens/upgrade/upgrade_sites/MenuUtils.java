package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.variables.assets.AssetVariables;

/**
 * @author = Keeyzar on 06.02.2017.
 */
public class MenuUtils {
    private static Sound clickSound;

    public static void colorLabelDependingOnCosts(int costs, int actualCoins, Actor... toColor) {
        final float duration = 0.3f;
        Color color;
        if(costs <= actualCoins){
            color = Color.GREEN.cpy();
        } else {
            color = new Color(178/255f, 34/255f, 34/255f, 1);

        }
        for(Actor actor : toColor){
            actor.addAction(Actions.color(color, duration));
        }
    }

    public static void setEnabled(TextButton tb, boolean enabled){
        setEnabled(tb, enabled, false);
    }

    public static void setEnabled(TextButton tb, boolean enabled, boolean special){
        if (enabled) {
            tb.setTouchable(Touchable.enabled);
            if(special){
                tb.setStyle(ERQAssets.TB_SPECIAL);
            } else {
                tb.setStyle(ERQAssets.TB_ENABLED);
            }
        } else {
            tb.setTouchable(Touchable.disabled);
            tb.setStyle(ERQAssets.TB_DISABLED);
        }
    }

    public static void playClickSound(){
        if(clickSound == null){
            clickSound = ERQAssets.MANAGER.get(AssetVariables.SOUND_BUTTON_CLICK);
        }

        clickSound.play(MusicHandler.getSoundVolume());
    }
}
