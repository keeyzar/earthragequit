package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.AdjustHudStageListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudStage;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;
import de.keeyzar.earthragequit.variables.screens.ScreenVariables;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class AdjustHud extends Stage {

    OrthographicCamera camera;
    private HudStage hudStage;
    private AdjustHudStageListener adjustHudStageListener;

    public AdjustHud(HudStage hudStage, AdjustHudStageListener adjustHudStageListener){
        this.hudStage = hudStage;
        this.adjustHudStageListener = adjustHudStageListener;

        init();
        createSteuerung();
    }

    private void createSteuerung() {
        final TextButton finish = new TextButton(LANG.format("adjust_hud_finished"), ERQAssets.SKIN);
        finish.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                finished();
            }
        });
        Table table = new Table(ERQAssets.SKIN);
        table.setFillParent(true);
        Label label = new Label(LANG.format("adjust_hud_desc"), ERQAssets.SKIN);
        Label.LabelStyle style = new Label.LabelStyle(label.getStyle());
        style.background = new ScrollPane(null, ERQAssets.SKIN).getStyle().background;
        label.setStyle(style);

        table.add(label).padBottom(ScreenVariables.PAD_TB_S).row();
        table.add(finish).size(200, 75);
        addActor(table);
    }

    private void addAdjustableItems() {
        for(HudActor hudActor : hudStage.getHudActors()){
            addActor(hudActor);
        }
    }

    private void finished() {
        for(HudActor hudActor : hudStage.getHudActors()){
            hudActor.normalMode();
        }
        hudStage.adjustmentFinished();
        addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                adjustHudStageListener.startPause();
            }
        })));
    }

    private void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);
        setViewport(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, camera));
    }

    public void activateAdjustMode(){
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.2f)));
        addAdjustableItems();
        for(HudActor hudActor : hudStage.getHudActors()){
            hudActor.adjustModeListener(this);
        }
    }
}
