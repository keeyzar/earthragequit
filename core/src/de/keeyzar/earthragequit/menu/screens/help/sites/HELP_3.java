package de.keeyzar.earthragequit.menu.screens.help.sites;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.keeyzar.earthragequit.assets.ERQAssets;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * this site gives an option to redo the tutorial.
 * @author = Keeyzar on 08.04.2016
 */
public class HELP_3 extends HELP_ {
    Skin skin;

    public HELP_3() {
        this.skin = ERQAssets.SKIN;
        init();
    }

    /**
     * inits this site.
     */
    private void init() {
        setTitle(LANG.format("help_page_titel_faster_coins"));
        Label label = new Label(LANG.format("help_page_faster_coins_desc"), skin);
        label.setWrap(true);

        content.add(label).fill().row();
        content.add("").grow();

    }

}
