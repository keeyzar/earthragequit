package de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.beforeend;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossLevelDefining;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.BossScreen;
import de.keeyzar.earthragequit.gameplay.ingame.bosslevel.regular_boss_level.bossoverview.GameOverTextGroup;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.variables.GVars;

/**
 * @author = Keeyzar on 11.03.2016
 */
public class BeforeEndStage extends Stage {
    private final BossScreen bossScreen;
    private Array<Startable> startables;
    private boolean won = false;

    public BeforeEndStage(ERQGame game, BossScreen bossScreen, BossLevelDefining bld) {
        this.bossScreen = bossScreen;
        Player player = bld.getPlayer();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, GVars.FOV_WIDTH, GVars.FOV_HEIGHT);
        setViewport(new StretchViewport(GVars.FOV_WIDTH, GVars.FOV_HEIGHT, camera));
        startables = new Array<Startable>();
        startables.add(new PlayerClone(player, camera));
        startables.add(new BossClone(bld.getBoss(), camera));
        startables.add(new GameOverTextGroup());
        for(Startable startable : startables){
            addActor((Actor) startable);
        }

    }

    @Override
    public void act() {
        super.act();
        boolean hasActions = false;
        for(Startable startable : startables){
            Actor actor = (Actor) startable;
            if(actor.hasActions()){
                hasActions = true;
            }
        }
        if(!hasActions){
            if(won) {
                bossScreen.startOverview();
            } else {
                bossScreen.toMenu();
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void start(boolean won){
        this.won = won;
        for (Startable startable : startables) {
            startable.start(won);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        startables.clear();
    }
}
