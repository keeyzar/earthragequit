package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.menu.screens.elements.SkillUsed;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;

/**
 *
 * @author = Keeyzar on 22.02.2016
 */
public class SkillsDialog {
    private final Skin skin;
    private final Stage stage;
    private final ERQGame game;
    private SkillUsed skillUsed;
    private SkillSiteListener skl;
    private SkillsDialogContent sdc;

    int place;
    private PlayerSkills playerSkills;

    /**
     * Erstelle Dialog
     * @param stage
     * @param game
     */
    public SkillsDialog(Stage stage, ERQGame game, SkillUsed skillUsed, SkillSiteListener skl){
        this.game = game;
        this.skillUsed = skillUsed;
        this.skl = skl;
        this.skin = ERQAssets.SKIN;
        this.stage = stage;
        sdc = new SkillsDialogContent(game, skillUsed, skl, stage);
    }

    public void createDialog(int place){
        playerSkills = game.getSkillsVerwalter().getAct_PlayerskillsMap().get(place);
        this.place = place;
        final Dialog dialog = new Dialog("", skin);
        // add content to dialog
        sdc.createContent(dialog, place);

        dialog.setFillParent(true);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage, Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.4f, Interpolation.fade), Actions.run(new Runnable() {
            @Override
            public void run() {
                if(skl != null) {
                    skl.openedDialog();
                }
            }
        })));
    }

    public void highlightProcedure() {
        sdc.highlightProcedure();
    }
}
