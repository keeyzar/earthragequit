package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 14.04.2016
 */
public class TransparencyComponent {

    public static void addTransparencyComponent(final HudActor hudActor, Table table){
        Skin skin = ERQAssets.SKIN;
        Table smallTable = new Table();


        Slider transparencySlider = new Slider(0, 1, 0.05f, false, skin);
        transparencySlider.getStyle().knob.setMinHeight(50);
        transparencySlider.getStyle().background.setMinHeight(40);
        transparencySlider.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return false;
            }
        });
        transparencySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Slider musicSlider = (Slider) actor;
                final Color cpy = hudActor.getColor().cpy();
                cpy.a = musicSlider.getValue();
                hudActor.setColor(cpy);
            }
        });

        transparencySlider.setValue(hudActor.getColor().a);

        smallTable.add(new Label(LANG.format("adjust_hud_transparency"), skin)).padBottom(ScreenVariables.PAD_TB_S).row();
        smallTable.add(transparencySlider);
        table.add(smallTable).padTop(ScreenVariables.PAD_TB_N).width(400).height(50).row();
    }

}
