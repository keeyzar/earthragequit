package de.keeyzar.earthragequit.tools;

import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.achievements.AchievementPopupRef;
import de.keeyzar.earthragequit.achievements.all.Achievement;

/**
 * @author = Keeyzar on 24.03.2016
 */
public class ERQTools {
    private ERQGame game;
    private CheckBackKey cbk;
    private AchievementPopupRef acp;

    public ERQTools(ERQGame game, ToastInterface toastInterface){
        this.game = game;

        cbk = new CheckBackKey(game, toastInterface);
        acp = new AchievementPopupRef();

    }

    public void catchBackKey(float delta){
        cbk.act(delta);
    }

    public void showAchievement(float delta){
        acp.actAndDraw(delta);
    }

    public void newAchievement(Achievement achievement){
        acp.newAchievement(achievement);
    }

    public void dispose(){
        acp.dispose();
    }

    public void resize(int width, int height) {
        acp.resize(width, height);
    }

    public void registerBackNavigation(BackNavigationInterface backNavigationInterface) {
        cbk.registerBackNavigation(backNavigationInterface);
    }

    public void unregisterBackNavigation() {
        cbk.unregisterBackNavigation();
    }
}
