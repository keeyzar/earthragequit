package de.keeyzar.earthragequit.story.regular_story.motherstory_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.keeyzar.earthragequit.ERQGame;
import de.keeyzar.earthragequit.transition.Transitionable;
import de.keeyzar.earthragequit.sound.MusicHandler;
import de.keeyzar.earthragequit.story.regular_story.talking.TalkingStage;

/**
 * This class should provide everything you need for a Story Transscript.
 * You can show your background, that you wish (actAndDraw/draw)
 * You can use TalkingStage to show some additional Text
 * @author = Keeyzar on 23.03.2016
 */
public abstract class StoryScreen extends Transitionable {
    Stage backgroundStage;
    Stage workingStage;
    protected TalkingStage tS;
    private ERQGame game;


    boolean bSAct = false; //backgroundStage.actAndDraw
    boolean bSDraw = false; //backgroundStage.draw
    boolean wSAct = false; //workingStage.actAndDraw
    boolean wSDraw = false; //workingStage.draw

    public StoryScreen(ERQGame game) {
        this.game = game;
        tS = new TalkingStage();
        game.getMusicHandler().playMusic(MusicHandler.STORY_MUSIC);
    }



    public int state = 1;
    public int nextState = -10;
    public final int S_NOTHING = -1;
    public final int S_WAIT = -2;
    public final int S_WAIT_NEXT_TEXT = -3;

    @Override
    public void render(float delta) {
        //clear Screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state){
            case S_NOTHING:
                //do Nothing
                break;
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
            default:
                checkState();
        }
        if(backgroundStage != null && bSAct) backgroundStage.act(delta);
        if(backgroundStage != null && bSDraw) backgroundStage.draw();
        if(workingStage != null && wSAct) workingStage.act(delta);
        if(workingStage != null && wSDraw) workingStage.draw();
        tS.act(delta);
        tS.draw();
    }

    /**
     * In this Method you should check for your story specific cases
     * caution: only use State above zero
     */
    public abstract void checkState();

    @Override
    public void resize(int width, int height) {
        if(backgroundStage != null){
            backgroundStage.getViewport().update(width, height);
        }
        if(workingStage != null){
            workingStage.getViewport().update(width, height);
        }
        tS.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if(backgroundStage != null) backgroundStage.dispose();
        if(workingStage != null) workingStage.dispose();
        tS.dispose();
    }


    @Override
    public void act(float delta) {

    }

    @Override
    public void draw() {
        render(Gdx.graphics.getDeltaTime());
    }

    /**
     * Set the backgroundStage
     * @param backgroundStage
     */
    public void setBackgroundStage(Stage backgroundStage) {
        this.backgroundStage = backgroundStage;
    }

    /**
     * Set the WorkingStage
     * @param workingStage
     */
    public void setWorkingStage(Stage workingStage) {
        this.workingStage = workingStage;
    }

    public void setbSAct(boolean bSAct) {
        this.bSAct = bSAct;
    }

    public void setbSDraw(boolean bSDraw) {
        this.bSDraw = bSDraw;
    }

    public void setwSAct(boolean wSAct) {
        this.wSAct = wSAct;
    }

    public void setwSDraw(boolean wSDraw) {
        this.wSDraw = wSDraw;
    }
}
