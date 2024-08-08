package de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.starting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.GameScreen;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 03.03.2016
 */
public class StartingStage extends Stage {
    Player player;
    private GameScreen gameScreen;

    StartingBoostMovingPart startingBoostMovingPart;
    StartingBoostHitarea startingBoostHitarea;

    boolean disabled = false;
    public StartingStage(Player player, GameScreen gameScreen){
        disabled = gameScreen.getLevelDefining().isStartingBoostDisabled();
        this.player = player;
        this.gameScreen = gameScreen;
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 800);
        StretchViewport stretchViewport = new StretchViewport(1024, 800, camera);
        setViewport(stretchViewport);
        init();
    }

    private void init() {
        StartingBoostBalken startingBoostBalken = new StartingBoostBalken();
        startingBoostBalken.setZIndex(1);
        addActor(startingBoostBalken);
        startingBoostMovingPart = new StartingBoostMovingPart(startingBoostBalken);
        startingBoostMovingPart.setZIndex(3);
        startingBoostHitarea = new StartingBoostHitarea(startingBoostMovingPart);
        startingBoostHitarea.setZIndex(2);
        addActor(startingBoostHitarea);
        addActor(startingBoostMovingPart);
        Label label = new Label(LANG.format("mega_start_text"), ERQAssets.SKIN);
        label.layout();
        label.setPosition(1024/2 - label.getWidth() / 2, 800 / 2 + 100);
        addActor(label);
    }
    @Override
    public void draw() {
        super.draw();
        if(!disabled) {
            if (Gdx.input.isTouched()) {
                check();
                finished();
                gameScreen.fadeInFromGameStage();
            }
        } else {
            finished();
        }
    }

    private void check() {
        Rectangle rectHitArea = new Rectangle();
        rectHitArea.setSize(startingBoostHitarea.getWidth(), startingBoostHitarea.getHeight());
        rectHitArea.setPosition(startingBoostHitarea.getX(), startingBoostHitarea.getY());
        Rectangle rectMovingPart = new Rectangle();
        rectMovingPart.setSize(startingBoostMovingPart.getWidth(), startingBoostMovingPart.getHeight());
        rectMovingPart.setPosition(startingBoostMovingPart.getX(), startingBoostMovingPart.getY());

        if(rectHitArea.overlaps(rectMovingPart)){
                player.getPlayerMovement().startingBoost();
        }

    }

    private void finished(){
        gameScreen.showInfoForLevelQuest();
        gameScreen.startGameplay();
        dispose();
    }
}
