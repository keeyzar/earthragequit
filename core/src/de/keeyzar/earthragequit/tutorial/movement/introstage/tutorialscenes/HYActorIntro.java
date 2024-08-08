package de.keeyzar.earthragequit.tutorial.movement.introstage.tutorialscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import de.keeyzar.earthragequit.tutorial.movement.introstage.TutorialStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;
import de.keeyzar.earthragequit.story.regular_story.talking.TWListener;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 17.03.2016
 */
public class HYActorIntro {
    private final TalkingStage talkStage;
    private final TutorialStage tutStage;
    private final HYActor hyActor;


    private int state = 3;
    private final int S_WAIT_FOR_INPUT = 1;
    private final int S_WAIT_FOR_INPUT_NEXT_TEXT = 2;
    private final int STATE_SHOW_HYACTOR = 3;
    private final int STATE_SHOW_FIRST_TEXT = 4;
    private final int STATE_START_INTRO = 5;

    private int nextState = -1;
    private int S_NONE = -100;

    public HYActorIntro(TalkingStage talkStage, TutorialStage tutStage){
        this.talkStage = talkStage;
        this.tutStage = tutStage;
        hyActor = new HYActor((OrthographicCamera) talkStage.getCamera());
        talkStage.addActor(hyActor);
    }


    public void act(){
        switch (state){
            case S_WAIT_FOR_INPUT:
                if(Gdx.input.isTouched()){
                    state = nextState;
                    talkStage.endText();
                }
                break;
            case S_WAIT_FOR_INPUT_NEXT_TEXT:
                if(Gdx.input.isTouched()){
                    state = nextState;
                }
                break;
            case STATE_SHOW_HYACTOR:
                if (!hyActor.hasActions()) {
                    hyActor.addAction(Actions.fadeOut(0.5f));
                    state = STATE_SHOW_FIRST_TEXT;

                }
                break;
            case STATE_SHOW_FIRST_TEXT:
                hyActor.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        hyActor.remove();
                    }
                })));
                talkStage.startText(LANG.format("tutorial_story_text_1"), new TWListener(){
                    @Override
                    public void TShown() {
                        state = S_WAIT_FOR_INPUT_NEXT_TEXT;
                        nextState = STATE_START_INTRO;
                        tutStage.start(); // so the player mysteriously appears
                    }
                });
                state = S_NONE;
                break;
            case STATE_START_INTRO:
                tutStage.nextPart(true);
                break;
        }
    }


}
