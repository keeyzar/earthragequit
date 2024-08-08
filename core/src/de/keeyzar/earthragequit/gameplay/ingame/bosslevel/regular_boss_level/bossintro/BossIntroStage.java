package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.bossintro;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;

/**
 * @author = Keeyzar on 12.03.2016
 */
public class BossIntroStage extends Stage {
    private final BossScreen bossScreen;

    public BossIntroStage(BossScreen bossScreen, BossLevelDefining bld) {
        this.bossScreen = bossScreen;
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, GVars.FOV_WIDTH, GVars.FOV_HEIGHT);
        setViewport(new StretchViewport(GVars.FOV_WIDTH, GVars.FOV_HEIGHT, camera));
        addActor(bld.getBossIntroClone(camera));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Array<Actor> actors = getActors();
        boolean hasActions = false;
        for(Actor actor :actors){
            if(actor.hasActions()){
                hasActions = true;
            }
        }
        if(!hasActions){
            bossScreen.startInfo();
            bossScreen.startGameplay();
        }
    }
}
