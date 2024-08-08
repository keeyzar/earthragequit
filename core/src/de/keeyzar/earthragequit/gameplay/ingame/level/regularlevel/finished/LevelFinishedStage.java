package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.finished;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.TransitionScreen;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.menu.screens.mainmenu.MainMenuScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

/**
 * This stage shows some nice Gimmick. Like Crazy flickering 'Caution' or
 * other stuff. It's up to you
 * @author = Keeyzar on 07.03.2016
 */
public class LevelFinishedStage extends Stage {
    private GameScreen gameScreen;
    private ERQGame game;
    private boolean started = false;

    public LevelFinishedStage(GameScreen gameScreen, ERQGame game){
        this.gameScreen = gameScreen;
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);
        this.setViewport(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, camera));
        Array<Actor> actorArrayFromBeforeBossFight = gameScreen.getLevelDefining().getActorArrayFromBeforeBossFight();
        if(actorArrayFromBeforeBossFight == null) {
            addActor(new FinishedActor());
        }else {
            for(Actor actor : actorArrayFromBeforeBossFight){
                addActor(actor);
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act() {
        super.act(Gdx.graphics.getDeltaTime());
        boolean hasActions = false;
        for(Actor actor : getActors()){
            if(actor.hasActions()){
               hasActions = true;
            }
        }
        if(!hasActions && !started){
            setBossLevel();
            started = true;
        }
    }

    private void setBossLevel() {
        Transitionable bossLevel = gameScreen.getLevelDefining().getBossLevel();
        if(bossLevel != null){
            game.setScreen(new TransitionScreen(game, gameScreen, bossLevel));

        } else {
            game.setScreen(new TransitionScreen(game, gameScreen, new MainMenuScreen(game)));
        }
    }
}
