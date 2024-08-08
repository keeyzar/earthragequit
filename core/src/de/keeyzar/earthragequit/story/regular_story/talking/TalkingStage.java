package de.keeyzar.earthragequit.story.regular_story.talking;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.keeyzar.earthragequit.assets.ERQAssets;
import de.keeyzar.earthragequit.gameplay.ingame.level.regularlevel.hud.HudVars;

import static de.keeyzar.earthragequit.assets.ERQAssets.LANG;

/**
 * @author = Keeyzar on 15.03.2016
 */
public class TalkingStage extends Stage{
    private final de.keeyzar.earthragequit.story.regular_story.talking.TutArrow tutArrow;
    de.keeyzar.earthragequit.story.regular_story.talking.TextWriter textWriter;
    private boolean acting = false;

    public TalkingStage(){
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT);
        setViewport(new StretchViewport(HudVars.HUD_WIDTH, HudVars.HUD_HEIGHT, camera));
        camera.update();
        tutArrow = new de.keeyzar.earthragequit.story.regular_story.talking.TutArrow(this);
        tutArrow.setBounds(0, 0, 50, 100);
        textWriter = new de.keeyzar.earthragequit.story.regular_story.talking.TextWriter(this);
        textWriter.setBounds(0, 0, HudVars.HUD_WIDTH, 200);
    }

    public void startText(String text, TWListener TWListener){
        acting = true;
        textWriter.textStart(text, TWListener);
    }

    public void newText(String text, TWListener TWListener){
        textWriter.newText(text, TWListener);
    }

    public void endText(){
        textWriter.textEnd();
        acting = false;
    }

    public void startTutArrow(int dir, int x, int y, int intensity){
        tutArrow.start(dir, x, y, intensity);
    }

    public de.keeyzar.earthragequit.story.regular_story.talking.TutArrow getTutArrow(){
        return tutArrow;
    }

    public void stopTutArrow(){
        tutArrow.stop();
    }

    public void setDisabledEnableInput(InputMultiplexer input, Stage stageToDisable){
        textWriter.setEnableDisableInput(input, stageToDisable);
    }

    public boolean isActing() {
        return acting;
    }

    public de.keeyzar.earthragequit.story.regular_story.talking.TextWriter getTW() {
        return textWriter;
    }

    TextButton skip;

    /**
     * Inits a "Skip" button. ClickListener is for callback
     * @param clickListener
     */
    public void initSkip(ClickListener clickListener){
        skip = new TextButton(LANG.format("tutorial_story_skip_button"), ERQAssets.SKIN);
        skip.addListener(clickListener);
        skip.setSize(200, 75);
        skip.setPosition(HudVars.HUD_WIDTH / 2 - skip.getWidth() / 2, HudVars.HUD_HEIGHT - skip.getHeight() * 2);
        addActor(skip);
    }

    public TextButton getSkip(){
        return skip;
    }

    @Override
    public void dispose() {
        super.dispose();
        textWriter.dispose();
    }
}
