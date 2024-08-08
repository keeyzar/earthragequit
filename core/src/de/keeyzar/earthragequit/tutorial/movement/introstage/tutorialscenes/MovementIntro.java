package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.Player;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.actors.HudActor;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.tutorial.movement.introstage.TutorialStage;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 16.03.2016
 */
public class MovementIntro {
    private final TalkingStage tS;
    private final TutorialStage tutStage;
    private Player player;
    private OrthographicCamera camera;
    private ParticleWall particleWall;

    public MovementIntro(TalkingStage tS, TutorialStage tutStage, Player player, Camera camera){
        this.tS = tS;
        this.tutStage = tutStage;
        this.player = player;
        this.camera = (OrthographicCamera) camera;

        particleWall = new ParticleWall();
        particleWall.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y + Player.HEIGHT * 1.25f);

    }

    private int state = 0;
    private final int S_START = 0;
    private final int S_WAIT= 1;
    private final int S_WAIT_NEXT_TEXT = 2;
    private final int S_CHECK_IF_REACHED_TOP = 4;
    private final int S_ROTATION_FINISHED = 7;

    private int nextState = -1;
    public void act() {
        switch (state){
            case S_WAIT:
                if(Gdx.input.isTouched()){
                    tS.endText();
                    state = nextState;
                }
                break;
            case S_WAIT_NEXT_TEXT:
                if(Gdx.input.isTouched()){
                    state = nextState;
                }
                break;
            case S_START:
                String startText = LANG.format("tutorial_story_text_2");
                TWListener TWListener = new TWListener(){
                    @Override
                    public void TFadeOutFinish() {}

                    @Override
                    public void TShown() {
                        tutStage.addActor(particleWall);
                        state = S_WAIT;
                        nextState = S_CHECK_IF_REACHED_TOP;
                        tutStage.getHudStage().highlightTouchpad(true);
                    }
                };

                if(tutStage.isTextWriterOpened()){
                    tS.newText(startText, TWListener);
                } else {
                    tS.startText(startText, TWListener);
                }
                state = -1;
                break;
            case S_CHECK_IF_REACHED_TOP:
                checkIfPlayerHasReachedTopOnce();
                break;
            case S_ROTATION_FINISHED:
                tS.startText(LANG.format("tutorial_story_text_3"), new TWListener(){
                    @Override
                    public void TShown() {
                        state = S_WAIT;
                        nextState = -1;
                        tutStage.getHudStage().highlightTouchpad(false);
                    }
                    @Override
                    public void TFadeOutFinish() {
                        tutStage.nextPart(false);
                        HudActor hudActor = tutStage.getHudStage().getHudActor(HudVars.BOSSPROGRESS);
                        hudActor.getColor().a = 0;
                        hudActor.addAction(Actions.fadeIn(1));
                        hudActor.setVisible(true);
                    }
                });
                state = -1;
                break;
        }
    }


    private void checkIfPlayerHasReachedTopOnce() {
        if(player.body.getPosition().y >= camera.position.y + Player.HEIGHT){
            state = S_ROTATION_FINISHED;
            particleWall.remove();
        }
    }
}
