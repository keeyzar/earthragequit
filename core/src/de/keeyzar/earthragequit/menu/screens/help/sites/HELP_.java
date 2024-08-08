package de.keeyzar.earthragequit.menu.screens.help.sites;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

/**
 * is the super class for every Help site
 * @author = Keeyzar on 08.04.2016
 */
public abstract class HELP_ {
    protected Table site;
    protected Table content;

    private Label title;

    public HELP_(){
        site = new Table(ERQAssets.SKIN);
        content = new Table(ERQAssets.SKIN);
        title = new Label("", ERQAssets.SKIN);

        site.add(title).padBottom(ScreenVariables.PAD_TB_S).row();
        site.add(content).grow();
    }

    /**
     * Sets the titleText from this HelpDialog
     * @param title
     */
    public void setTitle(String title){
        this.title.setText(title);
    }

    public Table getSite() {
        return site;
    }
}
