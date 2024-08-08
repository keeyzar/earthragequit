package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;


/**
 * @author = Keeyzar on 14.04.2016
 */
public class ColorComponent {

    public static void addColorComponent(final Color color, String text, Table table){
        Skin skin = ERQAssets.SKIN;

        Label[] blob = createLabels();
        final SelectBox<Label> sb = new SelectBox<Label>(skin){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
            }
        };
        sb.setItems(blob);
        sb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuUtils.playClickSound();
                color.set(new Color(sb.getSelected().getStyle().fontColor));
            }
        });

        Table smallTable = new Table();
        smallTable.add(new Label(text, skin)).padBottom(ScreenVariables.PAD_TB_S).row();
        smallTable.add(sb);
        table.add(smallTable).padTop(ScreenVariables.PAD_TB_N).row();
    }

    static Array<Color> colorList;
    private static Label[] createLabels() {
        if(colorList == null){
            colorList = new Array<Color>();
            colorList.add(Color.BLACK);
            colorList.add(Color.WHITE);
            colorList.add(Color.RED);
            colorList.add(Color.GREEN);
            colorList.add(Color.BLUE);
            colorList.add(Color.YELLOW);
            colorList.add(Color.ORANGE);
            colorList.add(Color.CORAL);
            colorList.add(Color.GRAY);
            colorList.add(Color.BROWN);
            colorList.add(Color.CHARTREUSE);
            colorList.add(Color.GOLD);
        }
        Label[] blob = new Label[colorList.size];

        int counter = 0;
        for(Color c : colorList){
            Skin skin = ERQAssets.SKIN;
            Label label = new Label("new Color", skin);
            Label.LabelStyle style = new Label.LabelStyle(label.getStyle());
            style.fontColor = c;
            blob[counter] = label;
            counter++;
        }
        return blob;
    }
}
