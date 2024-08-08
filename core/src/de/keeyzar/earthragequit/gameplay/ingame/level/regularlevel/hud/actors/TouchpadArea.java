package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.tutorial.tools.Highlightable;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.SCREEN_HEIGHT;
import static de.keeyzar.earthragequit.variables.screens.ScreenVariables.SCREEN_WIDTH;

/**
 * @author = Keeyzar on 28.02.2016
 */
public class TouchpadArea extends Group implements Highlightable{
    private Player player;
    private PlayerTouchpad playerTouchpad;
    private InputEvent inputEvent;
    private Label highlightActor;

    public TouchpadArea(Player player){
        this.player = player;
        init();
        initTouchpad();
        addActor(playerTouchpad);
        inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchDown);
    }

    private void initTouchpad() {
        playerTouchpad = new PlayerTouchpad(player);
    }

    private void init() {
        setBounds(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        //delegate touchup, because PlayerTouchpad is too small.
        addListener(new InputListener(){
            private Vector2 tmp = new Vector2();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return ((InputListener)playerTouchpad.getListeners().get(0)).touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ((InputListener)playerTouchpad.getListeners().get(0)).touchUp(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                ((InputListener)playerTouchpad.getListeners().get(0)).touchDragged(event, x, y, pointer);
            }
        });

    }

    @Override
    public void enableHighlight(boolean enable) {
        if(enable){
            setDebug(true);
            highlightActor = new Label(LANG.format("tutorial_story_touchpad_text"), ERQAssets.SKIN);
            highlightActor.layout();
            highlightActor.setPosition(getWidth() / 2 - highlightActor.getWidth(), getHeight() - 100);
            addActor(highlightActor);
        } else {
            setDebug(false);
            if(highlightActor != null){
                highlightActor.remove();
                highlightActor = null;
            }
        }
    }
}
