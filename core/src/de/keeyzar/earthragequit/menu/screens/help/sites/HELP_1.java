package de.keeyzar.earthragequit.menu.screens.help.sites;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.tutorial.TVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.*;

/**
 * this site gives an option to redo the tutorial.
 * @author = Keeyzar on 08.04.2016
 */
public class HELP_1 extends HELP_ {
    Skin skin;
    private ERQGame game;
    private Transitionable transitionable;

    public HELP_1(ERQGame game, Transitionable transitionable) {
        this.game = game;
        this.transitionable = transitionable;
        this.skin = ERQAssets.SKIN;
        init();
    }

    /**
     * inits this site.
     */
    private void init() {
        setTitle(LANG.format("help_page_titel_tutorial"));
        content.add(new Label(LANG.format("help_page_tutorial_desc"), skin))
                .padBottom(PAD_TB_S).row();

        TextButton textButton = new TextButton(LANG.format("help_page_bt_play_tut"), skin);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                playTutorial();
            }
        });
        content.add(textButton).size(B_WID, B_HEI).row();
        content.add("").grow();
    }

    /**
     * starts the tutorial.
     */
    private void playTutorial() {
        game.getTutorialVerwalter().setLevelShouldBeAbsolved(TVars.TUTORIAL, true);
        game.getTutorialVerwalter().redoLevel();
        game.getTutorialVerwalter().startLevel(game, transitionable);
    }
}
