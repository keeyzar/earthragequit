package de.keeyzar.earthragequit.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.keeyzar.earthragequit.ERQGame;

import java.util.Stack;

/**
 * Catches Back key globally
 * @author = Keeyzar on 24.03.2016
 */
public class CheckBackKey {
    boolean toast=false;
    float toasttimer=0;
    float timeToExit = 1.5f;
    private ERQGame game;
    private final ToastInterface toastInterface;
    private Stack<BackNavigationInterface> stack;


    public CheckBackKey(ERQGame game, ToastInterface toastInterface) {
        this.game = game;
        this.toastInterface = toastInterface;
        stack = new Stack<BackNavigationInterface>();
        Gdx.input.setCatchBackKey(true);
    }

    public void act(float delta) {
        if(!stack.empty() && !stack.peek().shouldUseDefaultBehaviour() && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if(stack.peek().shouldRemoveFromStack()) {
                stack.pop().backPressed();
            } else {
                stack.peek().backPressed();
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) && !toast) {
                toast = true;
                toastInterface.showToast("Tap again to exit the app");
            } else if (toast && Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
                Gdx.app.exit();
            } else if (toasttimer > timeToExit) {
                toast = false;
                toasttimer = 0;
            } else if (toast) {
                toasttimer += delta;
            }
        }
    }

    public void registerBackNavigation(BackNavigationInterface backNavigationInterface){
        stack.push(backNavigationInterface);
    }

    public void unregisterBackNavigation(){
        if(!stack.empty()) stack.pop();
    }
}
