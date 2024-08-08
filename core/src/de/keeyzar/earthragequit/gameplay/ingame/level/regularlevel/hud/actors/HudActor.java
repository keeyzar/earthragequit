package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.adjusthud.AdjustHudDialog;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.MenuUtils;

/**
 * @author = Keeyzar on 03.03.2016
 */
public abstract class HudActor extends Actor {
    private String identifierName;
    private String displayName;
    private final String PREFERENCES = "HUD";
    private boolean couldLoad = true;
    private boolean fixedSizeRatio = false;
    private boolean fixedTransparency = false;
    private Color foreground;
    private Color background;
    private Color text;

    public HudActor(String identifierName){
        this.identifierName = identifierName;
        loadPosition();
        normalMode();
    }

    public void adjustModeListener(Stage stage){
        setDebug(true);
        getListeners().clear();
        final AdjustHudDialog adjustHudDialog = new AdjustHudDialog(stage, this);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuUtils.playClickSound();
                adjustHudDialog.createDialog(displayName);
            }
        });
    }

    public void normalMode(){
        setDebug(false);
    }


    private void loadPosition() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
        float _x = prefs.getFloat(identifierName + "_X", -100);
        float _y = prefs.getFloat(identifierName + "_Y", -100);
        float _width = prefs.getFloat(identifierName + "_WIDTH", -100);
        float _height = prefs.getFloat(identifierName + "_HEIGHT", -100);
        float _trans = prefs.getFloat(identifierName + "_TRANS", 1);

        if (_x == -100 || _y == -100 || _width == -100 || _height == -100) {
            couldLoad = false;
        }
        if (!couldLoad) {
            initPosition();
            return;
        }
        setBounds(_x, _y, _width, _height);
        getColor().a = _trans;
    }

    /**
     * place standart xywh here
     */
    public abstract void initPosition();

    public void safeXYWH() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES);
        preferences.putFloat(identifierName + "_X", getX());
        preferences.putFloat(identifierName + "_Y", getY());
        preferences.putFloat(identifierName + "_WIDTH", getWidth());
        preferences.putFloat(identifierName + "_HEIGHT", getHeight());
        preferences.putFloat(identifierName + "_TRANS", getColor().a);

        preferences.flush();
    }

    public void restoreToDefault(){
        initPosition();
        getColor().a = 1;
    }

    @Override
    public String getName() {
        return identifierName;
    }

    public boolean isFixedSizeRatio() {
        return fixedSizeRatio;
    }

    public void setFixedSizeRatio(boolean fixedSizeRatio) {
        this.fixedSizeRatio = fixedSizeRatio;
    }

    public boolean isFixedTransparency() {
        return fixedTransparency;
    }

    public void setFixedTransparency(boolean fixedTransparency) {
        this.fixedTransparency = fixedTransparency;
    }

    /**
     * if your size should stay in bounds, overwrite this method.
     */
    public void ensureSize(){

    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
